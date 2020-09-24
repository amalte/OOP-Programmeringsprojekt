package edu.chalmers.model.enemy.enemytypes;

import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.model.enemy.EnemyComponent;
import javafx.scene.paint.Color;

/**
 * BlobComponent class. A type of EnemyComponent.
 */
public class BlobComponent extends EnemyComponent {

    public BlobComponent(PhysicsComponent physics) {
        super(physics, Color.DARKORANGE, 75, 20, 100, 200);
    }
}
