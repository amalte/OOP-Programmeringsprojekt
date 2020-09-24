package edu.chalmers.model.enemy.enemytypes;

import javafx.scene.paint.Color;

/**
 * Rex class. A type of Enemy.
 */
public class Rex implements IEnemyType {

    @Override
    public Color getColor() {
        return Color.DARKRED;
    }

    @Override
    public int getHealth() {
        return 200;
    }

    @Override
    public int getDamage() {
        return 50;
    }

    @Override
    public int getMoveSpeed() {
        return 60;
    }

    @Override
    public int getJumpHeight() {
        return 100;
    }
}
