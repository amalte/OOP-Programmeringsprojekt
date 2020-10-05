package edu.chalmers.model.enemy;

/**
 * StatMultiplier class. Contains multiplier variables for Enemy entity stats.
 */
public class StatMultiplier {

    private double healthMultiplier;
    private double dmgMultiplier;
    private double speedMultiplier;
    private double jmpHeightMultiplier;

    // ----------CONSTRUCTORS---------- //

    public StatMultiplier(double healthMultiplier, double dmgMultiplier, double speedMultiplier, double jmpHeightMultiplier) {
        setMultipliers(healthMultiplier, dmgMultiplier, speedMultiplier, jmpHeightMultiplier);
    }

    public StatMultiplier(double healthMultiplier, double dmgMultiplier, double speedMultiplier) {
        setMultipliers(healthMultiplier, dmgMultiplier, speedMultiplier, 1);
    }

    public StatMultiplier(double healthMultiplier, double dmgMultiplier) {
        setMultipliers(healthMultiplier, dmgMultiplier, 1, 1);
    }

    public StatMultiplier(double healthMultiplier) {
        setMultipliers(healthMultiplier, 1, 1, 1);
    }

    public StatMultiplier() {
        setMultipliers(1, 1, 1, 1);
    }
    // ----------

    /**
     * Method sets multiplier variable values.
     * @param healthMultiplier Health multiplier.
     * @param dmgMultiplier Attack damage multiplier.
     * @param speedMultiplier Move speed multiplier.
     * @param jmpHeightMultiplier Jump height multiplier
     */
    private void setMultipliers(double healthMultiplier, double dmgMultiplier, double speedMultiplier, double jmpHeightMultiplier) {

        // Health multiplier : set to 1 if input is below 1.
        if(healthMultiplier < 1) {
            this.healthMultiplier = 1;
        } else {
            this.healthMultiplier = healthMultiplier;
        }

        // Attack damage multiplier : set to 1 if input is below 1.
        if(dmgMultiplier < 1) {
            this.dmgMultiplier = 1;
        } else {
            this.dmgMultiplier = dmgMultiplier;
        }

        // Move speed multiplier : set to 1 if input is below 1.
        if(speedMultiplier < 1) {
            this.speedMultiplier = 1;
        } else {
            this.speedMultiplier = speedMultiplier;
        }

        // Jump height multiplier : set to 1 if input is below 1.
        if(jmpHeightMultiplier < 1) {
            this.jmpHeightMultiplier = 1;
        } else {
            this.jmpHeightMultiplier = jmpHeightMultiplier;
        }
    }

    // ----------GETTERS---------- //

    public double getHealthMultiplier() {
        return healthMultiplier;
    }

    public double getDmgMultiplier() {
        return dmgMultiplier;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public double getJmpHeightMultiplier() {
        return jmpHeightMultiplier;
    }
}
