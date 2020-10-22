package edu.chalmers.model.building.blocks;

import edu.chalmers.FXGLTest;
import edu.chalmers.model.MockMapObserver;
import edu.chalmers.model.building.blocks.Block;
import javafx.geometry.Point2D;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Malte Åkvist
 * <p>
 * Test class for Block.
 */
public class TestBlock {
    private Block block;
    private Point2D blockSpawnPos = new Point2D(3, 0);

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    public void resetTest() throws InterruptedException {
        waitForRunLater(() -> {
            FXGLTest.clearAllEntities();
            block = new Block(blockSpawnPos);
        });
    }

    @Test
    public void testCanBeDestroyed() throws InterruptedException {
        resetTest();
        assertTrue(block.canBeDestroyed());
    }

    @Test
    public void testRemove() throws InterruptedException {
        resetTest();
        assertEquals(1, getGameWorld().getEntities().size());
        waitForRunLater(() -> block.remove());
        assertEquals(0, getGameWorld().getEntities().size());
    }

    @Test
    public void testInflictDamage() throws InterruptedException {
        resetTest();
        block.setTesting(true);
        assertEquals(100, block.getHealth());
        waitForRunLater(() -> block.inflictDamage(40));
        assertEquals(60, block.getHealth());
        assertEquals(1, getGameWorld().getEntities().size());
        waitForRunLater(() -> block.inflictDamage(200));
        assertEquals(0, getGameWorld().getEntities().size());
    }

    @Test
    public void testObserverMethods() throws InterruptedException {
        blockSpawnPos = new Point2D(180, 70);
        resetTest();
        MockMapObserver o = new MockMapObserver();
        block.IMapObservers.clear();
        assertEquals(0, block.IMapObservers.size());
        block.addObserver(o);
        assertEquals(1, block.IMapObservers.size());

        assertEquals(0, o.getTileToUpdate().getX());
        assertEquals(0, o.getTileToUpdate().getY());
        block.notifyObservers();
        assertEquals(3, o.getTileToUpdate().getX());
        assertEquals(1, o.getTileToUpdate().getY());
    }
}
