package edu.chalmers.model.wave;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.enemy.Enemy;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.*;

/**
 * A class that handles waves in the game (it spawns in new enemies)
 */
public class WaveManager {

    private ArrayList<String> enemiesToSpawn = new ArrayList<String>();
    private int currentWave = 1;
    private int shortSpawnMs = 1000;    // Lowest time between enemies spawning
    private int longSpawnMs = 3000;    // Longest time between enemies spawning
    private TimerAction spawnWave;   // Timer which will be used to spawn enemies

    private SpawnEnemyRunnable spawnEnemyRunnable;  // Spawn enemies in a time interval

    public WaveManager(Entity player) {
        spawnEnemyRunnable = new SpawnEnemyRunnable(enemiesToSpawn, shortSpawnMs, longSpawnMs, player);
    }

    public void generateNewWave() {
        currentWave++;
        calculateEnemiesToSpawn();

        if(!spawnEnemyRunnable.getIsRunnableActive()) {   // Make sure only one spawn wave timer is active
            spawnWave = runOnce(spawnEnemyRunnable, Duration.ZERO);  // Start spawn runnable, (first one will spawn with no delay)
            spawnEnemyRunnable.setIsRunnableActive(true);
        }
    }

    public int getCurrentWave() {
        return currentWave;
    }

    private void calculateEnemiesToSpawn() {
        for (int i = 0; i < currentWave; i++) {
            enemiesToSpawn.add("ZOMBIE");
        }
        for (int i = 0; i < Math.round((double)currentWave / 3); i++) {
            //enemiesToSpawn.add("Enemy2");
        }
        if(currentWave % 5 == 0) {  // Spawn difficult enemy every 5 waves
            for (int i = 0; i < currentWave / 5; i++) {
                //enemiesToSpawn.add("Enemy3");
            }
        }
    }

    public int getSpawnTimeSec() {
        if(enemiesToSpawn.size() == 0) {
            System.out.println("SpawnTime cant be calculated because enemiesToSpawn list is empty");
            return 0;
        }
        return Math.round((enemiesToSpawn.size() * averageSpawnIntervalSec()) - averageSpawnIntervalSec());     // -averageSpawnTime since first enemy takes no time to spawn
    }

    private float averageSpawnIntervalSec() {
        return (float)(longSpawnMs + shortSpawnMs) / (2 * 1000);    // Divided by 1000 to convert ms to sec
    }


    /* OPTIONAL public class EnemyToSpawn {
        String name;
        int amount;

        public EnemyToSpawn(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public string getEnemy() {
            if(amount <= 0) {
                return null;
            }
            amount--;
            return name;
        }

        public void removeAmount(int amountToRemove) {
            amount -= amountToRemove;
        }

        public void removeAmount() {
            amount -= 1;
        }
    }

       public void calculateEnemiesToSpawnTEST() {

        enemyToSpawnList.add(new EnemyToSpawn("Enemy1", currentWave));
        enemyToSpawnList.add(new EnemyToSpawn("Enemy2", (int)Math.round((double)currentWave/3)));
        if(currentWave % 5 == 0) {
            enemyToSpawnList.add(new EnemyToSpawn("Enemy3", currentWave/5));
        }
    }

      private class SpawnEnemyTask extends TimerTask {
        private Timer timer;
        private Random random;
        private GameWorld gameWorld;

        SpawnEnemyTask(Timer timer, Random random, GameWorld gameWorld) {
            this.timer = timer;
            this.random = random;
            this.gameWorld = gameWorld;
        }

        @Override
        public void run() {
            int spawnIndex = random.nextInt(enemyToSpawnList.size());
            gameWorld.spawn(enemyToSpawnList.get(spawnIndex).getName(), getRandomSpawnPoint()); // Spawns in random enemy from enemiesToSpawn list
            enemyToSpawnList.get(spawnIndex).removeAmount();
            if(enemyToSpawnList.get(spawnIndex).amount <= 0) {
                enemyToSpawnList.remove(spawnIndex);
            }

            if(enemiesToSpawn.size() == 0) {    // Stop spawning enemies when list is empty
                timer.cancel();
            }
            else {
                timer.schedule(new SpawnEnemyTask(timer, random, gameWorld), random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs);
            }
        }
    }*/
}



