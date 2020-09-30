package edu.chalmers.utilities;

import com.almasb.fxgl.core.math.FXGLMath;
import javafx.geometry.Point2D;

public class Point2DCalculations {

    public static double getAngle(Point2D pos1, Point2D pos2) {
        if(pos1 != null && pos2 != null) {
            return FXGLMath.atan2(pos2.getY() - pos1.getY(),pos2.getX() - pos1.getX());
        }
        return 0;
    }
}
