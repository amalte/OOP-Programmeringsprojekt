package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.wave.WaveManager;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

/**
 * Aggregate root class for the game.
 */
public class GenericPlatformer {

    private Entity player;
    private GameWorldFactory gameWorldFactory;
    private WaveManager waveManager;

    public GenericPlatformer() {
        this.gameWorldFactory = new GameWorldFactory();
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
    }

    /**
     * Get method for waveManager.
     * @return waveManager.
     */
    public WaveManager getWaveManager(){
        return waveManager;
    }
}
