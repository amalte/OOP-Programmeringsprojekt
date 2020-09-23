package edu.chalmers.model;

import com.almasb.fxgl.app.FXGLApplication;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.main.Main;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CountDownLatch;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class SetupWorld {       // Initialize world to get access to library methods.
    public static void initApp() throws InterruptedException {
        /*Async.INSTANCE.startAsyncFX(new Runnable() {
            @Override
            public void run() {
                GameApplication.launch(Main.class, new String[0]);
            }
        });*/

        /*new Thread(() -> {
            GameApplication.launch(Main.class, new String[0]);
        }).start();*/

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
