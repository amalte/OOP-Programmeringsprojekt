package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * EnemyComponent class. When added to entity it becomes an Enemy.
 */
public abstract class EnemyComponent extends Component {

    protected PhysicsComponent physics;
    protected Color color;

    // STATS
    protected int health;
    protected int damage;
    protected int moveSpeed;
    protected int jumpHeight;

    public EnemyComponent(PhysicsComponent physics, Color color, int health, int damage, int moveSpeed, int jumpHeight) {
        this.physics = physics;
        physics.setBodyType(BodyType.DYNAMIC);

        this.health = health;
        this.damage = damage;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.color = color;
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
}
