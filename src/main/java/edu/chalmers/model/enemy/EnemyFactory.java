package edu.chalmers.model.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.enemy.enemytypes.Blob;
import edu.chalmers.model.enemy.enemytypes.Rex;
import edu.chalmers.model.enemy.enemytypes.Zombie;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

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
     * @param enemyName Name of the entity which should be created.
     * @param x X-Position where entity should be created.
     * @param y Y-Position where entity should be created.
     * @param player A reference to the Player entity.
     * @return Returns a Enemy entity.
     */
    public Entity createEnemy(String enemyName, double x, double y, Entity player) {
        if(enemyName == null) {
            return null;
        }

        if(enemyName == "ZOMBIE") {
            EnemyComponent zombieComponent = new EnemyComponent(new Zombie());
            Entity zombie = buildEnemy(zombieComponent, x, y, player);
            getGameWorld().addEntity(zombie);
            return zombie;
        }
        else if(enemyName == "REX") {
            EnemyComponent rexComponent = new EnemyComponent(new Rex());
            Entity rex = buildEnemy(rexComponent, x, y, player);
            getGameWorld().addEntity(rex);
            return rex;
        }
        else if(enemyName == "BLOB") {
            EnemyComponent blobComponent = new EnemyComponent(new Blob());
            Entity blob = buildEnemy(blobComponent, x, y, player);
            getGameWorld().addEntity(blob);
            return blob;
        }

        // Return null if String "enemyName" doesn't match any known type of Enemy.
        return null;
    }

    /**
     * Method builds an enemy entity with the given arguments.
     * @param enemyComponent The type of enemy entity which should be built.
     * @param x X-Position where entity should be built.
     * @param y Y-Position where entity should be built.
     * @param player Reference to the player. Used for the PathfindingComponent.
     * @return Returns the enemy entity.
     */
    private Entity buildEnemy(EnemyComponent enemyComponent, double x, double y, Entity player) {
        Entity entity = FXGL.entityBuilder().type(EntityType.ENEMY).at(x,y).viewWithBBox(new Rectangle(40, 40, enemyComponent.getColor())).build();

        entity.addComponent(enemyComponent);                                        // Add EnemyComponent
        entity.addComponent(enemyComponent.getPhysics());                           // Add PhysicsComponent
        entity.addComponent(new CollidableComponent(true));                         // Add CollidableComponent
        entity.addComponent(new PathfindingComponent(enemyComponent, player));      // Add PathfindingComponent

        return entity;

        // TODO - Simplify code and initiate all components in EnemyComponent instead.
    }
}
