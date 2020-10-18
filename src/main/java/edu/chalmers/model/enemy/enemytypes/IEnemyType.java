package edu.chalmers.model.enemy.enemytypes;

public interface IEnemyType {
    String getTextureIdle();
    String getTextureWalk();
    String getTextureJump();
    int getHealth();
    int getDamage();
    int getBlockDamage();
    int getMoveSpeed();
    int getJumpHeight();
}
