package edu.chalmers.model.enemy.ai;

import edu.chalmers.model.enemy.EnemyTypes;

/**
 * StatImprovementAI. Contains all variables and methods used when Enemy stats need to be improved because of AI.
 */
class StatImprovementAI {

    EnemyAIComponent AI;

    // Ground to platform - jump height:
    private double zombieGroundToPlatformJmp = 1.6;
    private double rexGroundToPlatformJmp = 1.6;
    private double blobGroundToPlatformJmp = 1.7;

    // Platform to platform - move speed:
    private double zombiePlatformToPlatformSpeed = 1.6;
    private double rexPlatformToPlatformSpeed = 2.1;
    private double blobPlatformToPlatformSpeed = 1.9;

    // Platform to platform - jump height:
    private double zombiePlatformToPlatformJmp = 1.7;
    private double rexPlatformToPlatformJmp = 1.7;
    private double blobPlatformToPlatformJmp = 1.6;

    public StatImprovementAI(EnemyAIComponent enemyAIComponent) {
        this.AI = enemyAIComponent;
    }

    /**
     * Method resets Enemy move speed and jump height to original values.
     */
    public void resetSpeedAndJump() {
        AI.getThisEnemy().setMoveSpeedMultiplier(1);
        AI.getThisEnemy().setJumpHeightMultiplier(1);
    }

    /**
     * Method improves Enemy stats depending on the Enemy type.
     */
    public void groundToPlatformStatImprovement() {

        // If Enemy is a Zombie:
        if(AI.getThisEnemy().getEnemyType().getClass().equals(EnemyTypes.getZombieClass())) {
            AI.getThisEnemy().setJumpHeightMultiplier(zombieGroundToPlatformJmp);
        }

        // If Enemy is a Rex:
        else if(AI.getThisEnemy().getEnemyType().getClass().equals(EnemyTypes.getRexClass())) {
            AI.getThisEnemy().setJumpHeightMultiplier(rexGroundToPlatformJmp);
        }

        // If Enemy is a Blob
        else if(AI.getThisEnemy().getEnemyType().getClass().equals(EnemyTypes.getBlobClass())) {
            AI.getThisEnemy().setJumpHeightMultiplier(blobGroundToPlatformJmp);
        }
    }

    /**
     * Method improves Enemy stats depending on the Enemy type.
     */
    public void platformToPlatformStatImprovement() {

        // If Enemy is a Zombie:
        if(AI.getThisEnemy().getEnemyType().getClass().equals(EnemyTypes.getZombieClass())) {
            AI.getThisEnemy().setMoveSpeedMultiplier(zombiePlatformToPlatformSpeed);
            AI.getThisEnemy().setJumpHeightMultiplier(zombiePlatformToPlatformJmp);
        }

        // If Enemy is a Rex:
        else if(AI.getThisEnemy().getEnemyType().getClass().equals(EnemyTypes.getRexClass())) {
            AI.getThisEnemy().setMoveSpeedMultiplier(rexPlatformToPlatformSpeed);
            AI.getThisEnemy().setJumpHeightMultiplier(rexPlatformToPlatformJmp);
        }

        // If Enemy is a Blob
        else if(AI.getThisEnemy().getEnemyType().getClass().equals(EnemyTypes.getBlobClass())) {
            AI.getThisEnemy().setMoveSpeedMultiplier(blobPlatformToPlatformSpeed);
            AI.getThisEnemy().setJumpHeightMultiplier(blobPlatformToPlatformJmp);
        }
    }

    // -------- GETTERS -------- //

    public double getZombieGroundToPlatformJmp() {
        return zombieGroundToPlatformJmp;
    }

    public double getRexGroundToPlatformJmp() {
        return rexGroundToPlatformJmp;
    }

    public double getBlobGroundToPlatformJmp() {
        return blobGroundToPlatformJmp;
    }

    public double getZombiePlatformToPlatformSpeed() {
        return zombiePlatformToPlatformSpeed;
    }

    public double getRexPlatformToPlatformSpeed() {
        return rexPlatformToPlatformSpeed;
    }

    public double getBlobPlatformToPlatformSpeed() {
        return blobPlatformToPlatformSpeed;
    }

    public double getZombiePlatformToPlatformJmp() {
        return zombiePlatformToPlatformJmp;
    }

    public double getRexPlatformToPlatformJmp() {
        return rexPlatformToPlatformJmp;
    }

    public double getBlobPlatformToPlatformJmp() {
        return blobPlatformToPlatformJmp;
    }
}
