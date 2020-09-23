package edu.chalmers.model.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.model.EntityType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * Enemy class. Wraps a entity object as a Enemy.
 */
public abstract class Enemy {

    private Entity entity;
    private PhysicsComponent physics = new PhysicsComponent();

    // STATS
    private int health;
    private int damage;
    private int moveSpeed;
    private int jumpHeight;

    public Enemy(Color color, int health, int damage, int moveSpeed, int jumpHeight, double x, double y, Entity target) {
        this.health = health;
        this.damage = damage;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;

        physics.setBodyType(BodyType.DYNAMIC);
        entity = FXGL.entityBuilder().type(EntityType.ENEMY).at(x,y).viewWithBBox(new Rectangle(40, 40, color)).with(new CollidableComponent(true)).with(physics, new EnemyComponent(this, target)).buildAndAttach();
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
     * Method returns the actual enemy Entity.
     * @return The enemy Entity.
     */
    public Entity getEntity() {
        return entity;
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
