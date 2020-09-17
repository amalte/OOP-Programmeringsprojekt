package edu.chalmers.model;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WeaponProjectile {

    Entity projectile;
    private PhysicsComponent physics = new PhysicsComponent();

    protected double x;
    protected double y;
    protected Point2D mousePoint;
    private int projectileSpeed = 650;
    private int shooterSizeOffsetToCenter = 22;

    public WeaponProjectile(double x, double y, Point2D mousePoint) {

        this.mousePoint = mousePoint;
        this.x = x;
        this.y = y;

        physics.setBodyType(BodyType.KINEMATIC);
        projectile = FXGL.entityBuilder()
                .type(EntityType.PROJECTILE)
                .at((this.x+shooterSizeOffsetToCenter),(this.y+shooterSizeOffsetToCenter))
                .viewWithBBox(new Rectangle(5, 5, Color.BLACK))
                .with(physics)
                .with(new ExpireCleanComponent(Duration.seconds(3)))
                .buildAndAttach();

        setAngularSpeed();
    }
    private void setAngularSpeed() {

        double shootingAngle = calculateAngle();

        double spawnPointX = Math.cos(shootingAngle)*35+(x+shooterSizeOffsetToCenter);
        double spawnPointY = Math.sin(shootingAngle)*35+(y+shooterSizeOffsetToCenter);
        Point2D spawnPoint = new Point2D(spawnPointX,spawnPointY);
        physics.overwritePosition(spawnPoint);

        physics.setVelocityX(projectileSpeed*Math.cos(shootingAngle));
        physics.setVelocityY(projectileSpeed*Math.sin(shootingAngle));

    }

    private double calculateAngle() {

        return FXGLMath.atan2(mousePoint.getY() - (y+shooterSizeOffsetToCenter),mousePoint.getX() - (x+shooterSizeOffsetToCenter));

    }
}
