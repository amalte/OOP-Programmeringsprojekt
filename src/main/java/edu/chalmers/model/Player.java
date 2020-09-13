package edu.chalmers.model;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Player class. Wraps a entity object as a Player.
 */
public class Player {

    Entity player;
    private EntityBuilder entityBuilder = new EntityBuilder();
    private PhysicsComponent physics = new PhysicsComponent();

    // STATS
    protected int health = 100;
    protected int moveSpeed = 150;
    protected int jumpHeight = 350;

    public Player(double x, double y) {
        physics.setBodyType(BodyType.DYNAMIC);
        player = entityBuilder.at(x,y).viewWithBBox(new Rectangle(50, 50, Color.BLUE)).with(physics).buildAndAttach();
    }

    /**
     * Method moves players Entity left (negative x).
     */
    public void moveLeft(){
        physics.setVelocityX(-moveSpeed);
    }

    /**
     * Method moves players Entity right (positive x).
     */
    public void moveRight(){
        physics.setVelocityX(moveSpeed);
    }

    /**
     * Method moves players Entity up (negative y).
     */
    public void jump(){
        physics.setVelocityY(-jumpHeight);
    }

    /**
     * Method stop players Entity in the x direction.
     */
    public void stop(){
        physics.setVelocityX(0);
    }

    /**
     * Method returns the actual player Entity.
     * @return The player Entity.
     */
    public Entity getEntity() {
        return player;
    }

    /**
     * Gets the player entity's X-position from its PhysicsComponent.
     * @return Player X-position.
     */
    public float getX() {
        return getEntity().getComponent(PhysicsComponent.class).getBody().getTransform().p.x;
    }

    /**
     * Gets the player entity's Y-position from its PhysicsComponent.
     * @return Player Y-position.
     */
    public float getY() {
        return getEntity().getComponent(PhysicsComponent.class).getBody().getTransform().p.y;
    }
}
