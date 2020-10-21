package edu.chalmers.utilities;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Erik Wetter, Malte Åkvist
 *
 * Test class for Point2DCalculations.
 */
public class TestPoint2DCalculations {
    @Test
    void testGetAngle() {
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(0, 5);


        assertEquals(Math.toRadians(90), Point2DCalculations.getAngle(p1, p2));
    }
}
