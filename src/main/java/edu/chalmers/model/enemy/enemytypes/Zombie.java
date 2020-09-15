package edu.chalmers.model.enemy.enemytypes;

import edu.chalmers.model.enemy.Enemy;
import edu.chalmers.model.Player;
import javafx.scene.paint.Color;

/**
 * Zombie class. A type of Enemy.
 */
public class Zombie extends Enemy {

    public Zombie(double x, double y, Player target) {
        super(Color.DARKGREEN, 100, 25, 80, 150, x, y, target);
    }
}
