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

    private double thisEnemyPreviousXPosition = Double.NaN;

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

        int stopFollowRange = 50;

        // Is Enemy to the right of Player?
        if(isEnemyToRightOfPlayer(stopFollowRange)) {
            thisEnemy.moveLeft();
        }
        // Is Enemy to the left of Player?
        else if(isEnemyToLeftOfPlayer(stopFollowRange)) {
            thisEnemy.moveRight();
        }
    }

    private boolean isEnemyToRightOfPlayer(double distance) {
        return player.getRightX() - thisEnemy.getX() < -distance;
    }

    private boolean isEnemyToLeftOfPlayer(double distance) {
        return player.getX() - thisEnemy.getRightX() > distance;
    }

    /**
     * Temporary basic jump AI functionality.
     * Method makes the Entity jump when stuck on obstacles.
     */
    private void jump() {
        // TODO - Improve AI

        // Is 'thisEnemyPreviousXPosition' not set?  Give the variable its first value and return.
        if(Double.isNaN(thisEnemyPreviousXPosition)) {
            thisEnemyPreviousXPosition = thisEnemy.getX();
            return;
        }

        // Is Entity's current X same as its X in previous frame? If true: Entity is either stuck or standing next to Player.
        float precisionRange = 0.01f; // Precision range value.
        if(Math.abs(thisEnemy.getX() - thisEnemyPreviousXPosition) < precisionRange) {

            // Is Entity not standing next to the Player? Then probably stuck. Jump.
            if(isEnemyToRightOfPlayer(100) || isEnemyToLeftOfPlayer(100)) {
                thisEnemy.jump();
            }
        }

        // Set value for next method call.
        thisEnemyPreviousXPosition = thisEnemy.getX();
    }
}
