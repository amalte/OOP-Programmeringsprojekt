package edu.chalmers.model;

import javafx.scene.paint.Color;

/**
 * Zombie class. A type of Enemy.
 */
public class Blob extends Enemy {

    public Blob(double x, double y, Player target) {
        super(Color.DARKORANGE, 75, 20, 100, 200, x, y, target);
    }
}
