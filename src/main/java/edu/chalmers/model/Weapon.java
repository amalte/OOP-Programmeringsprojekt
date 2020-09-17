package edu.chalmers.model;


import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;


public class Weapon {


    /**
     * Creates an instance of WeaponProjectile
     * @param x Players x-position
     * @param y Players y-position
     */
    public void shoot(double x, double y) {
        new WeaponProjectile(x, y, mouseLocation());
    }

    /**
     * Gets the mouse pointer location in the game world
     * @return mouse pointer location in the game world as a Point2D
     */
    private Point2D mouseLocation() {
        return FXGL.getInput().getMousePositionWorld();

    }
}
