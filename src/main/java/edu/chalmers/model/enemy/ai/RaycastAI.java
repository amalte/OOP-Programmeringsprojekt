package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.physics.RaycastResult;
import edu.chalmers.utilities.RaycastCalculations;

/**
 * @author Sam Salek
 *
 * RaycastAI. Contains all variables and methods used by Enemy AI regarding raycasts.
 */
class RaycastAI {

    private EnemyAIComponent AI;
    //Line line;        // Debug line

    // ---- RAYCAST VARIABLES ---- //
    private RaycastResult higherHorizontalRaycast;
    private RaycastResult horizontalRaycast;
    private RaycastResult activeDownwardRaycast;
    private RaycastResult entityRaycast;

    // ---- RAYCAST LENGTHS ---- //
    private int higherHorizontalRaycastDeltaHeight = 20;    // Delta with entity's top Y-coordinate (how many pixels above entity the raycast should be placed).
    private int higherHorizontalRaycastLength = 60;
    private int horizontalRaycastLength = 30;
    private int downwardRaycastLength = 75;
    private int upwardRaycastLength = 20;
    private int entityRaycastLength = 3;
    private int playerPlatformRaycastLength = 5;        // Length for a raycast on the player.

    public RaycastAI(EnemyAIComponent enemyAIComponent) {
        this.AI = enemyAIComponent;

//        line = new Line(AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY()+3, AI.getThisEnemy().getRightX()+horizontalRaycastLength, AI.getThisEnemy().getY()+3);
//        FXGL.getGameScene().addUINode(line);
    }

    /**
     * Method updates correct direction of raycast variables based on moveDirection.
     */
    public void updateRaycastsDirection() {
        // +1 and -1 is used to indent the raycast position into the entity a bit, making it more accurate and able to catch entities with the same size.
        // Same goes for +3 and -3.
        // +10 at horizontalRaycast's X-pos (when moving left) as raycast was off center because of hitbox and texture size difference.

        // If moving left
        if (AI.getMovementAI().getMoveDirection() == MovementAI.Direction.LEFT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(-higherHorizontalRaycastLength, AI.getThisEnemy().getX(), AI.getThisEnemy().getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(-horizontalRaycastLength, AI.getThisEnemy().getX()+10, AI.getThisEnemy().getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(-entityRaycastLength, AI.getThisEnemy().getX(), AI.getThisEnemy().getY() + 3);
            activeDownwardRaycast = getLeftDownwardRaycast();

//            FXGL.getGameScene().removeUINode(line);
//            line = new Line(AI.getThisEnemy().getX()+10, AI.getThisEnemy().getY()+3, AI.getThisEnemy().getX()+5-horizontalRaycastLength, AI.getThisEnemy().getY()+3);
//            FXGL.getGameScene().addUINode(line);
        }
        // If moving right
        else if (AI.getMovementAI().getMoveDirection() == MovementAI.Direction.RIGHT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(higherHorizontalRaycastLength, AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(horizontalRaycastLength, AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(entityRaycastLength, AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY() + 3);
            activeDownwardRaycast = getRightDownwardRaycast();

//            FXGL.getGameScene().removeUINode(line);
//            line = new Line(AI.getThisEnemy().getRightX(), AI.getThisEnemy().getY()+3, AI.getThisEnemy().getRightX()+horizontalRaycastLength, AI.getThisEnemy().getY()+3);
//            FXGL.getGameScene().addUINode(line);
        }
    }

    public RaycastResult getHigherHorizontalRaycast() {
        return higherHorizontalRaycast;
    }

    public RaycastResult getHorizontalRaycast() {
        return horizontalRaycast;
    }

    public RaycastResult getLeftDownwardRaycast() {
        return RaycastCalculations.setVerticalRaycast(downwardRaycastLength, AI.getThisEnemy().getX() + 1, AI.getThisEnemy().getBottomY());
    }

    public RaycastResult getRightDownwardRaycast() {
        return RaycastCalculations.setVerticalRaycast(downwardRaycastLength, AI.getThisEnemy().getRightX() - 1, AI.getThisEnemy().getBottomY());
    }

    public RaycastResult getActiveDownwardRaycast() {
        return activeDownwardRaycast;
    }

    public RaycastResult getLeftUpwardRaycast() {
        return RaycastCalculations.setVerticalRaycast(-upwardRaycastLength, AI.getThisEnemy().getX() + 1, AI.getThisEnemy().getY());
    }

    public RaycastResult getRightUpwardRaycast() {
        return RaycastCalculations.setVerticalRaycast(-upwardRaycastLength, AI.getThisEnemy().getRightX() - 1, AI.getThisEnemy().getY());
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
