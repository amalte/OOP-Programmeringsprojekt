package edu.chalmers.utilities;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.RaycastResult;
import edu.chalmers.model.EntityType;
import javafx.geometry.Point2D;

import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;

/**
 * @author Sam Salek
 *
 * RaycastCalculations performs raycastcalculations
 */
public class RaycastCalculations {

    /**
     * Method sets a horizontal raycast.
     * @param raycastLength Length of raycast. Negative length = ray goes left. Positive length = ray goes right.
     * @param x X-Position of raycast.
     * @param y Y-Position of raycast.
     * @return Returns the newly set and created raycast.
     */
    public static RaycastResult setHorizontalRaycast(int raycastLength, double x, double y) {
        Point2D raycastStart = new Point2D(x, y);
        Point2D raycastEnd = new Point2D(x + raycastLength, y);

        return getPhysicsWorld().raycast(raycastStart, raycastEnd);
    }

    /**
     * Method sets a vertical raycast.
     * @param raycastLength Length of raycast. Negative length = ray goes up. Positive length = ray goes down.
     * @param x X-Position of raycast.
     * @param y Y-Position of raycast.
     * @return Returns the newly set and created raycast.
     */
    public static RaycastResult setVerticalRaycast(int raycastLength, double x, double y) {
        Point2D raycastStart = new Point2D(x, y);
        Point2D raycastEnd = new Point2D(x, y + raycastLength);

        return getPhysicsWorld().raycast(raycastStart, raycastEnd);
    }

    /**
     * Method checks if a raycast hit something.
     * @param raycast Raycast to check.
     * @param hit The object to check if hit.
     * @return True or false.
     */
    public static boolean checkRaycastHit(RaycastResult raycast, EntityType hit) {
        String raycastHitString = raycast.getEntity().toString();
        return raycastHitString.contains(hit.toString());
    }

    /**
     * Method returns Entity hit by given raycast.
     * @param raycast The raycast.
     * @return Returns the Entity hit.
     */
    public static Entity getRaycastHit(RaycastResult raycast) {
        Optional<Entity> optionalEntity = raycast.getEntity(); // Get *optional* entity

        // Is Entity *not* empty? Then return entity.
        if(!optionalEntity.equals(Optional.empty())) {
            return optionalEntity.get();
        } else {
            return null;
        }
    }
}
