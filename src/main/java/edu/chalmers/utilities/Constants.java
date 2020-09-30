package edu.chalmers.utilities;

public class Constants {

    public static final int TILE_SIZE = 60;   // How big a tile is in pixels
    public static final int GAME_WIDTH = 1920; // startWidth for the game, FXGL.getSettings().getWidth();
    public static final int GAME_HEIGHT = 1080; // startHeight for the game, FXGL.getSettings().getHeight();
    public static final int TILEMAP_WIDTH = GAME_WIDTH/TILE_SIZE;
    public static final int TILEMAP_HEIGHT = GAME_HEIGHT/TILE_SIZE;
    public static final double ASPECT_RATIO = (double)GAME_WIDTH/GAME_HEIGHT;
}
