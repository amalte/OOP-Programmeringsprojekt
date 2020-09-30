package edu.chalmers.utils;

import edu.chalmers.utilities.Constants;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class GameSize {
    double aspectRatio = Constants.ASPECT_RATIO;

    /**
     * Get method for the width of the game (depends on aspect ratio and what the actual window size is)
     * @return int width of the game
     */
    public int getWidth() {
        if(getGameScene().getWidth()/aspectRatio > getGameScene().getHeight()) {
            return (int) Math.round(getGameScene().getHeight()*aspectRatio);
        }
        else {
            return (int) Math.round(getGameScene().getWidth());
        }
    }

    /**
     * Get method for the height of the game (depends on aspect ratio and what the actual window size is)
     * @return int height of the game
     */
    public int getHeight() {
        if(getGameScene().getHeight()*aspectRatio > getGameScene().getWidth()) {
            return (int) Math.round(getGameScene().getWidth()/aspectRatio);
        }
        else {
            return (int) Math.round(getGameScene().getHeight());
        }
    }
}
