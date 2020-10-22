package edu.chalmers.utilities;

import com.almasb.fxgl.core.math.FXGLMath;
import javafx.geometry.Point2D;

/**
 * @author Malte Åkvist
 * <p>
 * Point2DCalculations calculations for points
 */
public final class Point2DCalculations {

    private Point2DCalculations() {
    }

    /**
     * Method gets the angle between two points
     *
     * @param pos1 the first point
     * @param pos2 the second point
     * @return double the angle in degrees between the two points
     */
    public static double getAngle(Point2D pos1, Point2D pos2) {
        if (pos1 != null && pos2 != null) {
            return FXGLMath.atan2(pos2.getY() - pos1.getY(), pos2.getX() - pos1.getX());
        }
        return 0;
    }
}
