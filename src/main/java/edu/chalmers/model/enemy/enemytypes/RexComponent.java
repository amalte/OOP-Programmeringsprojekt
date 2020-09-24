package edu.chalmers.model.enemy.enemytypes;

import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.model.enemy.EnemyComponent;
import javafx.scene.paint.Color;

/**
 * RexComponent class. A type of EnemyComponent.
 */
public class RexComponent extends EnemyComponent {

    public RexComponent(PhysicsComponent physics) {
        super(physics, Color.DARKRED, 200, 50, 60, 100);
    }
}
