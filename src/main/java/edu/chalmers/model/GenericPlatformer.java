package edu.chalmers.model;

/**
 * Aggregate root class for the game.
 */
public class GenericPlatformer {

    private Player player;
    private GameWorldFactory gameWorldFactory;
    private WaveManager waveManager;

    public GenericPlatformer() {
        this.gameWorldFactory = new GameWorldFactory();
    }

    public Player getPlayer(){
        if(player == null){
            createPlayer();
        }
        return player;
    }

    public void initWaveManager(){
        this.waveManager = new WaveManager(getPlayer());
    }

    public GameWorldFactory getGameWorldFactory(){
        return gameWorldFactory;
    }

    private void createPlayer(){
        player = new Player(0,0);
    }

    public WaveManager getWaveManager(){
        return waveManager;
    }
}
