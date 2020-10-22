package edu.chalmers.model.services;

import edu.chalmers.services.Coords;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.*;

/**
 * @author Malte Åkvist
 * <p>
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
    public void testSetAndGetXY() {
        Coords c1 = new Coords(0, 0);
        assertEquals(0, c1.getX());
        assertEquals(0, c1.getY());
        c1.setX(10);
        c1.setY(60);
        assertEquals(10, c1.getX());
        assertEquals(60, c1.getY());

        Coords c2 = new Coords(50, 100);
        assertEquals(50, c2.getX());
        assertEquals(100, c2.getY());
        c2.setX(15);
        c2.setY(13);
        assertEquals(15, c2.getX());
        assertEquals(13, c2.getY());
    }



    @Test
    public void testHashcode() {
        Coords c1 = new Coords(0, 0);
        Coords c1Clone = new Coords(c1.getX(), c1.getY());
        Coords c2 = new Coords(50, 100);

        assertEquals(c1Clone.hashCode(), c1.hashCode());
        assertFalse(c1.hashCode() == c2.hashCode());
    }
}
