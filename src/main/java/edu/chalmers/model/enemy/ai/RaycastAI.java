package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.physics.RaycastResult;
import edu.chalmers.utilities.RaycastCalculations;

class RaycastAI {

    private EnemyAIComponent AI;

    // ---- RAYCAST VARIABLES ---- //
    private RaycastResult higherHorizontalRaycast;
    private RaycastResult horizontalRaycast;
    private RaycastResult leftDownwardRaycast;
    private RaycastResult rightDownwardRaycast;
    private RaycastResult activeDownwardRaycast;
    private RaycastResult leftUpwardRaycast;
    private RaycastResult rightUpwardRaycast;
    private RaycastResult entityRaycast;

    // ---- RAYCAST LENGTHS ---- //
    private int higherHorizontalRaycastDeltaHeight = 20;    // Delta with entity's top Y-coordinate (how many pixels above entity the raycast should be placed).
    private int higherHorizontalRaycastLength = 40;
    private int horizontalRaycastLength = 30;
    private int downwardRaycastLength = 75;
    private int upwardRaycastLength = 20;
    private int entityRaycastLength = 3;
    private int playerPlatformRaycastLength = 5;        // Length for a raycast on the player.

    public RaycastAI(EnemyAIComponent enemyAIComponent) {
        this.AI = enemyAIComponent;
    }

    /**
     * Method sets correct direction of raycast variables based on moveDirection.
     */
    public void setRaycastsDirection() {
        // +1 and -1 is used to indent the raycast position into the entity a bit, making it more accurate and able to catch entities with the same size.
        // Same goes for +3 and -3.

        leftDownwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, AI.getThisEnemy().getX() + 1, AI.getThisEnemy().getBottomY());
        rightDownwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, AI.getThisEnemy().getRightX() - 1, AI.getThisEnemy().getBottomY());

        leftUpwardRaycast = RaycastCalculations.setVerticalRaycast(-upwardRaycastLength, AI.getThisEnemy().getX() + 1, AI.getThisEnemy().getY());
        rightUpwardRaycast = RaycastCalculations.setVerticalRaycast(-upwardRaycastLength, AI.getThisEnemy().getRightX() - 1, AI.getThisEnemy().getY());

        // If moving left
        if (AI.getMovementAI().getMoveDirection() == MovementAI.Direction.LEFT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(-higherHorizontalRaycastLength, AI.getThisEnemy().getX(), AI.getThisEnemy().getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(-horizontalRaycastLength, AI.getThisEnemy().getX(), AI.getThisEnemy().getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(-entityRaycastLength, AI.getThisEnemy().getX(), AI.getThisEnemy().getY() + 3);
            activeDownwardRaycast = leftDownwardRaycast;
        }
        // If moving right
        else if (AI.getMovementAI().getMoveDirection() == MovementAI.Direction.RIGHT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(higherHorizontalRaycastLength, AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(horizontalRaycastLength, AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(entityRaycastLength, AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY() + 3);
            activeDownwardRaycast = rightDownwardRaycast;
        }
    }

    public RaycastResult getHigherHorizontalRaycast() {
        return higherHorizontalRaycast;
    }

    public RaycastResult getHorizontalRaycast() {
        return horizontalRaycast;
    }

    public RaycastResult getLeftDownwardRaycast() {
        return leftDownwardRaycast;
    }

    public RaycastResult getRightDownwardRaycast() {
        return rightDownwardRaycast;
    }

    public RaycastResult getActiveDownwardRaycast() {
        return activeDownwardRaycast;
    }

    public RaycastResult getLeftUpwardRaycast() {
        return leftUpwardRaycast;
    }

    public RaycastResult getRightUpwardRaycast() {
        return rightUpwardRaycast;
    }

    public RaycastResult getEntityRaycast() {
        return entityRaycast;
    }

    public RaycastResult getLeftPlayerPlatformRaycast() {
        return RaycastCalculations.setVerticalRaycast(playerPlatformRaycastLength, AI.getPlayer().getX(), AI.getPlayer().getBottomY());
    }

    public RaycastResult getRightPlayerPlatformRaycast() {
        return RaycastCalculations.setVerticalRaycast(playerPlatformRaycastLength, AI.getPlayer().getRightX(), AI.getPlayer().getBottomY());
    }
}
