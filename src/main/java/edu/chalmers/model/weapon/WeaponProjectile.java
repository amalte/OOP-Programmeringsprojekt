package edu.chalmers.model.weapon;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.model.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WeaponProjectile {

    Entity projectile;
    private PhysicsComponent physics = new PhysicsComponent();

    private double x;
    private double y;
    private Point2D mousePoint;
    private int projectileSpeed;
    private int shooterSizeOffsetToCenter = 22;
    private float projectileSizeW = 5;
    private float projectileSizeH = 5;

    public WeaponProjectile(double x, double y, Point2D mousePoint, int projectileSpeed) {

        this.mousePoint = mousePoint;
        this.x = x;
        this.y = y;
        this.projectileSpeed = projectileSpeed;

        physics.setBodyType(BodyType.KINEMATIC);
        projectile = FXGL.entityBuilder()
                .type(EntityType.PROJECTILE)
                .at((this.x+shooterSizeOffsetToCenter),(this.y+shooterSizeOffsetToCenter))
                .viewWithBBox(new Rectangle(projectileSizeW, projectileSizeH, Color.BLACK))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new ExpireCleanComponent(Duration.seconds(3)))
                .buildAndAttach();

        setAngularVelocity();
    }
    /**
     * Sets the angular velocity of the projectile.
     */
    private void setAngularVelocity() {
        double shootingAngle = calculateAngle();
        moveProjectileOutsidePlayerHitbox(shootingAngle);
        physics.setVelocityX(projectileSpeed*Math.cos(shootingAngle));
        physics.setVelocityY(projectileSpeed*Math.sin(shootingAngle));
    }

    /**
     * Moves the projectile outside the player hitbox
     * @param shootingAngle The angle between center of player and the mouse pointer
     */
    private void moveProjectileOutsidePlayerHitbox(double shootingAngle) {
        double spawnPointX = Math.cos(shootingAngle)*35+(x+shooterSizeOffsetToCenter)-(projectileSizeW/2);
        double spawnPointY = Math.sin(shootingAngle)*35+(y+shooterSizeOffsetToCenter)-(projectileSizeH/2);
        Point2D spawnPoint = new Point2D(spawnPointX,spawnPointY);
        physics.overwritePosition(spawnPoint);
    }

    /**
     * Calculates the angle between center of player and the mouse pointer
     * @return Angle between center of player and the mouse pointer
     */
    private double calculateAngle() {

        return FXGLMath.atan2(mousePoint.getY() - (y+shooterSizeOffsetToCenter),mousePoint.getX() - (x+shooterSizeOffsetToCenter));

    }
}
