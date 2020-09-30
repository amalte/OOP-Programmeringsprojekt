package edu.chalmers.model.wave;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.enemy.EnemyComponent;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.*;

/**
 * Class that handles waves in the game. Uses SpawnEnemyRunnable to spawn in enemies.
 */
public class WaveManager {

    private List<String> enemiesToSpawn = new ArrayList<>();
    private int currentWave = 1;
    private int baseWaveTimeSec = 5;  // Shortest time a wave lasts before the next one starts
    private int shortSpawnMs = 1000;    // Lowest time between enemies spawning
    private int longSpawnMs = 3000;    // Longest time between enemies spawning

    private TimerAction waveTimerAction;    // Timer for when a new wave should spawn
    private SpawnEnemyRunnable spawnEnemyRunnable;  // Spawn enemies in a time interval


    public WaveManager(Entity player) {
        spawnEnemyRunnable = new SpawnEnemyRunnable(enemiesToSpawn, shortSpawnMs, longSpawnMs, player);
    }

    /**
     * Method will spawn a wave of enemies with a time interval
     */
    public void generateNewWave() {
        currentWave++;
        calculateEnemiesToSpawn(enemiesToSpawn, currentWave);
        //startNewWaveTimer();    // Will generateNewWave if timer reaches 0 (Warning will be infinite loop unless timer is stopped somewhere)
        spawnEnemies(spawnEnemyRunnable);
    }

    /**
     * Method will add enemies that should spawn to the param list, which and how many enemies that are added depends on which wave it is
     * @param spawnList which list the enemies should be added to
     * @param wave which wave the method should be calculated for
     */
    private void calculateEnemiesToSpawn(List<String> spawnList, int wave) {
        for (int i = 0; i < wave; i++) {
            spawnList.add("ZOMBIE");
        }
        for (int i = 0; i < Math.round((double)wave / 3); i++) {
            //spawnList.add("Enemy2");
        }
        if(wave % 5 == 0) {  // Spawn difficult enemy every 5 waves
            for (int i = 0; i < wave / 5; i++) {
                //spawnList.add("Enemy3");
            }
        }
    }

    /**
     * Method will spawn enemies from enemiesToSpawnList with an interval of shortSpawnMs to longSpawnMs milliseconds
     * @param spawnEnemyRunnable runnable class that spawns enemies
     */
    private void spawnEnemies(SpawnEnemyRunnable spawnEnemyRunnable) {
        if(!spawnEnemyRunnable.getIsRunnableActive()) {   // Make sure only one spawn wave timer is active
            runOnce(spawnEnemyRunnable, Duration.ZERO);  // Start spawn runnable, (first one will spawn with no delay)
            spawnEnemyRunnable.setIsRunnableActive(true);
        }
    }

    /**
     * Creates a timer which will call generateNewWave() method after baseWaveTimeSec + getSpawnTimeSec() seconds
     */
    public void startNewWaveTimer() {    // Begin wave timer
        stopWaveTimer();   // Stop and remove currentWaveTimer since a new one should start
        waveTimerAction = createWaveTimer();  // Create new timer, when timer reaches 0 it will generate wave
    }

    /**
     * Method will stop the current waveTimer if it exists
     */
    public void stopWaveTimer() { if(waveTimerAction != null) waveTimerAction.expire(); }

    private TimerAction createWaveTimer() {
        return runOnce(() -> generateNewWave(), Duration.seconds(baseWaveTimeSec + getSpawnTimeSec()));
    }

    /**
     * Returns the amount of seconds it will take to spawn the current wave
     * @return integer seconds it takes to spawn wave
     */
    public int getSpawnTimeSec() {
        if(enemiesToSpawn.size() == 0) {
            System.out.println("SpawnTime cant be calculated because enemiesToSpawn list is empty");
            return 0;
        }
        return Math.round((enemiesToSpawn.size() * averageSpawnIntervalSec()) - averageSpawnIntervalSec());     // -averageSpawnTime since first enemy takes no time to spawn
    }

    /**
     * Method calculates on average how long it should take to spawn one enemy
     * @return float average seconds it takes to spawn an enemy
     */
    private float averageSpawnIntervalSec() {
        return (float)(longSpawnMs + shortSpawnMs) / (2 * 1000);    // Divided by 1000 to convert ms to sec
    }

    /**
     * Getter for currentWave variable
     * @return integer currentWave
     */
    public int getCurrentWave() {
        return currentWave;
    }
}