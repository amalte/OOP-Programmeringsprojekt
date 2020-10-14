package edu.chalmers.model.wave;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.enemy.StatMultiplier;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * Class that handles waves in the game. Uses SpawnEnemyRunnable to spawn in enemies.
 */
public class WaveManager {
    private int currentWave = 1;
    private int baseWaveTimeSec = 15;  // Shortest time a wave lasts before the next one starts

    private TimerAction waveTimerAction;    // Timer for when a new wave should spawn
    private SpawnEnemyRunnable spawnEnemyRunnable;  // Spawn enemies in a time interval

    public WaveManager(Entity player) {
        spawnEnemyRunnable = new SpawnEnemyRunnable(player);
        startTimerAllEnemiesDead();  // Timer that generates wave if all enemies on screen are dead
    }

    /**
     * Method will spawn a wave of enemies with a time interval
     */
    public void generateNewWave() {
        currentWave++;
        calculateEnemiesToSpawn();
        updateEnemyStats();
        startNewWaveTimer();    // Will generateNewWave if timer reaches 0 (Warning will be infinite loop unless timer is stopped somewhere)
        spawnEnemies(spawnEnemyRunnable);
    }

    //Method will add enemies that should spawn to the param list, which and how many enemies that are added depends on which wave it is
    private void calculateEnemiesToSpawn() {
        List<String> enemiesToSpawn = spawnEnemyRunnable.getEnemiesToSpawn();

        for (int i = 0; i < currentWave; i++) {
            enemiesToSpawn.add("ZOMBIE");
        }
        for (int i = 0; i < Math.round((double)currentWave / 3); i++) {
            enemiesToSpawn.add("BLOB");
        }
        if(currentWave % 5 == 0) {  // Spawn difficult enemy every 5 waves
            for (int i = 0; i < currentWave / 5; i++) {
                enemiesToSpawn.add("REX");
            }
        }
        spawnEnemyRunnable.setEnemiesToSpawn(enemiesToSpawn);
    }

    private void updateEnemyStats() {
        spawnEnemyRunnable.setStatMultiplier(new StatMultiplier(1 + currentWave*0.1, 1 + currentWave*0.1));
    }

    //Method will spawn enemies from enemiesToSpawnList with an interval of shortSpawnMs to longSpawnMs milliseconds
    private void spawnEnemies(SpawnEnemyRunnable spawnEnemyRunnable) {
        if(!spawnEnemyRunnable.getIsRunnableActive()) {   // Make sure only one spawn wave timer is active
            runOnce(spawnEnemyRunnable, Duration.ZERO);  // Start spawn runnable, (first one will spawn with no delay)
            spawnEnemyRunnable.setIsRunnableActive(true);
        }
    }

     //Creates a timer which will call generateNewWave() method after baseWaveTimeSec + getSpawnTimeSec() seconds
    private void startNewWaveTimer() {    // Begin wave timer
        stopWaveTimer();   // Stop and remove currentWaveTimer since a new one should start
        waveTimerAction = createWaveTimer();  // Create new timer, when timer reaches 0 it will generate wave
    }

    //Method will stop the current waveTimer if it exists
    public void stopWaveTimer() { if(waveTimerAction != null) waveTimerAction.expire(); }

    private TimerAction createWaveTimer() {
        return runOnce(() -> generateNewWave(), Duration.seconds(baseWaveTimeSec + getSpawnTimeSec()));
    }

    /**
     * Returns the amount of seconds it will take to spawn the current wave
     * @return integer seconds it takes to spawn wave
     */
    public int getSpawnTimeSec() {
        if(spawnEnemyRunnable.getEnemiesToSpawn().size() == 0) {
            System.out.println("SpawnTime cant be calculated because enemiesToSpawn list is empty");
            return 0;
        }
        return Math.round((spawnEnemyRunnable.getEnemiesToSpawn().size() * averageSpawnIntervalSec()) - averageSpawnIntervalSec());     // -averageSpawnTime since first enemy takes no time to spawn
    }

    //Method calculates on average how long it should take to spawn one enemy
    private float averageSpawnIntervalSec() {
        return (float)(spawnEnemyRunnable.getLongSpawnMs() + spawnEnemyRunnable.getShortSpawnMs()) / (2 * 1000);    // Divided by 1000 to convert ms to sec
    }

    /**
     * Getter for currentWave variable
     * @return integer currentWave
     */
    public int getCurrentWave() {
        return currentWave;
    }

    private void startTimerAllEnemiesDead() {
        // No enemies to spawn and no enemies left
        FXGL.getGameTimer().runAtInterval(() -> {
            if (FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY).size() == 0 && spawnEnemyRunnable.getEnemiesToSpawn().size() == 0) { // No enemies to spawn and no enemies left
                generateNewWave();
            }
        }, Duration.seconds(1));
    }
}