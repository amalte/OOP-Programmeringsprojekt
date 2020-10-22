package edu.chalmers.model.weapon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.model.EntityType;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.utilities.Point2DCalculations;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * @author Erik Wetter
 * <p>
 * WeaponProjectile class. Spawns a projectile that moves from the spawn point towards the given target.
 */
public class WeaponProjectile {

    private PhysicsComponent physics = new PhysicsComponent();

    private Point2D centerPlayerPoint;
    private Point2D mousePoint;
    private int projectileSpeed;
    private int shooterSizeOffsetToCenter = 29;
    private float projectileSizeW = 5;
    private float projectileSizeH = 5;
    private Double shootingAngle;
    private boolean testing = false; //Boolean used for testing

    public WeaponProjectile(Point2D playerPoint, Point2D mousePoint, int projectileSpeed, boolean testing) {

        this.mousePoint = mousePoint;
        this.projectileSpeed = projectileSpeed;
        if (!testing) {
            this.centerPlayerPoint = new Point2D(playerPoint.getX() + shooterSizeOffsetToCenter, playerPoint.getY() + shooterSizeOffsetToCenter);
        } else {
            this.centerPlayerPoint = playerPoint;
        }

        physics.setBodyType(BodyType.KINEMATIC);
        FXGL.entityBuilder()
                .type(EntityType.PROJECTILE)
                .at(this.centerPlayerPoint.getX(), this.centerPlayerPoint.getY())
                .viewWithBBox(new Rectangle(projectileSizeW, projectileSizeH, Color.BLACK))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new ExpireCleanComponent(Duration.seconds(3)))
                .buildAndAttach();

        initMovement();
    }

    private void initMovement() {
        shootingAngle = calculateAngle();
        moveProjectileOutsidePlayerHitbox(shootingAngle);
        setAngularVelocity();
    }

    /**
     * Sets the angular velocity of the projectile.
     */
    private void setAngularVelocity() {
        physics.setVelocityX(projectileSpeed * Math.cos(shootingAngle));
        physics.setVelocityY(projectileSpeed * Math.sin(shootingAngle));
    }

    /**
     * Moves the projectile outside the player hitbox
     *
     * @param shootingAngle The angle between center of player and the mouse pointer
     */
    private void moveProjectileOutsidePlayerHitbox(double shootingAngle) {
        double spawnPointX = Math.cos(shootingAngle) * (shooterSizeOffsetToCenter + 15) + centerPlayerPoint.getX() - (projectileSizeW / 2);
        double spawnPointY = Math.sin(shootingAngle) * (shooterSizeOffsetToCenter + 15) + centerPlayerPoint.getY() - (projectileSizeH / 2);
        Point2D spawnPoint = new Point2D(spawnPointX, spawnPointY);
        physics.overwritePosition(spawnPoint);
    }

    /**
     * Calculates the angle between center of player and the mouse pointer
     *
     * @return Angle between center of player and the mouse pointer
     */
    private double calculateAngle() {
        return Point2DCalculations.getAngle(centerPlayerPoint, mousePoint);
    }
}
