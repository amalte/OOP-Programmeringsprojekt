package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.EntityType;
import edu.chalmers.utilities.RaycastCalculations;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * @author Sam Salek
 * <p>
 * MovementAI class handles the movement for enemies
 */
class MovementAI {

    private EnemyAIComponent AI;
    private Direction moveDirection;
    private Entity closestPlatform = null;
    private TimerAction underPlatformTimer;
    private TimerAction moveToNextPlatformTimer;
    private boolean underPlatform = false;
    private boolean moveToNextPlatform = true;
    private boolean jumpAllowed = true;
    public MovementAI(EnemyAIComponent enemyAIComponent) {
        this.AI = enemyAIComponent;
        initTimer();
    }

    /**
     * Method updates moveDirection based on Player position.
     */
    public void updateMoveDirection() {
        // Is target to the left or right?
        if (AI.isEntityMiddleToLeft(AI.getTarget())) {
            moveDirection = Direction.LEFT;
        } else if (AI.isEntityMiddleToRight(AI.getTarget())) {
            moveDirection = Direction.RIGHT;
        }
    }

    /**
     * Method moves Enemy towards the target.
     */
    public void moveTowardsTarget() {

        // Checks if a nearby Enemy has reached the player.
        boolean nearbyEnemyHasReachedPlayer = false;
        if (AI.getNearbyEnemyAI() != null) {
            nearbyEnemyHasReachedPlayer = AI.getNearbyEnemyAI().isPlayerReached();
        }

        // Is Enemy to the right of target AND Player is not reached by Enemy or nearby Enemy?
        if (AI.isEntityMiddleToLeft(AI.getTarget()) &&
                !AI.isPlayerReached() &&
                !nearbyEnemyHasReachedPlayer) {
            AI.getThisEnemy().moveLeft();
        }

        // Is Enemy to the left of target AND Player is not reached by Enemy or nearby Enemy?
        else if (AI.isEntityMiddleToRight(AI.getTarget()) &&
                !AI.isPlayerReached() &&
                !nearbyEnemyHasReachedPlayer) {
            AI.getThisEnemy().moveRight();
        }

        // Player has been reached; stop moving.
        else {
            AI.getThisEnemy().stop();
        }
    }

    /**
     * Jump method. Makes Enemy jump when needed.
     */
    public void doJump() {
        if (AI.getRaycastAI().getHigherHorizontalRaycast() == null ||
                AI.getRaycastAI().getHorizontalRaycast() == null ||
                AI.getRaycastAI().getActiveDownwardRaycast() == null) {
            return;
        }

        // Jump isn't allowed: return.
        if (!jumpAllowed) {
            return;
        }

        // IF (jump up to platform from the ground):
        // Players middle Y-pos is above Enemy *AND*
        // Player most recently did not touch the world ground *AND*
        // getHigherHorizontalRaycast hit a platform:
        if (AI.isEntityMiddleYAbove(AI.getPlayer()) &&
                !AI.getPlayerComponent().isOnGround() &&
                RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getHigherHorizontalRaycast(), EntityType.PLATFORM)) {

            AI.getStatImprovementAI().groundToPlatformStatImprovement();     // Increase moveSpeed and jumpHeight.
            AI.getThisEnemy().jump();
            return;

        }

        // IF (going to fall):
        // activeDownwardRaycast did *not* hit a platform (Enemy is usually walking off a platform) *AND*
        // activeDownwardRaycast did *not* hit a block *AND*
        // Enemy is not airborne:
        if (!RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getActiveDownwardRaycast(), EntityType.PLATFORM) &&
                !RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getActiveDownwardRaycast(), EntityType.BLOCK) &&
                !AI.getThisEnemy().isAirborne()) {

            AI.getStatImprovementAI().platformToPlatformStatImprovement();     // Increase moveSpeed and jumpHeight if Enemy is falling off platform and is going to jump.
            AI.getThisEnemy().jump();
            return;
        }

        // IF (hit a block or platform):
        // horizontalRaycast hit a Block or a platform:
        if (RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getHorizontalRaycast(), EntityType.BLOCK) ||
                RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getHorizontalRaycast(), EntityType.PLATFORM)) {

            AI.getThisEnemy().jump();
            return;
        }
    }

    /**
     * Method makes Enemy try to reach the Player when the path involves climbing up floating platforms.
     */
    public void doFloatingPlatformMovement() {

        // IF:
        // the Player is *not* on the ground *AND*
        // Enemy is *not* under a platform *AND*
        // Player is above Enemy *AND*
        // Player and Enemy *don't* have the same Y-position *AND*
        // Enemy should move to next platform:
        if (!AI.getPlayerComponent().isOnGround() &&
                !underPlatform &&
                AI.isEntityBottomYAbove(AI.getPlayer()) &&
                !AI.isEntitySameY(AI.getPlayer()) &&
                moveToNextPlatform) {

            // If closest platform is *not* equal to null
            if (closestPlatform != null) {
                // Checks if platform below Enemy is closestPlatform. If so: delay move to next platform.
                if (AI.getPlatformAI().checkPlatformBelowEnemy(closestPlatform)) {
                    moveToNextPlatform = false;
                    moveToNextPlatformDelay();      // Sets moveToNextPlatform to true after a short delay (to give Enemy time to reach platform).
                }
            }

            // Set closestPlatform if Enemy is not airborne.
            if (!AI.getThisEnemy().isAirborne()) {
                closestPlatform = AI.getPlatformAI().getClosestPlatform();
            }

            // Set target to closestPlatform if it isn't equal to null.
            if (closestPlatform != null &&
                    AI.getPlatformAI().getPlayerRecentPlatformContact() != null) {

                AI.setTarget(closestPlatform);

                // If the most recent platform the player was in contact with is the same platform below Enemy: set target to player.
                if (AI.getPlatformAI().getPlayerRecentPlatformContact().equals(AI.getPlatformAI().getPlatformBelowEnemy())) {
                    AI.setTarget(AI.getPlayer());
                }
            }
        }

        // Else: Set target to Player if Enemy is not airborne.
        else {
            if (!AI.getThisEnemy().isAirborne()) {
                AI.setTarget(AI.getPlayer());
            }
        }
    }

    /**
     * Enables Enemy to jump up to a platform if standing under one with the Player directly above.
     */
    public void enemyStuckUnderPlatformFix() {
        if (AI.getRaycastAI().getLeftUpwardRaycast() == null ||
                AI.getRaycastAI().getRightUpwardRaycast() == null) {
            return;
        }

        // IF:
        // Players middle Y-pos is above Enemy *AND*
        // leftUpwardRaycast or rightUpwardRaycast hits a platform *AND*
        // Enemy is not airborne *AND*
        // Enemy most recently touched the world ground *AND*
        // Enemy is not already under a platform *AND*
        // Player most recently touched a platform
        if (AI.isEntityMiddleYAbove(AI.getPlayer()) &&
                (RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getLeftUpwardRaycast(), EntityType.PLATFORM) ||
                        RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getRightUpwardRaycast(), EntityType.PLATFORM)) &&
                !AI.getThisEnemy().isAirborne() &&
                AI.getThisEnemy().isOnGround() &&
                !AI.getPlayerComponent().isOnGround()) {

            // Then Enemy is under a platform.
            underPlatform = true;
            jumpAllowed = false;
        }

        // Else: Enemy is not under a platform. Delay setting underPlatform to false with a short delay (to give Enemy time to get away from under the platform).
        else {
            noLongerUnderPlatformDelay();
        }


        // Override pathfinding if Enemy is under a platform.
        if (underPlatform) {
            AI.setPathfindingOverride(true);

            // Move right if Player is to the left.
            if (AI.isEntityMiddleToLeft(AI.getPlayer())) {
                AI.getThisEnemy().moveRight();
            }

            // Move left if Player is to the right.
            else if (AI.isEntityMiddleToRight(AI.getPlayer())) {
                AI.getThisEnemy().moveLeft();
            }
        }

        // Enemy is not under a platform, therefore turn pathfinding back on.
        else {
            AI.setPathfindingOverride(false);
        }
    }

    /**
     * Initiate damage delay timer.
     */
    private void initTimer() {
        underPlatformTimer = runOnce(() -> {
        }, Duration.seconds(0));
        moveToNextPlatformTimer = runOnce(() -> {
        }, Duration.seconds(0));
    }

    // ---------- TIMERS ---------- //

    /**
     * Method sets underPlatform variable to false after a short delay.
     */
    private void noLongerUnderPlatformDelay() {
        if (underPlatformTimer.isExpired()) {
            underPlatformTimer = runOnce(() -> {
                underPlatform = false;
                jumpAllowed = true;
            }, Duration.seconds(1));
        }
    }

    /**
     * Method sets moveToNextPlatform variable to true after a short delay.
     */
    private void moveToNextPlatformDelay() {
        if (moveToNextPlatformTimer.isExpired()) {
            moveToNextPlatformTimer = runOnce(() -> moveToNextPlatform = true, Duration.seconds(1));
        }
    }

    /**
     * Getter for moveDirection variable.
     *
     * @return True or False.
     */
    public Direction getMoveDirection() {
        return moveDirection;
    }

    // ------- GETTERS ------- //

    /**
     * Setter for moveDirection variable.
     *
     * @param moveDirection Direction value.
     */
    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    /**
     * Getter for jumpAllowed variable.
     *
     * @return True or False.
     */
    public boolean isJumpAllowed() {
        return jumpAllowed;
    }

    /**
     * Setter for jumpAllowed variable
     *
     * @param jumpAllowed True or False.
     */
    public void setJumpAllowed(boolean jumpAllowed) {
        this.jumpAllowed = jumpAllowed;
    }

    /**
     * Getter for isUnderPlatform variable.
     *
     * @return True or false.
     */
    public boolean isUnderPlatform() {
        return underPlatform;
    }

    // ------- SETTERS ------- //

    /**
     * Setter for underPlatform variable.
     *
     * @param underPlatform True or False.
     */
    public void setUnderPlatform(boolean underPlatform) {
        this.underPlatform = underPlatform;
    }

    /**
     * Getter for moveToNextPlatform variable.
     *
     * @return True or false.
     */
    public boolean isMoveToNextPlatform() {
        return moveToNextPlatform;
    }

    /**
     * Setter for moveToNextPlatform variable.
     *
     * @param moveToNextPlatform True or False.
     */
    public void setMoveToNextPlatform(boolean moveToNextPlatform) {
        this.moveToNextPlatform = moveToNextPlatform;
    }

    public enum Direction {LEFT, RIGHT}
}
