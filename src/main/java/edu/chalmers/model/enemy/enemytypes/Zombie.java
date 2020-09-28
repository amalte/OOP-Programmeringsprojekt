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
        return 100;
    }

    @Override
    public int getDamage() {
        return 25;
    }

    @Override
    public int getMoveSpeed() {
        return 80;
    }

    @Override
    public int getJumpHeight() {
        return 300;
    }
}
