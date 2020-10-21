package edu.chalmers.utilities;

import edu.chalmers.services.Coords;
import javafx.geometry.Point2D;
import org.junit.Test;

import static org.testng.AssertJUnit.*;

/**
 * @author Malte Åkvist
 *
 * Test class for Coords.
 */
public class TestCoords {

    @Test
    public void testEquals() {
        Coords c1 = new Coords(0, 0);
        Coords c2 = new Coords(50, 50);
        Coords c3 = new Coords(50, 50);
        Point2D p1 = new Point2D(0, 0);

        assertFalse(c2.equals(c1));
        assertFalse(c1.equals(c3));
        assertTrue(c2.equals(c3));
        assertFalse(p1.equals(c1));
    }

    @Test
    public void testX() {
        Coords c1 = new Coords(0, 0);
        Coords c2 = new Coords(50, 100);

        assertEquals(0, c1.x());
        assertEquals(50, c2.x());
    }

    @Test
    public void testY() {
        Coords c1 = new Coords(0, 0);
        Coords c2 = new Coords(50, 100);

        assertEquals(0, c1.y());
        assertEquals(100, c2.y());
    }

    @Test
    public void testHashcode() {
        Coords c1 = new Coords(0, 0);
        Coords c1Clone = new Coords(c1.x(), c1.y());
        Coords c2 = new Coords(50, 100);

        assertEquals(c1Clone.hashCode(), c1.hashCode());
        assertFalse(c1.hashCode() == c2.hashCode());
    }
}
