package edu.chalmers.model.wave;

import com.almasb.fxgl.entity.SpawnData;
import edu.chalmers.model.enemy.Enemy;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.Player;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class SpawnEnemyRunnable implements Runnable {
    ArrayList<Enemy> enemies;
    ArrayList<String> enemiesToSpawn;
    Random random;
    int shortSpawnMs;
    int longSpawnMs;
    Player p;
    SpawnData leftSpawnPoint = new SpawnData(0, 520);
    SpawnData rightSpawnPoint = new SpawnData(1000, 520);

    SpawnEnemyRunnable(ArrayList<Enemy> enemies, ArrayList<String> enemiesToSpawn, Random random, int shortSpawnMs, int longSpawnMs, Player p) {
        this.enemies = enemies;
        this.enemiesToSpawn = enemiesToSpawn;
        this.random = random;
        this.shortSpawnMs = shortSpawnMs;
        this.longSpawnMs = longSpawnMs;
        this.p = p;
    }

    public SpawnData getRandomSpawnPoint() {
        if(random.nextInt(2) == 0) {    // Left side if random = 0
            return leftSpawnPoint;
        }
        else {  // Right side
            return rightSpawnPoint;
        }
    }

    @Override
    public void run() {
        int spawnIndex = random.nextInt(enemiesToSpawn.size());

        EnemyFactory enemyFactory = EnemyFactory.getInstance();
        enemies.add(enemyFactory.createEnemy(enemiesToSpawn.get(spawnIndex), getRandomSpawnPoint().getX(), getRandomSpawnPoint().getY(), p));

        enemiesToSpawn.remove(spawnIndex);

        if (enemiesToSpawn.size() > 0) {
            runOnce(new SpawnEnemyRunnable(enemies, enemiesToSpawn, random, shortSpawnMs, longSpawnMs, p), Duration.millis(random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs));
        }
    }
}
