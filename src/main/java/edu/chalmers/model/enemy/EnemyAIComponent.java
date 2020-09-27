package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.Cell;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.RaycastResult;
import com.almasb.fxgl.physics.box2d.dynamics.Fixture;
import javafx.geometry.Point2D;

import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;

/**
 * PathfindingComponent class. Contains and gives basic pathfinding "AI" to an Entity.
 */
public class PathfindingComponent extends Component {

    enum Direction {LEFT, RIGHT}

    private EnemyComponent thisEnemy;
    private Entity player;
    private Direction moveDirection;
    private RaycastResult horizontalRaycast;
    private int horizontalRaycastLength = 30;

    private double thisEnemyPreviousXPosition = Double.NaN;

    public PathfindingComponent(EnemyComponent thisEnemy, Entity player) {
        this.player = player;
        this.thisEnemy = thisEnemy;
    }

    @Override
    public void onUpdate(double tpf) {
        setMoveDirection();
        setHorizontalRaycastDirection();

        moveTowardsPlayer();
        doJump();

        //System.out.println(getHorizontalRaycastHit());
        //jump();

        /*
        Point2D thisPosition = new Point2D(thisEnemy.getX(), thisEnemy.getY());
        RaycastResult raycastResult = getPhysicsWorld().raycast(thisPosition, new Point2D(thisPosition.getX(), thisPosition.getY() - 70));

        if(!raycastResult.getEntity().equals(Optional.empty())) {
            System.out.println(raycastResult.getEntity());
        }
        */

        // TIMER TEST
        /*
        getGameTimer().runOnceAfter(() -> {
            // ...
        }, Duration.seconds(2));
         */
    }

    private void setMoveDirection() {
        if(isPlayerToLeft(0)) {
            moveDirection = Direction.LEFT;
        }
        else if(isPlayerToRight(0)) {
            moveDirection = Direction.RIGHT;
        }
    }

    private void setHorizontalRaycastDirection() {
        Point2D thisEnemyPosition = new Point2D(thisEnemy.getX(), thisEnemy.getY());

        // Point raycast to the left
        if(moveDirection == Direction.LEFT) {
            horizontalRaycast = getPhysicsWorld().raycast(thisEnemyPosition, new Point2D(thisEnemy.getX() - horizontalRaycastLength, thisEnemy.getY()));
        }

        // Point raycast to the right
        else if(moveDirection == Direction.RIGHT) {
            horizontalRaycast = getPhysicsWorld().raycast(thisEnemyPosition, new Point2D(thisEnemy.getRightX() + horizontalRaycastLength, thisEnemy.getY()));
        }
    }

    /**
     * Moves Enemy towards the player.
     */
    private void moveTowardsPlayer() {

        int stopFollowRange = 50;

        // Is Enemy to the right of Player?
        if(isPlayerToLeft(stopFollowRange)) {
            thisEnemy.moveLeft();
        }
        // Is Enemy to the left of Player?
        else if(isPlayerToRight(stopFollowRange)) {
            thisEnemy.moveRight();
        }
    }

    private void doJump() {
        if(horizontalRaycast == null) {
            return;
        }

        // if *not* nothing is hit
        if(!horizontalRaycast.getEntity().equals(Optional.empty())) {

            if(horizontalRaycastHitPlatformSide()) {
                thisEnemy.jump();
            }
        }
    }

    private boolean horizontalRaycastHitPlayer() {
        String hitString = horizontalRaycast.getEntity().toString();
        return hitString.contains("PLAYER");
    }

    private boolean horizontalRaycastHitPlatformSide() {
        String hitString = horizontalRaycast.getEntity().toString();
        return hitString.contains("PLATFORMSIDE");
    }

    private String getHorizontalRaycastHitString() {
        return horizontalRaycast.getEntity().toString();
    }

    /**
     * Method checks if the Enemy entity is to the right of the Player entity.
     * @param distance Distance from player to exclude in the check. Ex: if distance = 50, the check will ignore the first 50 pixels to the right of player.
     * @return True or false.
     */
    private boolean isPlayerToLeft(double distance) {
        return player.getRightX() - thisEnemy.getX() < -distance;
    }

    /**
     * Method checks if the Enemy entity is to the left of the Player entity.
     * @param distance Distance from player to exclude in the check. Ex: if distance = 50, the check will ignore the first 50 pixels to the left of player.
     * @return True or false.
     */
    private boolean isPlayerToRight(double distance) {
        return player.getX() - thisEnemy.getRightX() > distance;
    }



    /**
     * Temporary basic jump AI functionality.
     * Method makes the Entity jump when stuck on obstacles.
    */
    @Deprecated
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
            if(isPlayerToLeft(100) || isPlayerToRight(100)) {
                thisEnemy.jump();
            }
        }

        // Set value for next method call.
        thisEnemyPreviousXPosition = thisEnemy.getX();
    }
}
