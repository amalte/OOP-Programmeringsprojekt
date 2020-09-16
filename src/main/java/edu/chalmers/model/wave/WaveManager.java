package edu.chalmers.model.wave;

import com.almasb.fxgl.core.concurrent.Executor;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.enemy.Enemy;
import edu.chalmers.model.Player;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.*;
import java.util.concurrent.Executors;

/**
 * A class that handles waves in the game (it spawns in new enemies)
 */
public class WaveManager {

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<String> enemiesToSpawn = new ArrayList<String>();

    int currentWave = 1;
    int shortSpawnMs = 1500;    // Lowest time between enemies spawning
    int longSpawnMs = 2000;    // Longest time between enemies spawning
    TimerAction spawnWave;   // Timer which will be used to spawn enemies

    SpawnEnemyRunnable spawnEnemyRunnable;  // Spawn enemies in a time interval
    Player p;

    public WaveManager(Player p) {
        this.p = p;
        spawnEnemyRunnable = new SpawnEnemyRunnable(enemies, enemiesToSpawn, shortSpawnMs, longSpawnMs, p);
    }

    public int getAmountOfEnemies() { return enemies.size(); }

    public void generateNewWave() {
        currentWave++;
        calculateEnemiesToSpawn();

        if(spawnWave == null || !spawnEnemyRunnable.getIsRunnableActive()) {   // Make sure only one spawn wave timer is active
            spawnWave = runOnce(spawnEnemyRunnable, Duration.ZERO);  // Start spawn runnable, (first one will spawn with no delay)
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

        for (int i = 0; i < Math.round((double)currentWave / 3); i++) {
            //enemiesToSpawn.add("Enemy2");
        }
        if(currentWave % 5 == 0) {  // Spawn difficult enemy every 5 waves
            for (int i = 0; i < currentWave / 5; i++) {
                //enemiesToSpawn.add("Enemy3");
            }
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



