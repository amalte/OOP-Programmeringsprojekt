package edu.chalmers.model.enemy.enemytypes;

/**
 * Zombie class. A type of Enemy.
 */
public class Zombie implements IEnemyType {

    @Override
    public String getTextureIdle() {
        return "ZombieSpriteIdle.png";
    }

    @Override
    public String getTextureWalk() {
        return "ZombieSpriteWalk.png";
    }

    @Override
    public int getHealth() {
        return 125;
    }

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public int getBlockDamage() {
        return 35;
    }

    @Override
    public int getMoveSpeed() {
        return 90;
    }

    @Override
    public int getJumpHeight() {
        return 260;
    }
}
