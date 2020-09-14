package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {

    private PhysicsComponent physics = new PhysicsComponent();

    private final int amountOfJumps = 1;
    private int jumps = amountOfJumps;
    public Entity player;

    public Player(double x, double y) {
        physics.setBodyType(BodyType.DYNAMIC);
        player = FXGL.entityBuilder().type(EntityType.PLAYER).at(x,y).viewWithBBox(new Rectangle(50, 50, Color.BLUE)).with(physics).with(new CollidableComponent(true)).buildAndAttach();

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
        physics.setVelocityX(-150);
    }

    /**
     * Method moves players Entity right (positive x).
     */
    public void moveRight(){
        physics.setVelocityX(150);
    }

    /**
     * Method moves players Entity up (negative y) if the player have any jumps left.
     */
    public void jump(){
        if(jumps != 0) {
            physics.setVelocityY(-300);
            jumps--;
            System.out.println(jumps);
        }
    }

    /**
     * Method stop players Entity in the x direction.
     */
    public void stop(){
        physics.setVelocityX(0);
    }

    /**
     * Reset players amount of jumps.
     */
    public void resetJumpAmounts(){
        jumps = amountOfJumps;
    }
}
