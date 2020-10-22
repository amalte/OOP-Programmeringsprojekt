package edu.chalmers.utilities;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Erik Wetter, Malte Åkvist
 * <p>
 * Test class for Point2DCalculations.
 */
public class TestPoint2DCalculations {
    @Test
    void testGetAngle() {
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(0, 5);
        assertEquals(90, Math.round(Math.toDegrees(Point2DCalculations.getAngle(p1, p2))));

        p1 = new Point2D(5, 5);
        p2 = new Point2D(6, 5);
        assertEquals(0, Math.round(Math.toDegrees(Point2DCalculations.getAngle(p1, p2))));

        p1 = new Point2D(0, 0);
        p2 = new Point2D(2, 2);
        assertEquals(45, Math.round(Math.toDegrees(Point2DCalculations.getAngle(p1, p2))));
    }
}
