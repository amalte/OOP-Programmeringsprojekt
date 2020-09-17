package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Player class. Wraps an entity object as a Player.
 */
public class Player {

    private Entity entity;
    private PhysicsComponent physics = new PhysicsComponent();
    Weapon weapon = new Weapon();

    //Stats
    private int health = 100;
    private int moveSpeed = 150;
    private int jumpHeight = 350;
    private final int amountOfJumps = 1;
    private int jumps = amountOfJumps;

    public Player(double x, double y) {
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        entity = FXGL.entityBuilder().type(EntityType.PLAYER).at(x,y).viewWithBBox(new Rectangle(50, 50, Color.BLUE)).with(physics).with(new CollidableComponent(true)).buildAndAttach();
    }

    /**
     * Get method used fot testing purposes.
     * @return integer jumps jumps
     */
    public int getJumps() {
        return jumps;
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
     * Method moves players Entity up (negative y) with jumpHeight if the player have any jumps left.
     * Reduce amount of jumps left by 1.
     */
    public void jump(){
        if(jumps != 0) {
            physics.setVelocityY(-jumpHeight);
            jumps--;
        }
    }
    public void shoot() {
        weapon.shoot(getX(),getY());
    }

    /**
     * Method stop players Entity in the x direction.
     */
    public void stop(){
        physics.setVelocityX(0);
    }

    /**
     * Resets players jumps to be equal to amountOfJumps variable.
     */
    public void resetJumpAmounts(){
        jumps = amountOfJumps;
    }

    // -------------- GETTERS -------------- //
    /**
     * Method returns the actual player Entity.
     * @return The player Entity.
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Gets the player entity's left side X-position.
     * @return Player X-position.
     */
    public double getX() {
        return entity.getX();
    }

    /**
     * Gets the player entity's right side X-position.
     * @return Player X-position.
     */
    public double getRightX() {
        return entity.getRightX();
    }

    /**
     * Gets the player entity's top side Y-position.
     * @return Player Y-position.
     */
    public double getY() {
        return entity.getY();
    }

    /**
     * Gets the player entity's bottom side Y-position.
     * @return Player Y-position.
     */
    public double getBottomY() {
        return entity.getBottomY();
    }
}
