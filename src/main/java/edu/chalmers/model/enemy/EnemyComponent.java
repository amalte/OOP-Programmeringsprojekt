package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.model.enemy.enemytypes.IEnemyType;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * EnemyComponent class. When added to entity it becomes an Enemy.
 */
public class EnemyComponent extends Component {

    IEnemyType enemyType;
    private PhysicsComponent physics;

    // STATS
    private Color color;
    private int health;
    private int damage;
    private int moveSpeed;
    private int jumpHeight;

    public EnemyComponent(IEnemyType enemyType) {
        this.enemyType = enemyType;

        physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        this.color = enemyType.getColor();
        this.health = enemyType.getHealth();
        this.damage = enemyType.getDamage();
        this.moveSpeed = enemyType.getMoveSpeed();
        this.jumpHeight = enemyType.getJumpHeight();
    }

    /**
     * Method moves enemy Entity left (negative x).
     */
    public void moveLeft(){
        physics.setVelocityX(-moveSpeed);
    }

    /**
     * Method moves enemy Entity right (positive x).
     */
    public void moveRight(){
        physics.setVelocityX(moveSpeed);
    }

    /**
     * Method moves enemy Entity up (negative y).
     */
    public void jump(){
        physics.setVelocityY(-jumpHeight);
    }

    /**
     * Method stop enemy Entity in the x direction.
     */
    public void stop(){
        physics.setVelocityX(0);
    }

    /**
     * Method kills the Entity and removes it from the game world.
     */
    public void die() {
        getGameWorld().removeEntity(entity);
    }

    /**
     * Gets the enemy entity's X-position from its PhysicsComponent.
     * @return Enemy X-position.
    */
    public double getX() {
        return entity.getX();
    }

    /**
     * Gets the enemy entity's right side X-position.
     * @return Enemy X-position.
    */
    public double getRightX() {
        return entity.getRightX();
    }

    /**
     * Gets the enemy entity's Y-position from its PhysicsComponent.
     * @return Enemy Y-position.
    */
    public double getY() {
        return entity.getY();
    }

    /**
     * Gets the enemy entity's bottom side Y-position.
     * @return Enemy Y-position.
    */
    public double getBottomY() {
        return entity.getBottomY();
    }

    /**
     * Gets the PhysicsComponent attached to this Component.
     * @return
     */
    public PhysicsComponent getPhysics() {
        return physics;
    }

    /**
     * Gets the Color of the Enemy entity.
     * @return The Color of entity.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for variable damage.
     * @return The amount of damage the enemy can inflict on an other Entity.
     */
    public int getDamage(){
        return damage;
    }

    /**
     * Lower Enemy's health with damage.
     * @param damage The amount of incoming damage.
     */
    public void inflictDamage(int damage){
        health -= damage;
    }

    /**
     * Getter for variable health.
     * @return The health of the Enemy.
     */
    public int getHealth(){
        return health;
    }
}
