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

    /**
     * Get method that creates a new player if no player is already created.
     * @return A player object.
     */
    public Player getPlayer(){
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
        player = new Player(0,0);
    }

    /**
     * Get method for waveManager.
     * @return waveManager.
     */
    public WaveManager getWaveManager(){
        return waveManager;
    }
}
