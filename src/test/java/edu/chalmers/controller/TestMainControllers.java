package edu.chalmers.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.FXGLTest;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.controller.main.PlayMenuController;
import edu.chalmers.controller.main.SettingsMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.MainMenu;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static edu.chalmers.FXGLTest.*;

@ExtendWith(RunWithFX.class)
public class TestMainControllers {
    private static Main mainInstance;

    @BeforeClass
    public static void setUp() throws InterruptedException {
        initialize();
        mainInstance = FXGLTest.getMainInstance();
    }

    @Test
    public void testMainMenuController() throws InterruptedException {
        /**
         * Start retrieve controller
         */
        MenuController menuController = mainInstance.getController(GameMenuType.Main);
        assertNotNull(menuController);
        assertTrue(menuController instanceof MainMenuController);
        MainMenuController mainMenuController = (MainMenuController)menuController;
        /**
         * End retrieve controller
         */

        /**
         * Start visibility test
         */
        waitForRunLater(mainMenuController::show);
        assertEquals(true, mainMenuController.isVisible());
        waitForRunLater(mainMenuController::hide);
        assertEquals(false, mainMenuController.isVisible());
        /**
         * End visibility test
         */

        /**
         * Start superclass test
         */
        assertEquals(mainInstance, mainMenuController.getMainInstance());
        assertTrue(mainMenuController.getViewInstance() instanceof MainMenu);
        assertEquals(GameMenuType.Main, mainMenuController.getGameMenuType());
        /**
         * End superclass test
         */

        /**
         * Start controller set/get
         */
        PlayMenuController playMenuController = (PlayMenuController) mainInstance.getController(GameMenuType.Play);
        mainMenuController.setPlayMenuController(null);
        assertEquals(null, mainMenuController.getPlayMenuController());
        mainMenuController.setPlayMenuController(playMenuController);
        assertEquals(playMenuController, mainMenuController.getPlayMenuController());

        SettingsMenuController settingsMenuController = (SettingsMenuController) mainInstance.getController(GameMenuType.Settings);
        mainMenuController.setSettingsMenuController(null);
        assertEquals(null, mainMenuController.getSettingsMenuController());
        mainMenuController.setSettingsMenuController(settingsMenuController);
        assertEquals(settingsMenuController, mainMenuController.getSettingsMenuController());
        /**
         * End controller set/get
         */

        /**
         * Start actions
         */
        MainMenu mainMenu = mainMenuController.getViewInstance();

        assertNotNull(mainMenu.getPlayButton().getOnMousePressed());
        assertNotNull(mainMenu.getSettingsButton().getOnMousePressed());
        assertNotNull(mainMenu.getExitButton().getOnMousePressed());

        waitForRunLater(() -> mainMenu.getPlayButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertTrue(mainMenuController.getPlayMenuController().isVisible());

        waitForRunLater(() -> mainMenu.getSettingsButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertTrue(mainMenuController.getSettingsMenuController().isVisible());

        waitForRunLater(() -> mainMenu.getExitButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertTrue(mainInstance.getGameShutdown());
        /**
         * End actions
         */
    }

    @Test
    public void testPlayMenuController() {

    }

    @Test
    public void testSettingsMenuController() {

    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
        mainInstance = null;
    }
}
