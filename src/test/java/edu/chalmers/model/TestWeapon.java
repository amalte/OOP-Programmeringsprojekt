package edu.chalmers.model;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.model.weapon.Weapon;
import edu.chalmers.model.weapon.WeaponFactory;
import edu.chalmers.model.weapon.WeaponProjectile;
import javafx.geometry.Point2D;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWeapon {

    private Weapon weapon;


    @BeforeClass
    public static void setUp() throws InterruptedException {
        initialize();
    }

    @Test
    public void testProjectileAngularVelocity() throws InterruptedException {
        waitForRunLater(() -> {
            FXGL.getGameWorld().getEntities().clear();

            new WeaponProjectile(new Point2D(0, 0), FXGL.getAppCenter(), 500, true);

            double angle = FXGLMath.atan2(FXGL.getAppCenter().getY(), FXGL.getAppCenter().getX());

            Point2D projectileActualVelocity = FXGL.getGameWorld().getEntities().get(0).getComponent(PhysicsComponent.class).getLinearVelocity();
            Point2D projectileExpectedVelocity = new Point2D(500 * Math.cos(angle), 500 * Math.sin(angle));

            assertEquals((int) projectileExpectedVelocity.getY(), (int) projectileActualVelocity.getY());
            assertEquals((int) projectileExpectedVelocity.getX(), (int) projectileActualVelocity.getX());
        });

    }

    @Test
    public void testShoot() throws InterruptedException {
        waitForRunLater(() -> {
            weapon = WeaponFactory.getInstance().createWeapon("Crossbow");
            assertEquals(weapon.getMagazineCounter(), 1);
            weapon.shoot(0,0);
            assertEquals(weapon.getMagazineCounter(), 0);
            weapon.shoot(0,0);
            assertEquals(weapon.getMagazineCounter(), 0);}
        );

    }

    @Test
    public void testReload() throws InterruptedException {
        waitForRunLater(() -> {
            weapon = WeaponFactory.getInstance().createWeapon("Handgun");
            weapon.setTesting(true);
            weapon.shoot(0, 0);
            weapon.shoot(0, 0);
            assertEquals(weapon.getMagazineCounter(), 8);
            weapon.reload();
            assertEquals(weapon.getMagazineCounter(), 10);
        });
    }

    @Test
    public void testGetDamage() {
        weapon = WeaponFactory.getInstance().createWeapon("Handgun");
        assertEquals(weapon.getDamage(), 45);
    }

    @Test
    public void testObserverMethods() throws InterruptedException {
        MockObserver o = new MockObserver();
        weapon = WeaponFactory.getInstance().createWeapon("Handgun");
        assertTrue(weapon.observers.size() == 0);
        weapon.addObserver(o);
        assertTrue(weapon.observers.size() == 1);
        weapon.notifyObserver();
        assertTrue(o.isTest());
        weapon.removeObserver(o);
        assertTrue(weapon.observers.size() == 0);
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }
}