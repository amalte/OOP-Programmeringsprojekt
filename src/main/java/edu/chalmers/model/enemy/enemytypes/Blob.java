package edu.chalmers.model.enemy.enemytypes;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.enemy.Enemy;
import javafx.scene.paint.Color;


/**
 * Zombie class. A type of Enemy.
 */
public class Blob extends Enemy {

    public Blob(double x, double y, Entity target) {
        super(Color.DARKORANGE, 75, 20, 100, 200, x, y, target);
    }
}
