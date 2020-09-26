package edu.chalmers.model.weapon;


import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;

import java.util.Timer;
import java.util.TimerTask;


public abstract class Weapon {

    private int magazineSize;
    private int magazineAmmo;
    private int reloadTimerMilliseconds;
    private boolean needReloading = false;
    private int damage;


    public Weapon(int magazineSize, int reloadTimerMilliseconds, int damage) {
        this.magazineSize = magazineSize;
        this.reloadTimerMilliseconds = reloadTimerMilliseconds;
        this.damage = damage;
        magazineAmmo = magazineSize;
    }

    /**
     * Creates an instance of WeaponProjectile
     * @param x Players x-position
     * @param y Players y-position
     */
    public void shoot(double x, double y) {
        if (magazineAmmo > 0) {
            magazineAmmo--;
            new WeaponProjectile(x, y, mouseLocation());
        }else {
            needReloading = true;
        }
    }


    private Point2D mouseLocation() {
        return FXGL.getInput().getMousePositionWorld();

    }

    public int getDamage(){
        return damage;
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
                magazineAmmo = magazineSize;
                timer.cancel();
                needReloading = false;
            }
        }, reloadTimerMilliseconds);
    }
}
