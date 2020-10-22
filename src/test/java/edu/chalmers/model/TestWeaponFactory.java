package edu.chalmers.model;

import edu.chalmers.model.weapon.Weapon;
import edu.chalmers.model.weapon.WeaponFactory;
import edu.chalmers.model.weapon.weapontypes.Crossbow;
import edu.chalmers.model.weapon.weapontypes.Handgun;
import edu.chalmers.model.weapon.weapontypes.ThrowingKnife;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.chalmers.FXGLTest.deInitialize;
import static edu.chalmers.FXGLTest.initialize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Erik Wetter
 * <p>
 * Test class for WeaponFactory.
 */
public class TestWeaponFactory {

    private Weapon weapon;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        initialize();
    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    @Test
    public void testThrowingKnife() {
        weapon = WeaponFactory.getInstance().createWeapon("ThrowingKnife");
        assertEquals(weapon.getWeaponType().getClass(), ThrowingKnife.class);
    }

    @Test
    public void testCrossbow() {
        weapon = WeaponFactory.getInstance().createWeapon("Crossbow");
        assertEquals(weapon.getWeaponType().getClass(), Crossbow.class);

    }

    @Test
    public void testHandgun() {
        weapon = WeaponFactory.getInstance().createWeapon("Handgun");
        assertEquals(weapon.getWeaponType().getClass(), Handgun.class);
    }

    @Test
    public void testWrongWeaponName() {
        weapon = WeaponFactory.getInstance().createWeapon("abc123");
        assertNull(weapon);
    }
}
