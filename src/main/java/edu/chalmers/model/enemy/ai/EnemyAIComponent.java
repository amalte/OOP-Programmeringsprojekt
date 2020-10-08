package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.model.enemy.EnemyComponent;
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

        // Check most recent platform the player was in contact with
        if(!getPlayerComponent().isAirborne()) {
            getPlatformAI().playerRecentPlatformContactCheck();
        }

        // Move towards Player if pathfinding haven't been overridden.
        if (!pathfindingOverride) {
            movementAI.moveTowardsTarget();
        }

        enemyAboveOrBelowFix();        // Must be before doJump() and multiplier reset.
        movementAI.doJump();
        setPlayerReached();             // Must be after movement code.
        movementAI.enemyStuckUnderPlatformFix();

        // If number of platforms is 4 (map is level1): Activate floatingPlatformMovement.
        if(platformAI.getPlatforms().size() == 4) {
            movementAI.floatingPlatformMovement();
        }

        //movementAI.followPlayerDown();

        // Reset move speed and jump height if Enemy is touching solid ground.
        if(!thisEnemy.isAirborne()) {
            thisEnemy.setMoveSpeedMultiplier(1);
            thisEnemy.setJumpHeightMultiplier(1);
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
     * Method reduces mobility issues with enemies when another Enemy is on top of them.
     */
    private void enemyAboveOrBelowFix() {
        if(enemyAbove() || enemyBelow()) {
            thisEnemy.setJumpHeightMultiplier(2);
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
     * Getter for player's PlayerComponent class.
     * @return player.
     */
    public PlayerComponent getPlayerComponent() {
        return player.getComponent(PlayerComponent.class);
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
        return EntityPos.getMiddleX(entity) - EntityPos.getMiddleX(thisEnemy.getEntity()) < 0;
    }

    /**
     * Method checks if the given entity is to the right of the Enemy.
     * @return True or false.
     */
    public boolean isEntityToRight(Entity entity) {
        return EntityPos.getMiddleX(entity) - EntityPos.getMiddleX(thisEnemy.getEntity()) > 0;
    }

    /**
     * Method checks if given entity's middle Y-position is above the Enemy.
     * @return True or false.
     */
    public boolean isEntityMiddleYAbove(Entity entity) {
        return (entity.getY() + (entity.getHeight() / 2)) - thisEnemy.getY() < 0;
    }

    /**
     * Method checks if given entity's bottom Y-position is above the Enemy.
     * @return True or false.
     */
    public boolean isEntityBottomYAbove(Entity entity) {
        return (entity.getBottomY() - thisEnemy.getY()) < 0;
    }

    /**
     * Method checks if the given entity is below the Enemy.
     * @return True or false.
     */
    public boolean isEntityBelow(Entity entity) {
        return (entity.getY() + (entity.getHeight() / 2)) - thisEnemy.getY() > 0;
    }

    /**
     * Method checks if the given entity is below the Enemy.
     * @return True or false.
     */
    public boolean isEntitySameY(Entity entity) {
        return Math.abs(entity.getBottomY() - thisEnemy.getBottomY()) < 5;
    }

    /**
     * Method checks if there is an Enemy above this entity.
     * @return True or False.
     */
    public boolean enemyAbove() {
        return RaycastCalculations.checkRaycastHit(raycastAI.getLeftUpwardRaycast(), EntityType.ENEMY) ||
                RaycastCalculations.checkRaycastHit(raycastAI.getRightUpwardRaycast(), EntityType.ENEMY);
    }

    /**
     * Method checks if there is an Enemy on below this entity.
     * @return True or False.
     */
    public boolean enemyBelow() {
       return RaycastCalculations.checkRaycastHit(raycastAI.getLeftDownwardRaycast(), EntityType.ENEMY) ||
                RaycastCalculations.checkRaycastHit(raycastAI.getRightDownwardRaycast(), EntityType.ENEMY);
    }
}
