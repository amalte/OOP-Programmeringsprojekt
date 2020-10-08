package edu.chalmers.model.enemy.enemytypes;

import javafx.scene.paint.Color;

/**
 * Blob class. A type of Enemy.
 */
public class Blob implements IEnemyType {

    @Override
    public Color getColor() {
        return Color.DARKORANGE;
    }

    @Override
    public int getHealth() {
        return 100;
    }

    @Override
    public int getDamage() {
        return 15;
    }

    @Override
    public int getBlockDamage() {
        return 20;
    }

    @Override
    public int getMoveSpeed() {
        return 80;
    }

    @Override
    public int getJumpHeight() {
        return 275;
    }
}
