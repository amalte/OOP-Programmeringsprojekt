package edu.chalmers.controller;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.MainMenu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMainControllers {
    private Main mainInstance;

    @Before
    public void setUp() {
        this.mainInstance = new Main();

        new Thread(() ->
        {
            GameApplication.launch(Main.class, new String[0]);
        }).start();
    }

    @Test
    public void testMainMenuController() throws InterruptedException {
        Thread.sleep(500);

        MainMenuController menuController = new MainMenuController(new MainMenu(), this.mainInstance);
        menuController.show();
    }

    @Test
    public void testPlayMenuController() {

    }

    @Test
    public void testSettingsMenuController() {

    }

    @After
    public void tearDown()
    {
        FXGL.getGameController().exit();
    }
}
