package edu.chalmers.model;


import java.awt.*;

public class Weapon {



    public void shoot(double x, double y) {
        new WeaponProjectile(x, y, mouseLocation());
    }
    private Point mouseLocation() {
        return MouseInfo.getPointerInfo().getLocation();
    }
}
