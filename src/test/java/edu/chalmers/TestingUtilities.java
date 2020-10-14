package edu.chalmers;

import com.almasb.fxgl.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class TestingUtilities {

    // Removes all entities in the world
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
