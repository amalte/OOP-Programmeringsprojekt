package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.RaycastResult;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.utilities.RaycastCalculations;

class RaycastAI {

    private EnemyAIComponent AI;
    private EnemyComponent thisEnemy;

    private RaycastResult higherHorizontalRaycast;
    private RaycastResult horizontalRaycast;
    private RaycastResult leftDownwardRaycast;
    private RaycastResult rightDownwardRaycast;
    private RaycastResult activeDownwardRaycast;
    private RaycastResult leftUpwardRaycast;
    private RaycastResult rightUpwardRaycast;
    private RaycastResult entityRaycast;

    private int higherHorizontalRaycastDeltaHeight = 20;    // Delta with entity's top Y-coordinate (how many pixels above entity the raycast should be placed).
    private int higherHorizontalRaycastLength = 40;
    private int horizontalRaycastLength = 30;
    private int downwardRaycastLength = 50;
    private int upwardRaycastLength = 20;
    private int entityRaycastLength = 3;

    public RaycastAI(EnemyAIComponent enemyAIComponent) {
        this.AI = enemyAIComponent;
        this.thisEnemy = enemyAIComponent.getThisEnemy();
    }

    /**
     * Method sets correct direction of raycast variables based on moveDirection.
     */
    public void setRaycastsDirection() {
        // +1 and -1 is used to indent the raycast position into the entity a bit, making it more accurate and able to catch entities with the same size.
        // Same goes for +3 and -3.

        leftDownwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, thisEnemy.getX() + 1, thisEnemy.getBottomY());
        rightDownwardRaycast = RaycastCalculations.setVerticalRaycast(downwardRaycastLength, thisEnemy.getRightX() - 1, thisEnemy.getBottomY());

        leftUpwardRaycast = RaycastCalculations.setVerticalRaycast(-upwardRaycastLength, thisEnemy.getX() + 1, thisEnemy.getY());
        rightUpwardRaycast = RaycastCalculations.setVerticalRaycast(-upwardRaycastLength, thisEnemy.getRightX() - 1, thisEnemy.getY());

        // If moving left
        if (AI.getMovementAI().getMoveDirection() == MovementAI.Direction.LEFT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(-higherHorizontalRaycastLength, thisEnemy.getX(), thisEnemy.getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(-horizontalRaycastLength, thisEnemy.getX(), thisEnemy.getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(-entityRaycastLength, thisEnemy.getX(), thisEnemy.getY() + 3);
            activeDownwardRaycast = leftDownwardRaycast;
        }
        // If moving right
        else if (AI.getMovementAI().getMoveDirection() == MovementAI.Direction.RIGHT) {
            higherHorizontalRaycast = RaycastCalculations.setHorizontalRaycast(higherHorizontalRaycastLength, thisEnemy.getRightX(), thisEnemy.getY() - higherHorizontalRaycastDeltaHeight);
            horizontalRaycast = RaycastCalculations.setHorizontalRaycast(horizontalRaycastLength, thisEnemy.getRightX(), thisEnemy.getY() + 3);
            entityRaycast = RaycastCalculations.setHorizontalRaycast(entityRaycastLength, thisEnemy.getRightX(), thisEnemy.getY() + 3);
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
}
