package edu.chalmers.model;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import edu.chalmers.model.Building.BuildManager;
import edu.chalmers.model.wave.WaveManager;

import java.awt.geom.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

/**
 * Aggregate root class for the game.
 */
public class GenericPlatformer {

    private Entity player;
    private GameWorldFactory gameWorldFactory;
    private WaveManager waveManager;
    private BuildManager buildManager;
    private CollisionDetection collisionDetection;

    public GenericPlatformer() {
        this.gameWorldFactory = new GameWorldFactory();
        this.collisionDetection = new CollisionDetection();

        initBuildManager();
    }

    /**
     * Get method that creates a new player if no player is already created.
     * @return A player object.
     */
    public Entity getPlayer(){
        if(player == null){
            createPlayer();
        }
        return player;
    }

    /**
     * Initiates buildManager.
     */
    public void initBuildManager(){
        //this.buildManager = new BuildManager(getPlayer().getComponent(PlayerComponent.class).getBuildRangeTiles());
        this.buildManager = new BuildManager(3);
    }

    /**
     * Get method for buildManager.
     * @return buildManager.
     */
    public BuildManager getBuildManager(){
        return buildManager;
    }

    /**
     * Initiates waveManger.
     */
    public void initWaveManager(){
        this.waveManager = new WaveManager(getPlayer());
    }

    /**
     * Get method for gameWorldFactory.
     * @return gameWorldFactory.
     */
    public GameWorldFactory getGameWorldFactory(){
        return gameWorldFactory;
    }

    /**
     * Creates a player at position 0,0.
     */
    private void createPlayer(){
        player = spawn("player");
        gameWorldFactory.newPLayer(new SpawnData(0,0));
    }

    /**
     * Get method for waveManager.
     * @return waveManager.
     */
    public WaveManager getWaveManager(){
        return waveManager;
    }

    /**
     * Initiates collision detections for the game worlds entities.
     */
    public void initCollisionDetection(){
        collisionDetection.initCollisionHandler(getPlayer().getComponent(PlayerComponent.class));
    }
}
