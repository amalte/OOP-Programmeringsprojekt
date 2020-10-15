package edu.chalmers.model;

import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.model.weapon.Weapon;
import edu.chalmers.model.weapon.WeaponFactory;
import edu.chalmers.model.weapon.weapontypes.Crossbow;
import edu.chalmers.model.weapon.weapontypes.Handgun;
import edu.chalmers.model.weapon.weapontypes.ThrowingKnife;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static edu.chalmers.FXGLTest.deInitialize;
import static edu.chalmers.FXGLTest.initialize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(RunWithFX.class)
public class TestWeaponFactory {

    private Weapon weapon;

    @BeforeClass
    public static void setUp() throws InterruptedException {
        initialize();
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

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }
}
