package edu.chalmers.utilities;

import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.model.building.BuildManager;
import edu.chalmers.model.building.MapManager;
import edu.chalmers.services.Coords;
import edu.chalmers.services.TileMap;
import javafx.geometry.Point2D;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCoordsCalculations {

    @Test
    public void testPosToTile() {
        Point2D p1 = new Point2D(50, 150);
        Point2D p2 = new Point2D(-1001, -1001);

        assertEquals(0, CoordsCalculations.posToTile(p1).x());
        assertEquals(2, CoordsCalculations.posToTile(p1).y());
        assertEquals(-16, CoordsCalculations.posToTile(p2).x());
        assertEquals(-16, CoordsCalculations.posToTile(p2).y());
    }

    @Test
    public void testTileToPos() {
        Coords c1 = new Coords(0, 2);
        Coords c2 = new Coords(-1001, -1001);

        assertEquals(0, CoordsCalculations.tileToPos(c1).getX());
        assertEquals(120, CoordsCalculations.tileToPos(c1).getY());
        assertEquals(-60060, CoordsCalculations.tileToPos(c2).getX());
        assertEquals(-60060, CoordsCalculations.tileToPos(c2).getY());
    }

    @Test
    public void testPosToTilePos() {
        Point2D p1 = new Point2D(50, 150);
        Point2D p2 = new Point2D(-1001, -1001);

        assertEquals(0, CoordsCalculations.posToTilePos(p1).getX());
        assertEquals(120, CoordsCalculations.posToTilePos(p1).getY());
        assertEquals(-960, CoordsCalculations.posToTilePos(p2).getX());
        assertEquals(-960, CoordsCalculations.posToTilePos(p2).getY());
    }
}
