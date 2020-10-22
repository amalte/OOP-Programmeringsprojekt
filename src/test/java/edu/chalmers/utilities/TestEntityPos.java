package edu.chalmers.utilities;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.FXGLTest;
import javafx.geometry.Point2D;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Malte Åkvist
 *
 * Test class for EntityPos.
 */
public class TestEntityPos {
    Entity player;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    private void resetTest() throws InterruptedException {
        waitForRunLater(() -> {
            FXGLTest.clearAllEntities();
            player = spawn("player");
        });
    }

    @Test
    public void testGetPosition() throws InterruptedException {
        resetTest();
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        entity1.setX(0);
        entity1.setY(0);
        Point2D p1 = EntityPos.getPosition(entity1);
        entity2.setX(200);
        entity2.setY(500);
        Point2D p2 = EntityPos.getPosition(entity2);
        player.setX(150);
        player.setY(800);

        assertEquals(0, Math.round(p1.getX()));
        assertEquals(0, Math.round(p1.getY()));
        assertEquals(200, Math.round(p2.getX()));
        assertEquals(500, Math.round(p2.getY()));;
        assertEquals(150, Math.round(player.getX()));
        assertEquals(800, Math.round(player.getY()));
    }

    @Test
    public void testGetMiddleX() throws InterruptedException {
        resetTest();
        Entity entity1 = new Entity();
        entity1.setX(500);
        player.setX(800);
        int pWidth = (int) Math.round(player.getWidth());

        assertEquals(500, Math.round(EntityPos.getMiddleX(entity1)));
        assertEquals(800 + pWidth/2, Math.round(EntityPos.getMiddleX(player)));
    }

    @Test
    public void testGetMiddleY() throws InterruptedException {
        resetTest();
        Entity entity1 = new Entity();
        entity1.setY(500);
        player.setY(800);
        int pHeight = (int) Math.round(player.getHeight());

        assertEquals(500, Math.round(EntityPos.getMiddleY(entity1)));
        assertEquals(800 + pHeight/2, Math.round(EntityPos.getMiddleY(player)));
    }

    @Test
    public void testGetRightSideX() throws InterruptedException {
        resetTest();
        player.setX(800);
        int pWidth = (int) Math.round(player.getWidth());
        int rightSide1 = (int) Math.round(EntityPos.getRightSideX(player));
        player.setX(0);
        int rightSide2 = (int) Math.round(EntityPos.getRightSideX(player));

        assertEquals(800 + pWidth, rightSide1);
        assertEquals(pWidth, rightSide2);
    }

    @Test
    public void testGetLeftSideX() throws InterruptedException {
        resetTest();
        player.setX(800);
        int leftSide1 = (int) Math.round(EntityPos.getLeftSideX(player));
        player.setX(0);
        int leftSide2 = (int) Math.round(EntityPos.getLeftSideX(player));

        assertEquals(800, leftSide1);
        assertEquals(0, leftSide2);
    }

    @Test
    public void testGetTopY() throws InterruptedException {
        resetTest();
        player.setY(150);
        int top1 = (int) Math.round(EntityPos.getTopY(player));
        player.setY(0);
        int top2 = (int) Math.round(EntityPos.getTopY(player));

        assertEquals(150, top1);
        assertEquals(0, top2);
    }

    @Test
    public void testGetBottomY() throws InterruptedException {
        resetTest();
        player.setY(200);
        int pHeight = (int) Math.round(player.getHeight());
        int top1 = (int) Math.round(EntityPos.getBottomY(player));
        player.setY(0);
        int top2 = (int) Math.round(EntityPos.getBottomY(player));

        assertEquals(200+pHeight, top1);
        assertEquals(pHeight, top2);
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }
}
