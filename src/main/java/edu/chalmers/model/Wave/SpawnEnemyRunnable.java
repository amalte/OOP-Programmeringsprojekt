package edu.chalmers.model.Wave;

import com.almasb.fxgl.entity.SpawnData;
import edu.chalmers.model.EnemyFactory;
import edu.chalmers.model.Player;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class SpawnEnemyRunnable implements Runnable {
    Random random;
    int shortSpawnMs;
    int longSpawnMs;
    ArrayList<String> enemiesToSpawn;
    Player p;
    SpawnData leftSpawnPoint = new SpawnData(0, 520);
    SpawnData rightSpawnPoint = new SpawnData(1000, 520);

    SpawnEnemyRunnable(Random random, int shortSpawnMs, int longSpawnMs, ArrayList<String> enemiesToSpawn, Player p) {
        this.random = random;
        this.shortSpawnMs = shortSpawnMs;
        this.longSpawnMs = longSpawnMs;
        this.enemiesToSpawn = enemiesToSpawn;
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
        enemyFactory.createEnemy(enemiesToSpawn.get(spawnIndex), getRandomSpawnPoint().getX(), getRandomSpawnPoint().getY(), p);

        enemiesToSpawn.remove(spawnIndex);

        if (enemiesToSpawn.size() > 0) {
            runOnce(new SpawnEnemyRunnable(random, shortSpawnMs, longSpawnMs, enemiesToSpawn, p), Duration.millis(random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs));
        }
    }
}
