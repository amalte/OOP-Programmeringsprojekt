package edu.chalmers.controller;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.model.GameWorldFactory;
import edu.chalmers.view.main.MainMenu;
import org.junit.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class TestControllerMiscUtilities {
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
    public void testMenuController() {

    }

    @After
    public void tearDown()
    {
        FXGL.getGameController().exit();
    }
}
