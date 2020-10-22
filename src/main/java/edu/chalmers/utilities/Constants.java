package edu.chalmers.utilities;

/**
 * @author Malte Åkvist
 *
 * Constants class that contains constant data in the game.
 */
public final class Constants {

    private Constants() {}

    static final int TILE_SIZE = 60;   // How big a tile is in pixels
    public static final int GAME_WIDTH = 1920; // startWidth for the game, FXGL.getSettings().getWidth();
    public static final int GAME_HEIGHT = 1080; // startHeight for the game, FXGL.getSettings().getHeight();
    public static final int TILEMAP_WIDTH = GAME_WIDTH/TILE_SIZE;
    public static final int TILEMAP_HEIGHT = GAME_HEIGHT/TILE_SIZE;
}
