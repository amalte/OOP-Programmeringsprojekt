package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.Texture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that allows you to create a {@see com.almasb.fxgl.physics.PhysicsWorld} but with support for entities, manipulation of the background etc.
 */
public class GameWorld {
    private static final double DEFAULT_PPM = 16;
    private static final int MAX_ENTITIES = 256;

    private final PhysicsWorld physicsWorld;
    private final int appHeight;
    private final double ppm;

    private List<Entity> entityList = new ArrayList<>();
    private Texture backgroundTexture;

    /**
     * Primary constructor for GameWorld.
     * Uses class PhysicsWorld through delegation. Instantiates the aforementioned class with the parameters for this constructor.
     * @param appHeight The height of the app
     * @see com.almasb.fxgl.physics.PhysicsWorld
     */
    public GameWorld(int appHeight) {
        this.appHeight = appHeight;
        ppm = DEFAULT_PPM;
        physicsWorld = new PhysicsWorld(appHeight, DEFAULT_PPM);
    }

    /**
     * Secondary constructor for GameWorld. Allows you to set the "PPM"-value.
     * Uses PhysicsWorld through delegation. Instantiates the aforementioned class with the parameters for this constructor.
     * @param appHeight The height of the app
     * @param ppm Pixels per meter
     * @see com.almasb.fxgl.physics.PhysicsWorld
     */
    public GameWorld(int appHeight, double ppm) {
        this.appHeight = appHeight;
        this.ppm = ppm;
        physicsWorld = new PhysicsWorld(appHeight, ppm);
    }

    /**
     * @param tpf Time per frame
     */
    public void onUpdate(double tpf)
    {
        physicsWorld.onUpdate(tpf);
    }

    /**
     * Sets the new background of the GameWorld. The height of the texture has to be equal to GameWorld.appHeight.
     * @param backgroundTexture The new background of the GameWorld
     * @return True if backgroundTexture was not null and backgroundTexture.getHeight() was equal to GameWorld.appHeight.
     */
    public boolean setBackgroundTexture(Texture backgroundTexture)
    {
        if (backgroundTexture != null && backgroundTexture.getHeight() == appHeight)
        {
            this.backgroundTexture = backgroundTexture;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the background texture for the world.
     * @return The current background texture of the world.
     */
    public Texture getBackgroundTexture()
    {
        return backgroundTexture;
    }

    /**
     * Adds an entity to the world.
     * @param entity The entity to add
     * @return Whether or not MAX_ENTITIES was exceeded and the entity was not added.
     */
    public boolean addEntity(Entity entity)
    {
        if (entityList.size() < MAX_ENTITIES)
        {
            entityList.add(entity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an entity from the world.
     * @param entity The entity to remove
     * @return Whether or not the entity existed in the first place.
     */
    public boolean removeEntity(Entity entity)
    {
        if (entityList.contains(entity))
        {
            entityList.remove(entity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the iterator for the list of entities.
     * @return Iterator for the list of entities.
     */
    public Iterator<Entity> getEntityIterator()
    {
        return entityList.iterator();
    }
}