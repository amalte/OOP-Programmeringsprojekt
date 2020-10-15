package edu.chalmers;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import edu.chalmers.main.Main;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
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
            waitForRunLater(mainInstance::shutdown);
            mainInstance = null;
        }
    }

    public static void waitForRunLater(Runnable runnable) throws InterruptedException {
        AtomicReference<CountDownLatch> runLaterLatch = new AtomicReference<>();
        runLaterLatch.set(new CountDownLatch(1));

        Platform.runLater(() ->{
            assertTrue(Platform.isFxApplicationThread());

            runnable.run();
            runLaterLatch.get().countDown();
        });

        assertTrue(runLaterLatch.get().await(FX_TIMEOUT_SEC, TimeUnit.SECONDS));
    }

    public static Main getMainInstance()
    {
        return mainInstance;
    }

    /**
     * Clears all entities in the world
     */
    public static void clearAllEntities() {
        // A separate list for entities was used to avoid ConcurrentModification exception.
        List entities = new ArrayList<Entity>();        // List with all entities.

        // Add each and every entity from the game world to the list.
        for(Entity e : getGameWorld().getEntities()) {
            entities.add(e);
        }
        getGameWorld().removeEntities(entities);     // Remove all existing entities from the world.
    }
}
