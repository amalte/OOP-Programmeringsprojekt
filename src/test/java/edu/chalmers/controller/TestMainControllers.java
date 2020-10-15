package edu.chalmers.controller;

import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.FXGLTest;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.MainMenu;
import javafx.application.Platform;
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
        MenuController menuController = mainInstance.getController(GameMenuType.Main);
        assertNotNull(menuController);
        assertTrue(menuController instanceof MainMenuController);

        MainMenuController mainMenuController = (MainMenuController)menuController;

        waitForRunLater(mainMenuController::show);
        assertEquals(true, mainMenuController.isVisible());

        waitForRunLater(mainMenuController::hide);
        assertEquals(false, mainMenuController.isVisible());

        assertTrue(mainMenuController.getViewInstance() instanceof MainMenu);
        assertEquals(GameMenuType.Main, mainMenuController.getGameMenuType());

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
