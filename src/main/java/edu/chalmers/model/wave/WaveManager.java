package edu.chalmers.model.wave;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.IObservable;
import edu.chalmers.model.IObserver;
import edu.chalmers.model.enemy.StatMultiplier;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * @author Malte Åkvist
 *
 * Class that handles waves in the game. Uses SpawnEnemyRunnable to spawn in enemies.
 */
public class WaveManager implements IObservable {
    private int currentWave = 0;
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
        increaseEnemyStats();
        baseWaveTimeSec += 5;   // Timer for how long a wave can last increases by 5 seconds each wave
        startNewWaveTimer();    // Will generateNewWave if timer reaches 0 (Warning will be infinite loop unless timer is stopped somewhere)
        spawnEnemies(spawnEnemyRunnable);
        notifyObserver();
    }

    //Method will add enemies that should spawn to the param list, which and how many enemies that are added depends on which wave it is
    private void calculateEnemiesToSpawn() {
        List<String> enemiesToSpawn = spawnEnemyRunnable.getEnemiesToSpawn();

        for (int i = 0; i < currentWave; i++) {
            enemiesToSpawn.add("ZOMBIE");
        }
        for (int i = 0; i < Math.round((double)currentWave / 2); i++) {
            enemiesToSpawn.add("BLOB");
        }
        if(currentWave % 3 == 0) {  // Spawn difficult enemy every 3 waves
            for (int i = 0; i < currentWave / 5; i++) {
                enemiesToSpawn.add("REX");
            }
        }
        spawnEnemyRunnable.setEnemiesToSpawn(enemiesToSpawn);
    }

    private void increaseEnemyStats() {
        spawnEnemyRunnable.setStatMultiplier(new StatMultiplier(1 + currentWave*0.05));   // Health of enemies increases by 5% every wave
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

    /**
     * Stop the current waveTimer, if it exists and is not expired.
     */
    public void stopWaveTimer() {
        if(waveTimerAction != null && !waveTimerAction.isExpired()) waveTimerAction.expire();
    }

    private TimerAction createWaveTimer() {
        return runOnce(this::generateNewWave, Duration.seconds(baseWaveTimeSec + getSpawnTimeSec()));
    }

    //Returns the amount of seconds it will take to spawn the current wave
    private int getSpawnTimeSec() {
        if(spawnEnemyRunnable.getEnemiesToSpawn().size() == 0) {
            return 0;   // SpawnTime cant be calculated because enemiesToSpawn list is empty
        }
        return Math.round((spawnEnemyRunnable.getEnemiesToSpawn().size() * averageSpawnIntervalSec()) - averageSpawnIntervalSec());     // -averageSpawnTime since first enemy takes no time to spawn
    }

    //Method calculates on average how long the spawn interval for each enemy is
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
        FXGL.getGameTimer().runAtInterval(() -> {
            if (FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY).size() == 0 && spawnEnemyRunnable.getEnemiesToSpawn().size() == 0) {  // No enemies to spawn and no enemies left
                generateNewWave();
            }
        }, Duration.seconds(1));
    }

    /**
     * Adds an observer to the observers list
     * @param o the observer to add
     */
    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    /**
     * Notifies the observers an update has happened
     */
    @Override
    public void notifyObserver() {
        for(IObserver o : observers){
            o.update();
        }
    }

    /**
     * Removes an observer from the observers list
     * @param o the observer to remove
     */
    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }
}