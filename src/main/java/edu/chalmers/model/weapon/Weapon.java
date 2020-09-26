package edu.chalmers.model.weapon;


import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.model.weapon.weapontypes.IWeaponType;
import javafx.geometry.Point2D;

import java.util.Timer;
import java.util.TimerTask;


public class Weapon {

    IWeaponType weaponType;

    private int magazineSize;
    private int reloadTimerMilliseconds;
    private int damage;
    private int projectileSpeed;
    private int magazineCounter;
    private boolean needReloading = false;

    public Weapon(IWeaponType weaponType) {
        this.weaponType = weaponType;

        this.magazineSize = weaponType.getMagazineSize();
        this.reloadTimerMilliseconds = weaponType.getReloadTimeMilliseconds();
        this.damage = weaponType.getDamage();
        this.projectileSpeed = weaponType.getProjectileSpeed();
        magazineCounter = magazineSize;
    }

    /**
     * Creates an instance of WeaponProjectile
     * @param x Players x-position
     * @param y Players y-position
     */
    public void shoot(double x, double y) {
        if (magazineCounter > 0) {
            magazineCounter--;
            new WeaponProjectile(x, y, mouseLocation(), projectileSpeed);
        }else {
            needReloading = true;
        }
    }

    /**
     * Resets the magazineAmmo counter after a delay specified by reloadTimerMilliseconds
     */
    public void reload() {
        needReloading = true;

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                magazineCounter = magazineSize;
                timer.cancel();
                needReloading = false;
            }
        }, reloadTimerMilliseconds);
    }

    private Point2D mouseLocation() {
        return FXGL.getInput().getMousePositionWorld();

    }

    public int getDamage(){
        return damage;
    }


}
