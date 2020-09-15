package edu.chalmers.model;

import javafx.scene.paint.Color;

/**
 * Rex class. A type of Enemy.
 */
public class Rex extends Enemy {

    public Rex(double x, double y, Player target) {
        super(Color.DARKRED, 200, 50, 60, 100, x, y, target);
    }
}
