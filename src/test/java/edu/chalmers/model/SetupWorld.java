package edu.chalmers.model;

import com.almasb.fxgl.app.GameApplication;
import edu.chalmers.main.Main;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class SetupWorld {       // Initialize world to get access to library methods.
    public static void initApp() throws InterruptedException {

        Thread t = new Thread(() ->
        {
            GameApplication.launch(Main.class, new String[0]);
        });
        t.start();

        Thread.sleep(1000);
        System.out.println("App thread started");


        // Initialize before tests \\
        getGameWorld().addEntityFactory(new GameWorldFactory());
    }
}
