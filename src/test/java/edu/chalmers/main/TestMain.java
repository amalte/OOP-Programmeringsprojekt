package edu.chalmers.main;

import edu.chalmers.FXGLTest;
import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.MenuController;
import edu.chalmers.controller.game.ExitMenuController;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.controller.main.PlayMenuController;
import edu.chalmers.controller.main.SettingsMenuController;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Anwarr Shiervani
 * <p>
 * Test class for all of the main classes.
 */
public class TestMain {
    private static Main mainInstance;

    /**
     * Set up the test class.
     *
     * @throws InterruptedException
     */
    @BeforeClass
    public static void setUp() throws InterruptedException {
        initialize();
        mainInstance = FXGLTest.getMainInstance();
    }

    /**
     * Tear down the test class.
     *
     * @throws InterruptedException
     */
    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
        mainInstance = null;
    }

    /**
     * Test the Main class.
     *
     * @throws InterruptedException
     */
    @Test
    public void testMain() throws InterruptedException {
        /**
         * Start controller type test
         */
        MenuController mainMenuController = mainInstance.getController(GameMenuType.Main);
        assertNotNull(mainMenuController);
        assertTrue(mainMenuController instanceof MainMenuController);

        MenuController playMenuController = mainInstance.getController(GameMenuType.Play);
        assertNotNull(playMenuController);
        assertTrue(playMenuController instanceof PlayMenuController);

        MenuController settingsMenuController = mainInstance.getController(GameMenuType.Settings);
        assertNotNull(settingsMenuController);
        assertTrue(settingsMenuController instanceof SettingsMenuController);

        MenuController exitMenuController = mainInstance.getController(GameMenuType.Exit);
        assertNotNull(exitMenuController);
        assertTrue(exitMenuController instanceof ExitMenuController);

        MenuController dummyController = mainInstance.getController(GameMenuType.Dummy);
        assertNull(dummyController);
        /**
         * End controller type test
         */

        /**
         * Start game start/stop
         */
        waitForRunLater(() -> mainMenuController.hide());

        CountDownLatch gameRunningLatch = new CountDownLatch(1);
        mainInstance.setGameRunningLatch(gameRunningLatch);
        assertEquals(gameRunningLatch, mainInstance.getGameRunningLatch());
        waitForRunLater(() -> mainInstance.startGame(1));
// ERROR assertTrue(gameRunningLatch.await(AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS));

// ERROR assertTrue(mainInstance.getGameRunning());
        assertEquals("level1.tmx", mainInstance.getCurrentLevel());
        waitForRunLater(() -> mainInstance.stopGame());
        assertFalse(mainInstance.getGameRunning());

        waitForRunLater(() -> mainMenuController.hide());

        gameRunningLatch = new CountDownLatch(1);
        mainInstance.setGameRunningLatch(gameRunningLatch);
        waitForRunLater(() -> mainInstance.startGame(1));
// ERROR assertTrue(gameRunningLatch.await(AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS));

// ERROR assertTrue(mainInstance.getGameRunning());
        waitForRunLater(() -> mainInstance.shutdown());
        assertFalse(mainInstance.getGameRunning());
        assertFalse(mainInstance.getGameShutdown());

        /**
         * End game start/stop
         */

        /**
         * Start null checks
         */
// ERROR assertNotNull(mainInstance.getGameUI());
        assertNotNull(mainInstance.getInputController());
        /**
         * End null checks
         */
    }
}
