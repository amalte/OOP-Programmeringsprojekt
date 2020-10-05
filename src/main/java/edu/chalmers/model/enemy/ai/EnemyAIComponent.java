package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.model.enemy.StatMultiplier;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.utilities.RaycastCalculations;

import java.util.*;

/**
 * EnemyAIComponent class. Contains and gives basic Enemy AI to an Entity.
 */
public class EnemyAIComponent extends Component {

    private EnemyComponent thisEnemy;
    private Entity player;
    MovementAI movementAI;
    RaycastAI raycastAI;
    PlatformAI platformAI;

    private boolean pathfindingOverride = false;
    private boolean playerReached = false;
    private Entity target;

    public EnemyAIComponent(EnemyComponent thisEnemy, Entity player) {
        this.player = player;
        this.thisEnemy = thisEnemy;
        movementAI = new MovementAI(this);
        raycastAI = new RaycastAI(this);
        platformAI = new PlatformAI(this);

        target = player;
    }

    @Override
    public void onAdded() {
        platformAI.setPlatforms();
    }

    @Override
    public void onUpdate(double tpf) {
        movementAI.setMoveDirection();
        raycastAI.setRaycastsDirection();

        // Move towards Player if pathfinding haven't been overridden.
        if (!pathfindingOverride) {
            movementAI.moveTowardsTarget();
        }

        movementAI.doJump();
        setPlayerReached(); // Must be after movement code.
        movementAI.enemyStuckUnderPlatformFix();
        standingOnEnemyCheck();
        movementAI.reachPlayer();

        // Reset stat multiplier if Enemy is touching solid ground.
        if(!thisEnemy.isAirborne()) {
            thisEnemy.resetStatMultiplier();
        }
    }

    /**
     * Method sets playerReached variable based on horizontalRaycast.
     */
    private void setPlayerReached() {
        if (raycastAI.getEntityRaycast() == null) {
            return;
        }

        // Sets playerReached if the Player is hit by raycast.
        if (RaycastCalculations.checkRaycastHit((raycastAI.getEntityRaycast()), EntityType.PLAYER)) {
            playerReached = true;
        } else {
            playerReached = false;
        }
    }

    /**
     * Method checks if the Enemy is standing on top of another Enemy and corrects it (moves him) if true.
     */
    // TODO - Improve this. Sometimes still gets stuck on edge of enemies. Will not work when a bigger Enemy stands on top of a smaller.
    private void standingOnEnemyCheck() {
        if (raycastAI.getLeftDownwardRaycast() == null ||
                raycastAI.getRightDownwardRaycast() == null) {
            return;
        }

        // Is Enemy standing on top of another Enemy?
        if (RaycastCalculations.checkRaycastHit(raycastAI.getLeftDownwardRaycast(), EntityType.ENEMY) ||
                RaycastCalculations.checkRaycastHit(raycastAI.getRightDownwardRaycast(), EntityType.ENEMY)) {

            // Activate pathfindingOverride if pathfinding is still running. Set variable to true.
            if (pathfindingOverride = false) {
                pathfindingOverride = true;
                return;
            }

            // Move right if Player is to the left (to counter below enemy's movement).
            if (isEntityToLeft(player)) {
                thisEnemy.moveRight();
            }
            // Move left if Player is to the right (to counter below enemy's movement).
            else if (isEntityToRight(player)) {
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
    public EnemyAIComponent getNearbyEnemyAI() {
        if(raycastAI.getEntityRaycast() == null) {
            return null;
        }

        // If entityRaycast hit an Enemy (Enemy is nearby).
        if(RaycastCalculations.checkRaycastHit(raycastAI.getEntityRaycast(), EntityType.ENEMY)) {
            Optional<Entity> optionalEntity = raycastAI.getEntityRaycast().getEntity(); // Get Enemy entity
            return optionalEntity.get().getComponent(EnemyAIComponent.class);
        }

        // If no Enemy is nearby.
        return null;
    }

    // -------- GETTERS -------- //

    /**
     * Getter for movementAI variable.
     * @return movementAI.
     */
    public MovementAI getMovementAI() {
        return movementAI;
    }

    /**
     * Getter for raycastAI variable.
     * @return raycastAI.
     */
    public RaycastAI getRaycastAI() {
        return raycastAI;
    }

    /**
     * Getter for platformAI variable.
     * @return platformAI.
     */
    public PlatformAI getPlatformAI() {
        return platformAI;
    }

    /**
     * Getter for thisEnemy variable.
     * @return thisEnemy.
     */
    public EnemyComponent getThisEnemy() {
        return thisEnemy;
    }

    /**
     * Getter for player variable.
     * @return player.
     */
    public Entity getPlayer() {
        return player;
    }

    /**
     * Getter for pathfindingOverride variable.
     * @return pathfindingOverride.
     */
    public boolean isPathfindingOverride() {
        return pathfindingOverride;
    }

    /**
     * Getter for target variable.
     * @return target.
     */
    public Entity getTarget() {
        return target;
    }

    /**
     * Getter for playerReached variable.
     * @return playerReached variable.
     */
    public boolean isPlayerReached() {
        return playerReached;
    }

    // --------- SETTERS --------- //

    /**
     * Setter for pathfindingOverride variable.
     */
    public void setPathfindingOverride(boolean pathfindingOverride) {
        this.pathfindingOverride = pathfindingOverride;
    }

    public void setPlayerReached(boolean playerReached) {
        this.playerReached = playerReached;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    // --------- BOOLEANS --------- //

    /**
     * Method checks if the given entity is to the left of the Enemy.
     * @return True or false.
     */
    public boolean isEntityToLeft(Entity entity) {
        return EntityPos.getMiddleX(entity) - thisEnemy.getX() < 0;
    }

    /**
     * Method checks if the given entity is to the right of the Enemy.
     * @return True or false.
     */
    public boolean isEntityToRight(Entity entity) {
        return EntityPos.getMiddleX(entity) - thisEnemy.getRightX() > 0;
    }

    /**
     * Method checks if given entity is above the Enemy.
     * @return True or false.
     */
    public boolean isEntityAbove(Entity entity) {
        //return (entity.getY() + (entity.getHeight() / 2)) - thisEnemy.getY() < 0;
        return (entity.getY() - thisEnemy.getY()) < 0;
    }

    /**
     * Method checks if the given entity is below the Enemy.
     * @return True or false.
     */
    public boolean isEntityBelow(Entity entity) {
        return (entity.getY() + (entity.getHeight() / 2)) - thisEnemy.getY() > 0;
    }
}
