package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.utilities.RaycastCalculations;

import java.util.Optional;

/**
 * EnemyAIComponent class. Contains and gives basic Enemy AI to an Entity.
 */
public class EnemyAIComponent extends Component {

    private EnemyComponent thisEnemy;
    private Entity player;
    private MovementAI movementAI;
    private RaycastAI raycastAI;
    private PlatformAI platformAI;
    private StatImprovementAI statImprovementAI;

    private boolean pathfindingOverride = false;
    private boolean playerReached = false;
    private Entity target;


    public EnemyAIComponent(EnemyComponent thisEnemy, Entity player) {
        this.player = player;
        this.thisEnemy = thisEnemy;
        movementAI = new MovementAI(this);
        raycastAI = new RaycastAI(this);
        platformAI = new PlatformAI(this);
        statImprovementAI = new StatImprovementAI(this);

        target = player;

        if(!this.player.hasComponent(PlayerComponent.class)) {
            this.thisEnemy.die();
            this.thisEnemy.getEntity().removeComponent(EnemyAIComponent.class);
        }
    }

    @Override
    public void onAdded() {
        platformAI.updatePlatforms();
    }

    @Override
    public void onUpdate(double tpf) {

        // Reset move speed and jump height if Enemy is touching solid ground.
        if(!thisEnemy.isAirborne()) {
            statImprovementAI.resetSpeedAndJump();
        }

        movementAI.updateMoveDirection();
        raycastAI.updateRaycastsDirection();

        // Check most recent platform the player was in contact with
        if(!getPlayerComponent().isAirborne()) {
            getPlatformAI().playerRecentPlatformContactCheck();
        }
        
        // Move towards Player if pathfinding haven't been overridden.
        if (!pathfindingOverride) {
            movementAI.moveTowardsTarget();
        }

        enemyAboveOrBelowFix();        // Must be before doJump() (and multiplier reset?).
        movementAI.doJump();
        setPlayerReached();             // Must be after movement code.
        movementAI.enemyStuckUnderPlatformFix();

        // If number of platforms is 4 (map is level1): Activate floatingPlatformMovement.
        if(platformAI.getPlatforms().size() == 4) {
            movementAI.doFloatingPlatformMovement();
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
        if(enemyDirectlyAbove() || enemyDirectlyBelow()) {
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

            // If the Enemy hit is not thisEnemy:
            if(!RaycastCalculations.getRaycastHit(raycastAI.getEntityRaycast()).equals(thisEnemy.getEntity())) {
                Optional<Entity> optionalEntity = raycastAI.getEntityRaycast().getEntity(); // Get Enemy entity
                return optionalEntity.get().getComponent(EnemyAIComponent.class);
            }

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
     * Getter for statImprovementAI variable.
     * @return statImprovementAI.
     */
    public StatImprovementAI getStatImprovementAI() {
        return statImprovementAI;
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

    /**
     * Getter for pathfindingOverride variable.
     * @return pathFinding variable.
     */
    public boolean isPathfindingOverride() {
        return pathfindingOverride;
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
     * Method checks if the given entity's middle x-pos is to the left of the Enemy.
     * @return True or false.
     */
    public boolean isEntityMiddleToLeft(Entity entity) {
        return Math.round(EntityPos.getMiddleX(entity) - thisEnemy.getX()) < 0;

    }

    /**
     * Method checks if the given entity's middle x-pos is to the right of the Enemy.
     * @return True or false.
     */
    public boolean isEntityMiddleToRight(Entity entity) {
        return Math.round(EntityPos.getMiddleX(entity) - thisEnemy.getRightX()) > 0;
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
     * Method checks if given entity's middle Y-position is below the Enemy.
     * @return True or false.
     */
    public boolean isEntityMiddleYBelow(Entity entity) {
        return (entity.getY() + (entity.getHeight() / 2)) - thisEnemy.getBottomY() > 0;
    }

    /**
     * Method checks if the given entity is below the Enemy.
     * @return True or false.
     */
    public boolean isEntitySameY(Entity entity) {
        return Math.abs(entity.getBottomY() - thisEnemy.getBottomY()) < 5;
    }

    /**
     * Method checks if there is an Enemy directly above this entity.
     * @return True or False.
     */
    public boolean enemyDirectlyAbove() {
        if (RaycastCalculations.checkRaycastHit(raycastAI.getLeftUpwardRaycast(), EntityType.ENEMY)) {

            // Is the Enemy hit not thisEnemy?
            if (!RaycastCalculations.getRaycastHit(raycastAI.getLeftUpwardRaycast()).equals(thisEnemy.getEntity())) {
                return true;
            }
        }

        if(RaycastCalculations.checkRaycastHit(raycastAI.getRightUpwardRaycast(), EntityType.ENEMY)) {

            // Is the Enemy hit not thisEnemy?
            if (!RaycastCalculations.getRaycastHit(raycastAI.getRightUpwardRaycast()).equals(thisEnemy.getEntity())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if there is an Enemy directly below this entity.
     * @return True or False.
     */
    public boolean enemyDirectlyBelow() {
        if (RaycastCalculations.checkRaycastHit(raycastAI.getLeftDownwardRaycast(), EntityType.ENEMY)) {

            // Is the Enemy hit not thisEnemy?
            if (!RaycastCalculations.getRaycastHit(raycastAI.getLeftDownwardRaycast()).equals(thisEnemy.getEntity())) {
                return true;
            }
        }

        if(RaycastCalculations.checkRaycastHit(raycastAI.getRightDownwardRaycast(), EntityType.ENEMY)) {

            // Is the Enemy hit not thisEnemy?
            if (!RaycastCalculations.getRaycastHit(raycastAI.getRightDownwardRaycast()).equals(thisEnemy.getEntity())) {
                return true;
            }
        }
        return false;
    }
}
