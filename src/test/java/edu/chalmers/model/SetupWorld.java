package edu.chalmers.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import edu.chalmers.main.Main;

import java.util.List;

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

    /**
     * Clears all entities in the world
     */
    public static void clearEntities() {
        List<Entity> entitiesToRemove = getGameWorld().getEntities();
        for (int i = 0; i < entitiesToRemove.size(); i++) {     // Clear all entities in gameWorld
            getGameWorld().removeEntity(entitiesToRemove.get(i));
        }
    }
}
