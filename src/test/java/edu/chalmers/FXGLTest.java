package edu.chalmers;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.main.Main;
import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class FXGLTest {
    private static final int FXGL_TIMEOUT_SEC = 10;
    private static Main mainInstance;

    public static void setUp() throws InterruptedException
    {
        if (mainInstance == null)
        {
            Main.setInitializedLatch(new CountDownLatch(1));

            new Thread(() -> {
                GameApplication.launch(Main.class, new String[0]);
            }).start();

            assertTrue(Main.getInitializedLatch().await(FXGL_TIMEOUT_SEC, TimeUnit.SECONDS));

            GameApplication gameApplication = FXGL.getApp();
            assertNotNull(gameApplication);
            assertTrue(gameApplication instanceof Main);

            mainInstance = (Main)gameApplication;
        }
    }

    public static void tearDown() throws InterruptedException
    {
        if (mainInstance != null)
        {
            synchronized (mainInstance)
            {
                AtomicReference<CountDownLatch> timerActionLatch = new AtomicReference<>();
                timerActionLatch.set(new CountDownLatch(1));

                Platform.runLater(() -> {
                    FXGL.getGameController().exit();
                    timerActionLatch.get().countDown();
                });

                assertTrue(timerActionLatch.get().await(FXGL_TIMEOUT_SEC, TimeUnit.SECONDS));
            }

            mainInstance = null;
        }
    }

    public static Main getMainInstance()
    {
        return mainInstance;
    }
}
