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
        return entity.getX() + (entity.getWidth() / 2);
    }

    /**
     * Method gets the middle Y-Position of an entity.
     * @param entity The entity to get position from.
     * @return Middle Y-Position of the entity.
     */
    public static double getMiddleY(Entity entity) {
        return entity.getY() + (entity.getHeight() / 2);
    }

    /**
     * Method gets the right side X-Position of an entity.
     * @param entity The entity to get position from.
     * @return Right side X-Position of the entity.
     */
    public static double getRightSideX(Entity entity) {
        return entity.getX() + entity.getWidth();
    }

    /**
     * Method gets the left side X-Position of an entity.
     * @param entity The entity to get position from.
     * @return Left side X-Position of the entity.
     */
    public static double getLeftSideX(Entity entity) {
        return entity.getX();
    }

    /**
     * Method gets the top side Y-Position of an entity.
     * @param entity The entity to get position from.
     * @return Top side Y-Position of the entity.
     */
    public static double getTopY(Entity entity) {
        return entity.getY();
    }

    /**
     * Method gets the bottom side Y-Position of an entity.
     * @param entity The entity to get position from.
     * @return Bottom side Y-Position of the entity.
     */
    public static double getBottomY(Entity entity) {
        return entity.getY() + entity.getHeight();
    }
}
