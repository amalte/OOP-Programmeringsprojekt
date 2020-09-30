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

    private RaycastResult higherHorizontalRaycast;
    private RaycastResult horizontalRaycast;
    private RaycastResult leftDownwardRaycast;
    private RaycastResult rightDownwardRaycast;
    private RaycastResult activeDownwardRaycast;
    private RaycastResult entityRaycast;
    private int higherHorizontalRaycastDeltaHeight = 20;    // Delta with entity's top Y-coordinate (how many pixels above entity the raycast should be placed).
    private int higherHorizontalRaycastLength = 40;
    private int horizontalRaycastLength = 30;
    private int downwardRaycastLength = 50;
    private int entityRaycastLength = 3;

    private boolean playerReached = false;
    private boolean nearbyEnemyPlayerReached = false;
    private boolean pathfindingOverride = false;

    public EnemyAIComponent(EnemyComponent thisEnemy, Entity player) {
        this.player = player;
        this.thisEnemy = thisEnemy;
    }

    @Override
    public void onUpdate(double tpf) {
        setMoveDirection();
        setRaycastsDirection();

        // Move towards Player if pathfinding haven't been overridden.
        if(!pathfindingOverride) {
            moveTowardsPlayer();
        }
        doJump();
        setPlayerReached(); // Must be after movement code.
        standingOnEnemyCheck();
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
        // +1 and -1 is used to indent the raycast position into the entity a bit, making it more accurate and able to catch entities with the same size.
        // Same goes for +3 and -3.

        leftDownwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, thisEnemy.getX() + 1, thisEnemy.getBottomY());
        rightDownwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, thisEnemy.getRightX() - 1, thisEnemy.getBottomY());

        if(moveDirection == Direction.LEFT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(-higherHorizontalRaycastLength, thisEnemy.getX(), thisEnemy.getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(-horizontalRaycastLength, thisEnemy.getX(), thisEnemy.getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(-entityRaycastLength, thisEnemy.getX(), thisEnemy.getY() + 3);
            activeDownwardRaycast = leftDownwardRaycast;
        }

        else if(moveDirection == Direction.RIGHT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(higherHorizontalRaycastLength, thisEnemy.getRightX(), thisEnemy.getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(horizontalRaycastLength, thisEnemy.getRightX(), thisEnemy.getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(entityRaycastLength, thisEnemy.getRightX(), thisEnemy.getY() + 3);
            activeDownwardRaycast = rightDownwardRaycast;
        }
    }

    /**
     * Method sets playerReached variable based on horizontalRaycast.
     */
    private void setPlayerReached() {
        if(entityRaycast == null) {
            return;
        }

        // Sets playerReached if the Player is hit by raycast.
        if(RaycastCalculations.checkRaycastHit(entityRaycast, EntityType.PLAYER)) {
            playerReached = true;
        } else {
            playerReached = false;
        }

        // Sets nearbyEnemyPlayerReached if another enemy is horizontally nearby
        if(RaycastCalculations.checkRaycastHit(entityRaycast, EntityType.ENEMY)) {
            nearbyEnemyPlayerReached = getNearbyEnemy().getComponent(EnemyAIComponent.class).isPlayerReached();
        } else {
            nearbyEnemyPlayerReached = false;
        }
    }

    /**
     * Method moves Enemy towards the player.
     */
    private void moveTowardsPlayer() {
        // Is Enemy to the right of Player AND Player is not reached by Enemy or nearby Enemy?
        if(isPlayerToLeft() && !playerReached && !nearbyEnemyPlayerReached) {
            thisEnemy.moveLeft();
        }
        // Is Enemy to the left of Player AND Player is not reached by Enemy or nearby Enemy?
        else if(isPlayerToRight() && !playerReached && !nearbyEnemyPlayerReached) {
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
        if(higherHorizontalRaycast == null || horizontalRaycast == null || activeDownwardRaycast == null) {
            return;
        }

        // TODO - higherHorizontalRaycast; check for hit with platform side.
        // IF:
        // higherHorizontalRaycast hit a platform (TODO - temporary)
        // horizontalRaycast hit a platform side *OR*
        // horizontalRaycast hit a Block *OR*
        // activeDownwardRaycast did *not* hit a platform (Enemy is walking of a platform).
        if(RaycastCalculations.checkRaycastHit(higherHorizontalRaycast, EntityType.PLATFORM) ||
                RaycastCalculations.checkRaycastHit(horizontalRaycast, EntityType.PLATFORMSIDE) ||
                RaycastCalculations.checkRaycastHit(horizontalRaycast, EntityType.BLOCK) ||
                !RaycastCalculations.checkRaycastHit(activeDownwardRaycast, EntityType.PLATFORM)) {

            // If Player is above Enemy; jump.
            if(isPlayerAbove()) {
                thisEnemy.jump();
            }
        }
    }

    /**
     * Method checks if the Enemy is standing on top of another Enemy and corrects it (moves him) if true.
     */
    private void standingOnEnemyCheck() {
        if(leftDownwardRaycast == null || rightDownwardRaycast == null) {
            return;
        }

        // Is Enemy standing on top of another Enemy?
        if(RaycastCalculations.checkRaycastHit(leftDownwardRaycast, EntityType.ENEMY) || RaycastCalculations.checkRaycastHit(rightDownwardRaycast, EntityType.ENEMY)) {

           // Activate pathfindingOverride if pathfinding is still running. Set variable to true.
           if(pathfindingOverride = false) {
               pathfindingOverride = true;
                return;
            }

            // Move right if Player is to the left (to counter below enemy's movement).
            if(isPlayerToLeft()) {
                thisEnemy.moveRight();
            }
            // Move left if Player is to the right (to counter below enemy's movement).
            else if(isPlayerToRight()) {
                thisEnemy.moveLeft();
            }
        }
        // If Enemy is not standing on top of another enemy:
        else {
            pathfindingOverride = false;
        }
    }

    /**
     * Method checks if another Enemy is horizontally nearby.
     * @return Returns nearby enemy if it exists.
     */
    private Entity getNearbyEnemy() {
        if(entityRaycast == null) {
            return null;
        }

        // Get Enemy entity
        Optional<Entity> optionalEntity = entityRaycast.getEntity();
        return optionalEntity.get();
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
