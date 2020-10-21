package edu.chalmers.model.wave;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.enemy.StatMultiplier;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * @author Malte Åkvist
 *
 * A RunnableClass that spawns enemies.
 */
public class SpawnEnemyRunnable implements Runnable {
    private Random random = new Random();
    private List<String> enemiesToSpawn = new ArrayList<>();
    private StatMultiplier statMultiplier = new StatMultiplier();
    private int shortSpawnMs = 1000;    // Lowest time between enemies spawning
    private int longSpawnMs = 3000;    // Longest time between enemies spawning
    private Entity player;

    private EnemyFactory enemyFactory = EnemyFactory.getInstance();
    private boolean isRunnableActive = false;

    public SpawnEnemyRunnable(Entity player) {
        this.player = player;
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
     * Getter for enemies to spawn list
     * @return list enemies to spawn
     */
    public List<String> getEnemiesToSpawn() {
        return new ArrayList<>(enemiesToSpawn);
    }

    /**
     * Setter for enemies to spawn list
     * @param enemiesToSpawn enemies to spawn from wave
     */
    public void setEnemiesToSpawn(List<String> enemiesToSpawn) {
        this.enemiesToSpawn = enemiesToSpawn;
    }

    /**
     * Getter for longest time between enemies spawning
     * @return int long spawn ms
     */
    public int getLongSpawnMs() { return longSpawnMs; }

    /**
     * Getter for lowest time between enemies spawning
     * @return int short spawn ms
     */
    public int getShortSpawnMs() { return shortSpawnMs; }

    /**
     * Setter for statMultiplier
     * @param statMultiplier enemy stats
     */
    public void setStatMultiplier(StatMultiplier statMultiplier) {
        this.statMultiplier = statMultiplier;
    }

    /**
     * Method spawns all enemies in a time interval on a random spawn position
     */
    @Override
    public void run() {
        int spawnIndex = random.nextInt(enemiesToSpawn.size()); // Select random enemy from list

        Point2D spawnPoint = getGameWorld().getEntitiesByType(EntityType.ENEMYSPAWNPOINT).get(random.nextInt(2)).getPosition();   // Get a random spawn point for enemies
        enemyFactory.createEnemy(enemiesToSpawn.get(spawnIndex), spawnPoint.getX(), spawnPoint.getY(), player, statMultiplier);   // Spawn an enemy randomly from list

        enemiesToSpawn.remove(spawnIndex);  // Enemy has been spawned so remove from enemiesToSpawn list

        if (enemiesToSpawn.size() > 0) {
            runOnce(this, Duration.millis(random.nextInt(longSpawnMs - shortSpawnMs) + shortSpawnMs));
        }
        else {  // No more enemies to spawn, runnable will no longer be active
            isRunnableActive = false;
        }
    }
}
