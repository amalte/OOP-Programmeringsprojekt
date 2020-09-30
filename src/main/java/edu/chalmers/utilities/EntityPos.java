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
        double x = entity.getX() + (entity.getWidth() / 2);
        double y = entity.getY() + (entity.getHeight() / 2);
        return new Point2D(x,y);
    }
}
