package edu.chalmers.model.wave;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.enemy.Enemy;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.Player;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class SpawnEnemyRunnable implements Runnable {
    private Random random = new Random();
    private ArrayList<Enemy> enemies;
    private ArrayList<String> enemiesToSpawn;
    private int shortSpawnMs;
    private int longSpawnMs;
    private Player p;

    private EnemyFactory enemyFactory = EnemyFactory.getInstance();
    private Point2D leftSpawnPoint = new Point2D(0, 520);
    private Point2D rightSpawnPoint = new Point2D(1000, 520);
    private boolean isRunnableActive = true;

    SpawnEnemyRunnable(ArrayList<Enemy> enemies, ArrayList<String> enemiesToSpawn, int shortSpawnMs, int longSpawnMs, Player p) {
        this.enemies = enemies;
        this.enemiesToSpawn = enemiesToSpawn;
        this.shortSpawnMs = shortSpawnMs;
        this.longSpawnMs = longSpawnMs;
        this.p = p;
    }

    private Point2D getRandomSpawnPoint() {
        if(random.nextInt(2) == 0) {    // Left side if random = 0
            return leftSpawnPoint;
        }
        else {  // Right side
            return rightSpawnPoint;
        }
    }

    public boolean getIsRunnableActive() {
        return isRunnableActive;
    }

    @Override
    public void run() {
        int spawnIndex = random.nextInt(enemiesToSpawn.size()); // Select random enemy from list
        enemies.add(enemyFactory.createEnemy(enemiesToSpawn.get(spawnIndex), getRandomSpawnPoint().getX(), getRandomSpawnPoint().getY(), p));   // Spawn an enemy randomly from list
        enemiesToSpawn.remove(spawnIndex);  // Enemy has been spawned so remove from enemiesToSpawn list

        if (enemiesToSpawn.size() > 0) {
            isRunnableActive = true;
            runOnce(this, Duration.millis(random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs));
        }
        else {  // No more enemies to spawn, runnable will no longer be active
            isRunnableActive = false;
        }
    }
}
