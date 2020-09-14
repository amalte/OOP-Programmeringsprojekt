package edu.chalmers.model;

/**
 * Aggregate root class for the game.
 */
public class GenericPlatformer {

    private Player player;
    private GameWorldFactory gameWorldFactory;
    //private WaveManager waveManager;

    public GenericPlatformer() {
        this.gameWorldFactory = new GameWorldFactory();
        //this.waveManager = new WaveManager(player);
    }

    public Player getPlayer(){
        if(player == null){
            createPlayer();
        }
        return player;
    }

    public GameWorldFactory getGameWorldFactory(){
        return gameWorldFactory;
    }

    private void createPlayer(){
        player = new Player(0,0);
    }

    /*public WaveManager getWaveManager(){
        return waveManager;
    }*/


}
