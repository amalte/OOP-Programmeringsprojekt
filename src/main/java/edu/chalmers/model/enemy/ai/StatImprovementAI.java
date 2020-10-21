package edu.chalmers.model.enemy.ai;

/**
 * @author Sam Salek
 *
 * StatImprovementAI. Contains all methods used when Enemy stats need to be improved because of AI.
 */
class StatImprovementAI {

    EnemyAIComponent AI;

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
     * Method improves Enemy stats depending on the Enemy type. Used for a ground to platform jump.
     */
    public void groundToPlatformStatImprovement() {

        // If Enemy is a Zombie:
        if(AI.getThisEnemy().getEnemyType().getName().equalsIgnoreCase("Zombie")) {
            improveStats(1, getZombieGroundToPlatformJmp());
        }

        // If Enemy is a Rex:
        else if(AI.getThisEnemy().getEnemyType().getName().equalsIgnoreCase("Rex")) {
            improveStats(1, getRexGroundToPlatformJmp());
        }

        // If Enemy is a Blob
        else if(AI.getThisEnemy().getEnemyType().getName().equalsIgnoreCase("Blob")) {
            improveStats(1, getBlobGroundToPlatformJmp());
        }
    }

    /**
     * Method improves Enemy stats depending on the Enemy type. Used for a platform to platform jump.
     */
    public void platformToPlatformStatImprovement() {

        // If Enemy is a Zombie:
        if(AI.getThisEnemy().getEnemyType().getName().equalsIgnoreCase("Zombie")) {
            improveStats(getZombiePlatformToPlatformSpeed(), getZombiePlatformToPlatformJmp());
        }

        // If Enemy is a Rex:
        else if(AI.getThisEnemy().getEnemyType().getName().equalsIgnoreCase("Rex")) {
            improveStats(getRexPlatformToPlatformSpeed(), getRexPlatformToPlatformJmp());
        }

        // If Enemy is a Blob
        else if(AI.getThisEnemy().getEnemyType().getName().equalsIgnoreCase("Blob")) {
            improveStats(getBlobPlatformToPlatformSpeed(), getBlobPlatformToPlatformJmp());
        }
    }

    /**
     * Method improves Enemy stats with the given multiplier values.
     * @param moveSpeedMultiplier Multiplier to improve move speed with.
     * @param jmpHeightMultiplier Multiplier to improve jump height with.
     */
    private void improveStats(double moveSpeedMultiplier, double jmpHeightMultiplier) {
        AI.getThisEnemy().setMoveSpeedMultiplier(moveSpeedMultiplier);
        AI.getThisEnemy().setJumpHeightMultiplier(jmpHeightMultiplier);
    }

    // -------- GETTERS -------- //

    public double getZombieGroundToPlatformJmp() {
        return 1.6;
    }

    public double getRexGroundToPlatformJmp() {
        return 1.6;
    }

    public double getBlobGroundToPlatformJmp() {
        return 1.7;
    }

    public double getZombiePlatformToPlatformSpeed() {
        return 1.9;
    }

    public double getRexPlatformToPlatformSpeed() {
        return 2.5;
    }

    public double getBlobPlatformToPlatformSpeed() {
        return 2.4;
    }

    public double getZombiePlatformToPlatformJmp() {
        return 1.7;
    }

    public double getRexPlatformToPlatformJmp() {
        return 1.7;
    }

    public double getBlobPlatformToPlatformJmp() {
        return 1.6;
    }
}
