package edu.chalmers.model.enemy.enemytypes;

import javafx.scene.paint.Color;

/**
 * Zombie class. A type of Enemy.
 */
public class Zombie implements IEnemyType {

    @Override
    public Color getColor() {
        return Color.DARKGREEN;
    }

    @Override
    public int getHealth() {
        return 150;
    }

    @Override
    public int getDamage() {
        return 35;
    }

    @Override
    public int getMoveSpeed() {
        return 90;
    }

    @Override
    public int getJumpHeight() {
        return 400;
    }
}
