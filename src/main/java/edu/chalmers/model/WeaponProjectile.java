package edu.chalmers.model;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class WeaponProjectile {

    Entity projectile;
    private PhysicsComponent physics = new PhysicsComponent();

    protected double x;
    protected double y;
    protected Point mousePoint;

    public WeaponProjectile(double x, double y, Point mousePoint) {

        this.x = x;
        this.y = y;
        this.mousePoint = mousePoint;

        physics.setBodyType(BodyType.KINEMATIC);
        projectile = FXGL.entityBuilder().type(EntityType.PROJECTILE).at(x,y).viewWithBBox(new Rectangle(5, 5, Color.BLACK)).with(physics).collidable().buildAndAttach();

        setAngularSpeed();
    }
    private void setAngularSpeed() {
        double angle = calculateAngle();
        physics.setVelocityX(650*Math.cos(angle));
        physics.setVelocityY(650*Math.sin(angle));

    }

    private double calculateAngle() {

        return FXGLMath.atan2((mousePoint.y-130) - y,(mousePoint.x-438) - x);

    }



}
