package edu.chalmers.model.enemy.enemytypes;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.enemy.Enemy;
import javafx.scene.paint.Color;

/**
 * Zombie class. A type of Enemy.
 */
public class Zombie extends Enemy {

    public Zombie(double x, double y, Entity target) {
        super(Color.DARKGREEN, 100, 25, 80, 150, x, y, target);
    }
}
