package edu.chalmers.model;

/**
 * Zombie class. A type of Enemy.
 */
public class Zombie extends Enemy {

    public Zombie(double x, double y, Player target) {
        super(100, 25, 75, 50, x, y, target);
    }
}
