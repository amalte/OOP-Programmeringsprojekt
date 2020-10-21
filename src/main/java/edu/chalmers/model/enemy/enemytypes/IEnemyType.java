package edu.chalmers.model.enemy.enemytypes;

/**
 * @author Sam Salek
 *
 * IEnemyType Interface. An interface for the Enemy type. Contains the necessary methods and values to differentiate each Enemy type.
 */
public interface IEnemyType {
    String getName();
    int getHealth();
    int getDamage();
    int getBlockDamage();
    int getMoveSpeed();
    int getJumpHeight();
    String getTextureIdle();
    String getTextureWalk();
    String getTextureJump();
}
