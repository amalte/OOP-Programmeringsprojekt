package edu.chalmers.model.building;

import edu.chalmers.FXGLTest;
import edu.chalmers.model.building.blocks.Block;
import edu.chalmers.services.Coords;
import edu.chalmers.services.TileMap;
import javafx.geometry.Point2D;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.chalmers.FXGLTest.*;
import static edu.chalmers.FXGLTest.waitForRunLater;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Malte Åkvist
 * <p>
 * Test class for MapManager.
 */
public class TestMapManager {
    private MapManager mapManager;
    private Block block1;
    private Block block2;
    private Block block3;
    private Block block4;

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
        mapManager = new MapManager(new TileMap().getBlockMapFromLevel("level1.tmx"));
    }

    @Test
    public void testIsTileEmpty() throws InterruptedException {
        resetTest();
        Coords tileToCheck = new Coords(0, 0);
        assertTrue(mapManager.isTileEmpty(tileToCheck));

        tileToCheck = new Coords(10, 11);   // On a block in the level1.tmx file
        assertFalse(mapManager.isTileEmpty(tileToCheck));
    }

    @Test
    public void testIsTileConnected() throws InterruptedException {
        resetTest();
        Coords tileToCheck = new Coords(0, 0);
        assertFalse(mapManager.isTileConnected(tileToCheck));

        tileToCheck = new Coords(10, 10);   // Above a block in the level1.tmx file
        assertTrue(mapManager.isTileConnected(tileToCheck));
    }

    @Test
    public void testAddBlockToMap() throws InterruptedException {
        resetTest();
        Coords tileToAdd = new Coords(0, 0);
        waitForRunLater(() -> block1 = new Block(new Point2D(20, 20)));

        assertFalse(mapManager.getBlockMap().containsKey(tileToAdd));
        mapManager.addBlockToMap(tileToAdd, block1);
        assertTrue(mapManager.getBlockMap().containsKey(tileToAdd));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        resetTest();

        // Create various tiles to add to the map
        Coords tile1 = new Coords(10, 10);  // Connected to permanent block and tile 2
        Coords tile2 = new Coords(10, 9);   // Connected to tile1 and tile3
        Coords tile3 = new Coords(10, 8);   // Connected to tile2
        Coords tile4 = new Coords(9, 10);   // Connected to permanent block

        // Create random blocks
        waitForRunLater(() -> block1 = new Block(new Point2D(20, 20)));
        waitForRunLater(() -> block2 = new Block(new Point2D(20, 20)));
        waitForRunLater(() -> block3 = new Block(new Point2D(20, 20)));
        waitForRunLater(() -> block4 = new Block(new Point2D(20, 20)));

        // Add blocks to the map
        mapManager.addBlockToMap(tile1, block1);
        mapManager.addBlockToMap(tile2, block2);
        mapManager.addBlockToMap(tile3, block3);
        mapManager.addBlockToMap(tile4, block4);

        // Check if correct tiles were removed
        waitForRunLater(() -> mapManager.update(tile1));  // tile1 gets removed and so should tile2 and tile3 since they are now floating
        assertFalse(mapManager.getBlockMap().containsKey(tile1));
        assertFalse(mapManager.getBlockMap().containsKey(tile2));
        assertFalse(mapManager.getBlockMap().containsKey(tile3));
        assertTrue(mapManager.getBlockMap().containsKey(tile4));
    }
}
