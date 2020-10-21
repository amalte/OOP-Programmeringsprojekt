package edu.chalmers.model.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import edu.chalmers.model.AnimationComponent;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.enemy.ai.EnemyAIComponent;
import edu.chalmers.model.enemy.enemytypes.Blob;
import edu.chalmers.model.enemy.enemytypes.Rex;
import edu.chalmers.model.enemy.enemytypes.Zombie;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * @author Sam Salek
 *
 * A factory used to create different types of Enemy entities.
 */
public class EnemyFactory {

    private static EnemyFactory instance;

    // Use 'getInstance' to get access
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
    public Entity createEnemy(String enemyName, double x, double y, Entity player, StatMultiplier statMultiplier) {
        if(enemyName == null) {
            return null;
        }

        if(enemyName.equalsIgnoreCase("ZOMBIE")) {
            EnemyComponent zombieComponent = new EnemyComponent(new Zombie(), statMultiplier);
            Entity zombie = buildEnemy(zombieComponent, x, y, player);
            getGameWorld().addEntity(zombie);
            return zombie;
        }
        else if(enemyName.equalsIgnoreCase("REX")) {
            EnemyComponent rexComponent = new EnemyComponent(new Rex(), statMultiplier);
            Entity rex = buildEnemy(rexComponent, x, y, player);
            getGameWorld().addEntity(rex);
            return rex;
        }
        else if(enemyName.equalsIgnoreCase("BLOB")) {
            EnemyComponent blobComponent = new EnemyComponent(new Blob(), statMultiplier);
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
        Entity entity = FXGL.entityBuilder().type(EntityType.ENEMY).at(x,y).bbox(new HitBox(BoundingShape.box(50,50))).build();

        entity.addComponent(enemyComponent);                                        // Add EnemyComponent
        entity.addComponent(enemyComponent.getPhysics());                           // Add PhysicsComponent
        entity.addComponent(new CollidableComponent(true));                         // Add CollidableComponent
        entity.addComponent(new EnemyAIComponent(enemyComponent, player));          // Add EnemyAIComponent
        entity.addComponent(new AnimationComponent(                                 // Add AnimationComponent
                enemyComponent.getEnemyType().getTextureIdle(),
                enemyComponent.getEnemyType().getTextureWalk(), enemyComponent.getEnemyType().getTextureJump()));

        return entity;

        // TODO - Simplify code and try to initiate all components in EnemyComponent instead.
    }
}
