package edu.chalmers.model;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player{

    private PhysicsComponent physics = new PhysicsComponent();
    private EntityBuilder builder = new EntityBuilder();

    public Player(int x, int y) {
        physics.setBodyType(BodyType.DYNAMIC);
        Entity playerTest = builder.at(x,y).viewWithBBox(new Rectangle(50, 50, Color.BLUE)).with(physics).with(new CollidableComponent(true)).buildAndAttach();

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
     * Method moves players Entity up (negative y).
     */
    public void jump(){
        physics.setVelocityY(-300);
    }

    /**
     * Method stop players Entity in the x direction.
     */
    public void stop(){
        physics.setVelocityX(0);
    }
}
