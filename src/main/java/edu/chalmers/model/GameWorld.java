package edu.chalmers.model;

import com.almasb.fxgl.physics.PhysicsWorld;

/**
 * A class that allows you to create a {@see com.almasb.fxgl.physics.PhysicsWorld} but with support for entities, manipulation of the background etc.
 */
public class GameWorld {
    private static final double DEFAULT_PPM = 16;

    private final PhysicsWorld physicsWorld;

    /**
     * Primary constructor for GameWorld.
     * Uses {@see com.almasb.fxgl.physics.PhysicsWorld} through delegation. Instantiates the aforementioned class with the parameters for this constructor.
     * @param appHeight The height of the app
     * @see com.almasb.fxgl.physics.PhysicsWorld
     */
    public GameWorld(int appHeight) {
        physicsWorld = new PhysicsWorld(appHeight, DEFAULT_PPM);
    }

    /**
     * Secondary constructor for GameWorld. Allows you to set the "PPM"-value.
     * Uses {@see com.almasb.fxgl.physics.PhysicsWorld} through delegation. Instantiates the aforementioned class with the parameters for this constructor.
     * @param appHeight The height of the app
     * @param ppm Pixels per meter
     * @see com.almasb.fxgl.physics.PhysicsWorld
     */
    public GameWorld(int appHeight, double ppm) {
        physicsWorld = new PhysicsWorld(appHeight, ppm);
    }


}