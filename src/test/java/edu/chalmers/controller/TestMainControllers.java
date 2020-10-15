package edu.chalmers.controller;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.main.Main;
import javafx.application.Platform;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestMainControllers {
    private static Main mainInstance;

    @BeforeClass
    public static void setUp() throws InterruptedException {
        if (mainInstance == null)
        {
            new Thread(() -> {
                GameApplication.launch(Main.class, new String[0]);
            }).start();

            Thread.sleep(2000);
        }
        GameApplication gameApplication = (Main) FXGL.getApp();

        if (gameApplication instanceof Main)
        {
            mainInstance = (Main)gameApplication;

            while (!mainInstance.getControllersInitialized())
                Thread.sleep(100);
        }
    }

    @Test
    public void testMainMenuController() throws InterruptedException {
        MainMenuController mainMenuController = null;

        while (true)
        {
            if (mainInstance != null)
            {
                MenuController menuController = mainInstance.getController(GameMenuType.Main);

                if (menuController != null)
                {
                    Boolean menuControllerCorrect = menuController instanceof MainMenuController;
                    assertEquals(true, menuControllerCorrect);

                    if (menuControllerCorrect)
                    {
                        mainMenuController = (MainMenuController)menuController;
                        break;
                    } else {
                        return;
                    }
                }
            }

            Thread.sleep(100);
        }

        MainMenuController finalMainMenuController = mainMenuController;
        Platform.runLater(() -> {
            finalMainMenuController.show();
        });
        Thread.sleep(500);

        assertEquals(true, finalMainMenuController.isVisible());
    }

    @Test
    public void testPlayMenuController() {

    }

    @Test
    public void testSettingsMenuController() {

    }
}
