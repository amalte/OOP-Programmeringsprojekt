package edu.chalmers.model;

import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

/**
 * EnemyComponent class. Contains very simple Enemy "AI".
 * Gives basic Enemy "AI" functionality when attached to a Enemy entity as a Component.
 */
public class EnemyComponent extends Component {

    // TODO - implement A* pathfinding

    private Enemy thisEnemy;
    private Player player;

    private float thisEnemyPreviousXPosition = Float.NaN;

    public EnemyComponent(Enemy thisEnemy, Player player) {
        this.player = player;
        this.thisEnemy = thisEnemy;
    }

    @Override
    public void onUpdate(double tpf) {
        followPlayer();
        jump();

        // TIMER TEST
        /*
        getGameTimer().runOnceAfter(() -> {
            // ...
        }, Duration.seconds(2));
         */
    }

    /**
     * Moves towards the player.
     */
    private void followPlayer() {
        if(player.getX() - thisEnemy.getX() < 0) {
            thisEnemy.moveLeft();
        } else if(player.getX() - thisEnemy.getX() > 0) {
            thisEnemy.moveRight();
        }
    }

    /**
     * Temporary basic jump AI functionality.
     * Method makes the Entity jump when stuck on obstacles.
     */
    private void jump() {
        // TODO - Improve AI

        // Is 'thisEnemyPreviousXPosition' not set?  Give the variable its first value and return.
        if(Float.isNaN(thisEnemyPreviousXPosition)) {
            System.out.println("FLOAT.NAN CONFIRMED");
            thisEnemyPreviousXPosition = thisEnemy.getX();
            return;
        }

        // Is Entity's current X same as its X in previous frame? If true: Entity is probably stuck. Jump.
        float precisionRange = 0.01f; // Precision range value.
        if(Math.abs(thisEnemy.getX() - thisEnemyPreviousXPosition) < precisionRange) {
            thisEnemy.jump();
        }

        // Set value for next method call.
        thisEnemyPreviousXPosition = thisEnemy.getX();
    }
}
