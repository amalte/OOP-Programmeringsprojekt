package edu.chalmers.model.building;

import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.services.Coords;
import edu.chalmers.services.TileMap;
import edu.chalmers.utilities.CoordsCalculations;
import javafx.geometry.Point2D;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Malte Åkvist
 * <p>
 * Test class for BuildManager.
 */
public class TestBuildManager {
    private MapManager mapManager = new MapManager(new TileMap().getBlockMapFromLevel("level1.tmx"));
    private BuildManager buildManager = new BuildManager(new PlayerComponent(new PhysicsComponent()).getBuildRangeTiles(), mapManager);

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    private void resetTest() throws InterruptedException {
        waitForRunLater(FXGLTest::clearAllEntities);
    }

    @Test
    public void testPlaceBlock() throws InterruptedException {
        resetTest();
        Point2D blockPos = new Point2D(100, 100);

        waitForRunLater(() -> buildManager.placeBlock(blockPos));

        assertEquals(1, getGameWorld().getEntitiesByType(EntityType.BLOCK).size());
    }

    @Test
    public void testPossibleToPlaceBlockOnPos() throws InterruptedException {
        resetTest();
        Point2D blockPos = CoordsCalculations.tileToPos(new Coords(3, 14));
        Point2D playerPos = CoordsCalculations.tileToPos(new Coords(3, 14));

        assertFalse(buildManager.possibleToPlaceBlockOnPos(blockPos, playerPos));   // Building on player
        blockPos = CoordsCalculations.tileToPos(new Coords(2, 14));
        assertTrue(buildManager.possibleToPlaceBlockOnPos(blockPos, playerPos));
    }

    @Test
    public void testGetEmptyReachableTiles() throws InterruptedException {
        resetTest();
        Coords playerTile = new Coords(0, 0);
        assertEquals(3, new PlayerComponent(new PhysicsComponent()).getBuildRangeTiles());

        assertEquals(16, buildManager.getEmptyReachableTiles(playerTile).size());   // In corner of map can reach 4*4 tiles
        playerTile = new Coords(10, 10);
        assertEquals(45, buildManager.getEmptyReachableTiles(playerTile).size());
    }

    @Test
    public void testIsInBuildRange() throws InterruptedException {
        resetTest();
        assertEquals(3, new PlayerComponent(new PhysicsComponent()).getBuildRangeTiles());

        Coords buildTile = new Coords(0, 0);
        Coords playerTile = new Coords(0, 0);
        assertTrue(buildManager.isInBuildRange(buildTile, playerTile));

        buildTile.setX(5);
        assertFalse(buildManager.isInBuildRange(buildTile, playerTile));
    }
}
