package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.sun.javafx.geom.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.Random;


public class main extends GameApplication {

    Entity entity = new Entity();
    Random random = new Random();

    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Game Test");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initGame() {
        EntityBuilder entityBuilder = new EntityBuilder();
        entity = entityBuilder.at(0, 0).viewWithBBox(new Rectangle(50, 50)).with("velocity", new Point2D(2, 1)).buildAndAttach();
    }

    @Override
    protected void onUpdate(double tpf) {
        Point2D velocity = entity.getObject("velocity");
        entity.translate(new Vec2(velocity.x, velocity.y));



        if(entity.getRightX() >= 800) {
            entity.setProperty("velocity", new Point2D(-randomInt(3, 5), randomInt(3, 5, true)));
        }

        if(entity.getX() < 0) {
            entity.setProperty("velocity", new Point2D(randomInt(3, 5), randomInt(3, 5, true)));
        }

        if(entity.getBottomY() >= 600) {
            entity.setProperty("velocity", new Point2D(randomInt(3, 5, true), -randomInt(3, 5)));
        }

        if(entity.getY() < 0) {
            entity.setProperty("velocity", new Point2D(randomInt(3, 5, true), randomInt(3, 5)));
        }

    }

    private int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    private int randomInt(int min, int max, boolean canBeNegative) {

        // Can be negative
        if(canBeNegative) {
            int negative = randomInt(0, 2);
            if(negative == 0) {
                return randomInt(min, max);
            } else if (negative == 1) {
                return -randomInt(min, max);
            } else {
                throw new IndexOutOfBoundsException("Whut?");
            }

            // Can't be negative - call main method
        } else
        {
            return randomInt(min, max);
        }
    }
}
