package edu.chalmers.model.enemy.enemytypes;

import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.model.enemy.EnemyComponent;
import javafx.scene.paint.Color;

/**
 * ZombieComponent class. A type of EnemyComponent.
 */
public class ZombieComponent extends EnemyComponent {

    public ZombieComponent(PhysicsComponent physics) {
        super(physics, Color.DARKGREEN, 100, 25, 80, 150);
    }
}
