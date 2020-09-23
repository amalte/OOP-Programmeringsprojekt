package edu.chalmers.model;


import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;

import java.util.Timer;
import java.util.TimerTask;


public class Weapon {

    private int magazineAmmo = 10;
    private int reloadTimerMilliseconds = 3000;
    private boolean needReloading = false;
    private int damage = 10;


    /**
     * Creates an instance of WeaponProjectile
     * @param x Players x-position
     * @param y Players y-position
     */
    public void shoot(double x, double y) {
        if (!needReloading && magazineAmmo > 0) {
            magazineAmmo--;
            new WeaponProjectile(x, y, mouseLocation());
        }else {
            needReloading = true;
        }
    }

    /**
     * Gets the mouse pointer location in the game world
     * @return mouse pointer location in the game world as a Point2D
     */
    private Point2D mouseLocation() {
        return FXGL.getInput().getMousePositionWorld();

    }

    public int getDamage(){
        return damage;
    }

    /**
     * Resets the magazineAmmo counter after a delay specified by reloadTimerMilliseconds
     */
    private void reload() {
        needReloading = true;

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                magazineAmmo = 10;
                timer.cancel();
                needReloading = false;
            }
        }, reloadTimerMilliseconds);
    }
}
