package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.RaycastResult;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.utilities.RaycastCalculations;

import java.util.Optional;

/**
 * EnemyAIComponent class. Contains and gives basic Enemy AI to an Entity.
 */
public class EnemyAIComponent extends Component {

    enum Direction {LEFT, RIGHT}

    private EnemyComponent thisEnemy;
    private Entity player;
    private Direction moveDirection;
    private RaycastResult aboveHorizontalRaycast;
    private RaycastResult horizontalRaycast;
    private RaycastResult downwardRaycast;
    private RaycastResult playerReachedRaycast;

    private int aboveHorizontalRaycastLength = 40;
    private int horizontalRaycastLength = 30;
    private int downwardRaycastLength = 50;
    private int playerReachedRaycastLength = 3;
    private boolean playerReached = false;

    public EnemyAIComponent(EnemyComponent thisEnemy, Entity player) {
        this.player = player;
        this.thisEnemy = thisEnemy;
    }

    @Override
    public void onUpdate(double tpf) {
        setMoveDirection();
        setRaycastsDirection();

        moveTowardsPlayer();
        doJump();
        setPlayerReached(); // Must be after movement code

        // TESTING
        System.out.println(isPlayerAbove());
        checkOtherEnemy();
    }

    /**
     * Method sets moveDirection based on Player position.
     */
    private void setMoveDirection() {
        if(isPlayerToLeft()) {
            moveDirection = Direction.LEFT;
        }
        else if(isPlayerToRight()) {
            moveDirection = Direction.RIGHT;
        }
    }

    /**
     * Method sets correct direction of raycast variables based on moveDirection.
     */
    private void setRaycastsDirection() {

        if(moveDirection == Direction.LEFT) {
            aboveHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(-aboveHorizontalRaycastLength, thisEnemy.getX(), thisEnemy.getY() - 20);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(-horizontalRaycastLength, thisEnemy.getX(), thisEnemy.getY());
            downwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, thisEnemy.getX(), thisEnemy.getBottomY());
            playerReachedRaycast = RaycastCalculations.setHorizontalRaycast(-playerReachedRaycastLength, thisEnemy.getX(), thisEnemy.getY());
        }

        else if(moveDirection == Direction.RIGHT) {
            aboveHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(aboveHorizontalRaycastLength, thisEnemy.getRightX(), thisEnemy.getY() - 20);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(horizontalRaycastLength, thisEnemy.getRightX(), thisEnemy.getY());
            downwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, thisEnemy.getRightX(), thisEnemy.getBottomY());
            playerReachedRaycast = RaycastCalculations.setHorizontalRaycast(playerReachedRaycastLength, thisEnemy.getRightX(), thisEnemy.getY());
        }
    }

    /**
     * Method sets playerReached variable based on horizontalRaycast.
     */
    private void setPlayerReached() {
        if(horizontalRaycast == null) {
            return;
        }

        if(RaycastCalculations.checkRaycastHit(playerReachedRaycast, EntityType.PLAYER)) {
            playerReached = true;
        } else {
            playerReached = false;
        }
    }

    /**
     * Method moves Enemy towards the player.
     */
    private void moveTowardsPlayer() {
        // Is Enemy to the right of Player AND Player is not reached?
        if(isPlayerToLeft() && !playerReached) {
            thisEnemy.moveLeft();
        }
        // Is Enemy to the left of Player AND Player is not reached?
        else if(isPlayerToRight() && !playerReached) {
            thisEnemy.moveRight();
        }
        // Player has been reached; stop moving.
        else {
            thisEnemy.stop();
        }
    }

    /**
     * Jump method. Makes Enemy jump when needed.
     */
    private void doJump() {
        if(horizontalRaycast == null) {
            return;
        }

        // TODO - aboveHorizontalRaycast; check for hit with platform side.
        // IF:
        // aboveHorizontalRaycast hit a platform (TODO - temporary)
        // horizontalRaycast hit a platform side *OR*
        // horizontalRaycast hit a Block *OR*
        // downwardRaycast did *not* hit a platform (Enemy is walking of a platform).
        if(RaycastCalculations.checkRaycastHit(aboveHorizontalRaycast, EntityType.PLATFORM) ||
                RaycastCalculations.checkRaycastHit(horizontalRaycast, EntityType.PLATFORMSIDE) ||
                RaycastCalculations.checkRaycastHit(horizontalRaycast, EntityType.BLOCK) ||
                !RaycastCalculations.checkRaycastHit(downwardRaycast, EntityType.PLATFORM)) {

            // If Player is above Enemy; jump.
            if(isPlayerAbove()) {
                thisEnemy.jump();
            }
        }
    }

    private void checkOtherEnemy() {
        if(horizontalRaycast == null) {
            return;
        }

        if(RaycastCalculations.checkRaycastHit(horizontalRaycast, EntityType.ENEMY)) {
            // Get Enemy entity
            Optional<Entity> optionalEntity = horizontalRaycast.getEntity();
            Entity entity = optionalEntity.get();
            boolean otherEnemyReachedPlayer = entity.getComponent(EnemyAIComponent.class).isPlayerReached();
            //System.out.println(entity.getX());

            // TODO - not working?
        }
    }

    /**
     * Method checks if the Player is to the left of the Enemy entity.
     * @return True or false.
     */
    private boolean isPlayerToLeft() {
        return player.getRightX() - thisEnemy.getX() < 0;
    }

    /**
     * Method checks if the Player is to the right of the Enemy entity.
     * @return True or false.
     */
    private boolean isPlayerToRight() {
        return player.getX() - thisEnemy.getRightX() > 0;
    }

    /**
     * Method checks if the Player is above the Enemy entity.
     * @return True or false.
     */
    private boolean isPlayerAbove() {
        return (player.getY() + (player.getHeight() / 2)) - thisEnemy.getY() < 0;
    }

    /**
     * Getter for playerReached variable.
     * @return playerReached variable.
     */
    public boolean isPlayerReached() {
        return playerReached;
    }
}
