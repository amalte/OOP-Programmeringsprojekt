package edu.chalmers.model;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.TimerAction;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.*;

/**
 * A class that handles waves in the game (it spawns in new enemies)
 */
public class WaveManager {

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<Enemy> enemiesToSpawn = new ArrayList<Enemy>();

    //ArrayList<EnemyToSpawn> enemyToSpawnList = new ArrayList<EnemyToSpawn>();   // TEMPORARY

    int currentWave = 1;
    int shortSpawnMs = 500;    // Lowest time between enemies spawning
    int longSpawnMs = 1000;    // Longest time between enemies spawning
    // EnemyFactory enemyFactory = EnemyFactory.get(); // Used to spawn in enemies
    SpawnData leftSpawnPoint = new SpawnData(0, 100);
    SpawnData rightSpawnPoint = new SpawnData(300, 100);
    Random random = new Random();
    Timer timer = new Timer();



    public boolean isWaveActive() {
        if(enemies.size() > 0) {
            return true;
        }
        return false;
    }

    public void GenerateNewWave(GameWorld gameWorld) {
        currentWave++;
        calculateEnemiesToSpawn();
        timer.schedule(new SpawnEnemyTask(timer, random, gameWorld), random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs);

        // OPTION 2 TimerAction timerAction = runOnce(new SpawnEnemyRunnable(random), Duration.millis(random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs));
    }

    public int getCurrentWave() {
        return currentWave;
    }

    /**
     * A Task class that spawns in enemies in an interval
     */
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
            int spawnIndex = random.nextInt(enemiesToSpawn.size());
            // enemyFactory.spawn(enemiesToSpawn.get(spawnIndex));  // Spawns in random enemy from enemiesToSpawn list into enemies list
            gameWorld.spawn(enemiesToSpawn.get(spawnIndex).toString(), getRandomSpawnPoint()); // Spawns in random enemy from enemiesToSpawn list

            enemiesToSpawn.remove(spawnIndex);

            if(enemiesToSpawn.size() == 0) {    // Stop spawning enemies when list is empty
                timer.cancel();
            }
            else {
                timer.schedule(new SpawnEnemyTask(timer, random, gameWorld), random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs);
            }
        }
    }

    public void calculateEnemiesToSpawn() {
        for (int i = 0; i < currentWave; i++) {
            //enemiesToSpawn.add("Enemy1");
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

    public SpawnData getRandomSpawnPoint() {
        if(random.nextInt(2) == 0) {    // Left side if random = 0
            return leftSpawnPoint;
        }
        else {  // Right side
            return rightSpawnPoint;
        }
    }



    // OPTIONAL CODE \\


    /* OPTION 2 TIMER  class SpawnEnemyRunnable implements Runnable {
        Random random;

        SpawnEnemyRunnable(Random random) {
            this.random = random;
        }

        @Override
        public void run() {
            int spawnIndex = random.nextInt(enemiesToSpawn.size());
            // enemyFactory.spawn(enemiesToSpawn.get(spawnIndex));  // Spawns in random enemy from enemiesToSpawn list into enemies list
            enemies.add(enemiesToSpawn.get(spawnIndex));   // Adds random enemy from enemiesToSpawn list into enemies list TEMPORARY
            enemiesToSpawn.remove(spawnIndex);

            if (enemiesToSpawn.size() > 0) {
                runOnce(new SpawnEnemyRunnable(random), Duration.millis(random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs));
            }
        }
    } */


    /* OPTION 3  public class EnemyToSpawn {
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



