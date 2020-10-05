package edu.chalmers.model.wave;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.enemy.StatMultiplier;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * A RunnableClass that spawns enemies.
 */
public class SpawnEnemyRunnable implements Runnable {
    private Random random = new Random();
    private List<String> enemiesToSpawn;
    private int shortSpawnMs;
    private int longSpawnMs;
    private Entity player;

    private EnemyFactory enemyFactory = EnemyFactory.getInstance();
    private Point2D leftSpawnPoint = new Point2D(0, 520);
    private Point2D rightSpawnPoint = new Point2D(1000, 520);
    private boolean isRunnableActive = false;

    SpawnEnemyRunnable(List<String> enemiesToSpawn, int shortSpawnMs, int longSpawnMs, Entity player) {
        this.enemiesToSpawn = enemiesToSpawn;
        this.shortSpawnMs = shortSpawnMs;
        this.longSpawnMs = longSpawnMs;
        this.player = player;
    }

    private Point2D getRandomSpawnPoint() {
        if(random.nextInt(2) == 0) {    // Left side if random = 0
            return leftSpawnPoint;
        }
        else {  // Right side
            return rightSpawnPoint;
        }
    }

    /**
     * Getter for if the runnable is active
     * @return boolean isRunnableActive
     */
    public boolean getIsRunnableActive() {
        return isRunnableActive;
    }

    /**
     * Set isRunnableActive variable
     * @param value the boolean value to set
     */
    public void setIsRunnableActive(boolean value) {
         isRunnableActive = value;
    }

    /**
     * Method spawns all enemies in a time interval on a random spawn position
     */
    @Override
    public void run() {
        int spawnIndex = random.nextInt(enemiesToSpawn.size()); // Select random enemy from list
        StatMultiplier statMultiplier = new StatMultiplier();
        enemyFactory.createEnemy(enemiesToSpawn.get(spawnIndex), getRandomSpawnPoint().getX(), getRandomSpawnPoint().getY(), player, statMultiplier);   // Spawn an enemy randomly from list
        enemiesToSpawn.remove(spawnIndex);  // Enemy has been spawned so remove from enemiesToSpawn list

        if (enemiesToSpawn.size() > 0) {
            runOnce(this, Duration.millis(random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs));
        }
        else {  // No more enemies to spawn, runnable will no longer be active
            isRunnableActive = false;
        }
    }
}
