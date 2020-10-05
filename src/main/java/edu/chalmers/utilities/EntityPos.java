package edu.chalmers.utilities;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public class EntityPos {

    /**
     * Method gets the position of an entity
     * @param entity the entity to get position from
     * @return position of the entity
     */
    public static Point2D getPosition(Entity entity) {    // Takes into account width and height of entity
        double x = getMiddleX(entity);
        double y = getMiddleY(entity);
        return new Point2D(x,y);
    }

    /**
     * Method gets the middle X-Position of an entity.
     * @param entity The entity to get position from.
     * @return Middle X-Position of the entity.
     */
    public static double getMiddleX(Entity entity) {
        double x = entity.getX() + (entity.getWidth() / 2);
        return x;
    }

    /**
     * Method gets the middle Y-Position of an entity.
     * @param entity The entity to get position from.
     * @return Middle Y-Position of the entity.
     */
    public static double getMiddleY(Entity entity) {
        double y = entity.getY() + (entity.getHeight() / 2);
        return y;
    }
}
