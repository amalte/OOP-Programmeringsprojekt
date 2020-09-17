package edu.chalmers.model.enemy.enemytypes;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.enemy.Enemy;
import javafx.scene.paint.Color;


/**
 * Rex class. A type of Enemy.
 */
public class Rex extends Enemy {

    public Rex(double x, double y, Entity target) {
        super(Color.DARKRED, 200, 50, 60, 100, x, y, target);
    }
}
