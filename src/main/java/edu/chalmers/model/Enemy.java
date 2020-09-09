package edu.chalmers.model;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Entity {

    private PhysicsComponent physics = new PhysicsComponent();
    private EntityBuilder builder = new EntityBuilder();

    public Enemy(double x, double y) {
        physics.setBodyType(BodyType.DYNAMIC);
        Entity enemy = builder.at(x,y).viewWithBBox(new Rectangle(50, 50, Color.RED)).with(physics).buildAndAttach();
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

