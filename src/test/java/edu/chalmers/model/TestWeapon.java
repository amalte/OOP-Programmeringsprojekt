package edu.chalmers.model;

import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.model.weapon.Weapon;
import edu.chalmers.model.weapon.WeaponFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestWeapon {

    private Weapon weapon;

    @BeforeAll
    public static void initApplication() throws InterruptedException {
        SetupWorld.initApp();
    }

    @Test
    public void testShoot() {
        weapon = WeaponFactory.getInstance().createWeapon("Crossbow");
        assertEquals(weapon.getMagazineCounter(), 1);
        weapon.shoot(0,0);
        assertEquals(weapon.getMagazineCounter(), 0);
        weapon.shoot(0,0);
        assertEquals(weapon.getMagazineCounter(), 0);
    }

    @Test
    public void testReload() {
         weapon = WeaponFactory.getInstance().createWeapon("Handgun");
         weapon.shoot(0,0);
         weapon.shoot(0,0);
         assertEquals(weapon.getMagazineCounter(), 8);
         weapon.reload();
         assertEquals(weapon.getMagazineCounter(), 10);

    }

    @Test
    public void testGetDamage() {
        weapon = WeaponFactory.getInstance().createWeapon("Handgun");
        assertEquals(weapon.getDamage(), 45);
    }

}