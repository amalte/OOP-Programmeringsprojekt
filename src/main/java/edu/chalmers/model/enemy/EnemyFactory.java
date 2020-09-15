package edu.chalmers.model.enemy;

import edu.chalmers.model.enemy.enemytypes.Blob;
import edu.chalmers.model.enemy.enemytypes.Rex;
import edu.chalmers.model.enemy.enemytypes.Zombie;
import edu.chalmers.model.Player;

/**
 * A factory used to create different types of Enemy entities.
 */

public class EnemyFactory {

    private static EnemyFactory instance;

    // Use 'getInstance' to get access to class
    private EnemyFactory(){}

    /**
     * Singleton. Gets instance of this class, and creates one if instance doesn't already exist.
     * @return Returns the singleton instance of the class.
     */
    public static EnemyFactory getInstance() {
        if(instance == null) {
            instance = new EnemyFactory();
        }

        return instance;
    }

    /**
     * Method creates a Enemy entity.
     * @param enemyName Name of the entity.
     * @param xPosition X-Position where entity should be created.
     * @param yPosition Y-Position where entity should be created.
     * @param player A reference to the Player entity.
     * @return Returns a Enemy entity.
     */
    public Enemy createEnemy(String enemyName, double xPosition, double yPosition, Player player) {
        if(enemyName == null) {
            return null;
        }

        if(enemyName == "ZOMBIE") {
            return new Zombie(xPosition, yPosition, player);
        }
        else if(enemyName == "REX") {
            return new Rex(xPosition, yPosition, player);
        }
        else if(enemyName == "BLOB") {
            return new Blob(xPosition, yPosition, player);
        }

        // Return null if String "enemyName" doesn't match any known type of Enemy.
        return null;
    }
}
