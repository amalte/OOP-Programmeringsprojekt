package edu.chalmers.controller;

import edu.chalmers.FXGLTest;
import edu.chalmers.controller.game.ExitMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.game.ExitMenu;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for all of the game controllers.
 */
public class TestGameControllers {
    private static Main mainInstance;

    /**
     * Set up the test class.
     * @throws InterruptedException
     */
    @BeforeClass
    public static void setUp() throws InterruptedException {
        initialize();
        mainInstance = FXGLTest.getMainInstance();
    }

    /**
     * Test the ExitMenuController class.
     * @throws InterruptedException
     */
    @Test
    public void testExitMenuController() throws InterruptedException {
        /**
         * Start retrieve controller
         */
        MenuController menuController = mainInstance.getController(GameMenuType.Exit);
        assertNotNull(menuController);
        assertTrue(menuController instanceof ExitMenuController);
        ExitMenuController exitMenuController = (ExitMenuController)menuController;
        /**
         * End retrieve controller
         */

        /**
         * Start visibility test
         */
        waitForRunLater(exitMenuController::hide);
        assertFalse(exitMenuController.isVisible());
        waitForRunLater(exitMenuController::show);
        assertTrue(exitMenuController.isVisible());
        /**
         * End visibility test
         */

        /**
         * Start superclass test
         */
        assertEquals(mainInstance, exitMenuController.getMainInstance());
        assertTrue(exitMenuController.getViewInstance() instanceof ExitMenu);
        assertEquals(GameMenuType.Exit, exitMenuController.getGameMenuType());
        /**
         * End superclass test
         */

        /**
         * Start controller set/get
         */
        InputController inputController = mainInstance.getInputController();
        exitMenuController.setInputController(null);
        assertNull(exitMenuController.getInputController());
        exitMenuController.setInputController(inputController);
        assertEquals(inputController, exitMenuController.getInputController());
        /**
         * End controller set/get
         */

        /**
         * Start actions
         */
        ExitMenu exitMenu = exitMenuController.getViewInstance();

        waitForRunLater(exitMenuController::hide);

        CountDownLatch gameRunningLatch = new CountDownLatch(1);
        mainInstance.setGameRunningLatch(gameRunningLatch);
        waitForRunLater(() -> mainInstance.startGame(1));
        assertTrue(gameRunningLatch.await(AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS));

        // Exit
        assertNotNull(exitMenu.getExitButton().getOnMousePressed());
        waitForRunLater(() -> exitMenu.getExitButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertFalse(exitMenuController.isVisible());
        assertFalse(mainInstance.getGameRunning());

        // Wait for game to start
        gameRunningLatch = new CountDownLatch(1);
        mainInstance.setGameRunningLatch(gameRunningLatch);
        waitForRunLater(() -> mainInstance.startGame(1));
        assertTrue(gameRunningLatch.await(AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS));

        // Escape
        assertNotNull(getGameScene().getRoot().getScene().getOnKeyPressed());
        waitForRunLater(() -> InputController.getInputInstance().mockKeyPress(KeyCode.ESCAPE));
        assertTrue(exitMenuController.isVisible());
        waitForRunLater(() -> getGameScene().getRoot().getScene().getOnKeyPressed().handle(new KeyEvent(new EventType<>("ESC-1001"), "", "", KeyCode.ESCAPE, false, false, false, false)));
        assertFalse(exitMenuController.isVisible());

        // Stop the game.
        waitForRunLater(() -> mainInstance.stopGame());

        /**
         * End actions
         */
    }

    /**
     * Tear down the test class.
     * @throws InterruptedException
     */
    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
        mainInstance = null;
    }
}
