package edu.chalmers.model;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player{

    private PhysicsComponent physics = new PhysicsComponent();
    private EntityBuilder builder = new EntityBuilder();

    public Player(int x, int y) {
        physics.setBodyType(BodyType.DYNAMIC);
        Entity playerTest = builder.at(x,y).viewWithBBox(new Rectangle(50, 50, Color.BLUE)).with(physics).buildAndAttach();

    }

    public void moveLeft(){
        physics.setVelocityX(-150);
    }

    public void moveRight(){
        physics.setVelocityX(150);
    }

    public void jump(){
        physics.setVelocityY(-100);
    }

    public void stop(){
        physics.setVelocityX(0);
    }
}

