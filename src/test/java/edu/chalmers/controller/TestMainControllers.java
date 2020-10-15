package edu.chalmers.controller;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.test.RunWithFX;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.FXGLTest;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.main.Main;
import javafx.application.Platform;
import javafx.util.Duration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(RunWithFX.class)
public class TestMainControllers {
    private static FXGLTest fxglTest = new FXGLTest();
    private static Main mainInstance;

    @BeforeClass
    public static void setUp() throws InterruptedException {
        FXGLTest.setUp();
        mainInstance = FXGLTest.getMainInstance();
    }

    @Test
    public void testMainMenuController() throws InterruptedException {
        MenuController menuController = mainInstance.getController(GameMenuType.Main);
        assertNotNull(menuController);
        assertTrue(menuController instanceof MainMenuController);

        MainMenuController mainMenuController = (MainMenuController)menuController;
        Platform.runLater(mainMenuController::show);

        assertEquals(true, mainMenuController.isVisible());
    }

    @Test
    public void testPlayMenuController() {

    }

    @Test
    public void testSettingsMenuController() {

    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        FXGLTest.tearDown();
        mainInstance = null;
    }
}
