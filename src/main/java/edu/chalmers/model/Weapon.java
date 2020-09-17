package edu.chalmers.model;


import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;


public class Weapon {


    public void shoot(double x, double y) {
        new WeaponProjectile(x, y, mouseLocation());
    }
    private Point2D mouseLocation() {
        return FXGL.getInput().getMousePositionWorld();

    }
}
