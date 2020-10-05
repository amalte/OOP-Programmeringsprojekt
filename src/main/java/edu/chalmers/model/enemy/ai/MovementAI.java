package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.model.enemy.StatMultiplier;
import edu.chalmers.utilities.RaycastCalculations;
import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

class MovementAI {

    public enum Direction {LEFT, RIGHT}

    EnemyAIComponent AI;
    private Direction moveDirection;
    private int randInt;
    Entity closestPlatform = null;

    private TimerAction underPlatformTimer;
    private TimerAction moveToNextPlatformTimer;
    boolean underPlatform = false;
    boolean moveToNextPlatform = true;

    public MovementAI(EnemyAIComponent enemyAIComponent) {
        this.AI = enemyAIComponent;

        Random random = new Random();
        randInt = random.nextInt(2);

        initTimer();
    }

    /**
     * Method sets moveDirection based on Player position.
     */
    public void setMoveDirection() {
        // Is target to the left or right?
        if (AI.isEntityToLeft(AI.getTarget())) {
            moveDirection = Direction.LEFT;
        } else if (AI.isEntityToRight(AI.getTarget())) {
            moveDirection = Direction.RIGHT;
        }
    }

    /**
     * Method moves Enemy towards the target.
     */
    public void moveTowardsTarget() {

        // Checks if a nearby Enemy has reached the player.
        boolean nearbyEnemyHasReachedPlayer = false;
        if(AI.getNearbyEnemyAI() != null) {
            nearbyEnemyHasReachedPlayer = AI.getNearbyEnemyAI().isPlayerReached();
        }

        // Is Enemy to the right of target AND Player is not reached by Enemy or nearby Enemy?
        if (AI.isEntityToLeft(AI.getTarget()) &&
                !AI.isPlayerReached() &&
                !nearbyEnemyHasReachedPlayer) {
            AI.getThisEnemy().moveLeft();
        }

        // Is Enemy to the left of target AND Player is not reached by Enemy or nearby Enemy?
        else if (AI.isEntityToRight(AI.getTarget()) &&
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

        // If Player is above Enemy; check if jump is needed.
        if(AI.isEntityAbove(AI.getPlayer())) {

            // IF:
            // higherHorizontalRaycast hit a platform *OR*
            // horizontalRaycast hit a platform *OR*
            // activeDownwardRaycast did *not* hit a platform (Enemy is walking of a platform).
            if (RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getHigherHorizontalRaycast(), EntityType.PLATFORM) ||
                    RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getHorizontalRaycast(), EntityType.PLATFORM) ||
                    !RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getActiveDownwardRaycast(), EntityType.PLATFORM)) {

                // Increase moveSpeed and jumpHeight if Enemy is falling of platform and is going to jump.
                if(!RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getActiveDownwardRaycast(), EntityType.PLATFORM)) {
                    AI.getThisEnemy().setStatMultiplier(new StatMultiplier(1, 1, 1.5, 1.2));
                }

                // Jump
                AI.getThisEnemy().jump();
            }
        }



        // If horizontalRaycast hit a Block.
        if (RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getHorizontalRaycast(), EntityType.BLOCK)) {
            AI.getThisEnemy().jump();
        }
    }

    /**
     * Method makes Enemy try to reach the Player when the path is more complicated (involving climbing up platforms).
    */
    public void reachPlayer() {

        // IF:
        // the Player is *not* on the ground *AND*
        // Enemy is *not* under a platform *AND*
        // Player is above Enemy *AND*
        if(!AI.getPlayer().getComponent(PlayerComponent.class).isOnGround() &&
                !underPlatform &&
                AI.isEntityAbove(AI.getPlayer())) {

            // If Enemy should move to next platform
            if(moveToNextPlatform) {

                // If closest platform is *not* equal to null
                if(closestPlatform != null) {

                    // Checks if platform below Enemy is closestPlatform. If so: delay move to next platform.
                    if(AI.getPlatformAI().checkPlatformBelow(closestPlatform)) {
                        moveToNextPlatform = false;
                        moveToNextPlatformDelay();      // Sets moveToNextPlatform to true after a short delay (to give Enemy time to reach platform).
                    }
                }

                // Set closestPlatform if Enemy is not airborne.
                if(!AI.getThisEnemy().isAirborne()) {
                    closestPlatform = AI.getPlatformAI().getClosestPlatform();
                }

                // Set target to closestPlatform if it isn't equal to null.
                if(closestPlatform != null) {
                    AI.setTarget(closestPlatform);
                }
            }
        }

        // Else: Set target to Player and follow.
        else {
            AI.setTarget(AI.getPlayer());
        }
    }

    /**
     * Enables Enemy to jump up to a platform if standing under one with the Player directly above.
     */
    public void enemyStuckUnderPlatformFix() {
        if(AI.getRaycastAI().getLeftUpwardRaycast() == null ||
                AI.getRaycastAI().getRightUpwardRaycast() == null) {
            return;
        }

        // IF:
        // Player is above Enemy *AND*
        // leftUpwardRaycast or rightUpwardRaycast hits a platform *AND*
        // Enemy is not airborne *AND*
        // Enemy most recently touched the world ground *AND*
        // Enemy is not already under a platform
        if(AI.isEntityAbove(AI.getPlayer()) &&
                (RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getLeftUpwardRaycast(), EntityType.PLATFORM) ||
                        RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getRightUpwardRaycast(), EntityType.PLATFORM)) &&
                !AI.getThisEnemy().isAirborne() &&
                AI.getThisEnemy().isOnGround() &&
                !underPlatform) {

            // Then Enemy is under a platform.
            underPlatform = true;
        }

        // Else: Enemy is not under a platform. Delay setting underPlatform to false with a short delay (to give Enemy time to get away from under the platform).
        else {
            noLongerUnderPlatformDelay();
        }


        // Override pathfinding if Enemy is under a platform.
        if(underPlatform) {
            AI.setPathfindingOverride(true);

            // Move right if Player is to the left.
            if(AI.isEntityToLeft(AI.getPlayer())) {
                AI.getThisEnemy().moveRight();
            }

            // Move left if Player is to the right.
            else if (AI.isEntityToRight(AI.getPlayer())) {
                AI.getThisEnemy().moveLeft();
            }
        }

        // Enemy is not under a platform, therefore turn pathfinding back on.
        else {
            AI.setPathfindingOverride(false);
        }
    }

    /**
     * Method makes Enemy go down from a platform or a higher point if the Player is directly below.
     */
    // TODO - not done (not working as intended)
    public void followPlayerDown() {
        if(AI.isEntityBelow(AI.getPlayer()) && !AI.getThisEnemy().isAirborne()) {
            AI.setPathfindingOverride(true);

            if(randInt == 0) {
                AI.getThisEnemy().moveLeft();
            }
            else if(randInt == 1) {
                AI.getThisEnemy().moveRight();
            }
        }
        else {
            AI.setPathfindingOverride(false);
        }
    }

    /**
     * Method sets underPlatform variable to false after a short delay.
     */
    private void noLongerUnderPlatformDelay(){
        if(underPlatformTimer.isExpired()){
            underPlatformTimer = runOnce(() -> underPlatform = false, Duration.seconds(1));
        }
    }

    /**
     * Method sets moveToNextPlatform variable to true after a short delay.
     */
    private void moveToNextPlatformDelay(){
        if(moveToNextPlatformTimer.isExpired()){
            moveToNextPlatformTimer = runOnce(() -> moveToNextPlatform = true, Duration.seconds(1));
        }
    }

    /**
     * Initiate damage delay timer.
     */
    private void initTimer(){
        underPlatformTimer = runOnce(() -> {}, Duration.seconds(0));
        moveToNextPlatformTimer = runOnce(() -> {}, Duration.seconds(0));
    }

    // ------- GETTERS ------- //

    /**
     * Getter for moveDirection variable.
     * @return moveDirection.
     */
    public Direction getMoveDirection() {
        return moveDirection;
    }
}
