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
import static com.almasb.fxgl.dsl.FXGL.*;

public final class FXGLTest {
    private static final int FX_TIMEOUT_SEC = 10;
    private static Main mainInstance;

    public static void initialize() throws InterruptedException
    {
        if (mainInstance == null)
        {
            Main.setInitializedLatch(new CountDownLatch(1));
            new Thread(() -> {
                GameApplication.launch(Main.class, new String[0]);
            }).start();
            assertTrue(Main.getInitializedLatch().await(FX_TIMEOUT_SEC, TimeUnit.SECONDS));

            GameApplication gameApplication = getApp();
            assertNotNull(gameApplication);
            assertTrue(gameApplication instanceof Main);

            mainInstance = (Main)gameApplication;
        }
    }

    public static void deInitialize() throws InterruptedException
    {
        if (mainInstance != null)
        {
            waitForRunLater (getGameController()::exit);
            mainInstance = null;
        }
    }

    public static void waitForRunLater(Runnable runnable) throws InterruptedException {
        AtomicReference<CountDownLatch> runLaterLatch = new AtomicReference<>();
        runLaterLatch.set(new CountDownLatch(1));

        Platform.runLater(() ->{
            runnable.run();
            runLaterLatch.get().countDown();
        });

        assertTrue(runLaterLatch.get().await(FX_TIMEOUT_SEC, TimeUnit.SECONDS));
    }

    public static Main getMainInstance()
    {
        return mainInstance;
    }
}
