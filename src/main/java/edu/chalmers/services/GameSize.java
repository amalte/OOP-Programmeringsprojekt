package edu.chalmers.services;

import edu.chalmers.utilities.Constants;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class GameSize {
    double aspectRatio = Constants.ASPECT_RATIO;

    public int getWidth() {
        if(getGameScene().getWidth()/aspectRatio > getGameScene().getHeight()) {
            return (int) Math.round(getGameScene().getHeight()*aspectRatio);
        }
        else {
            return (int) Math.round(getGameScene().getWidth());
        }
    }

    public int getHeight() {
        if(getGameScene().getHeight()*aspectRatio > getGameScene().getWidth()) {
            return (int) Math.round(getGameScene().getWidth()/aspectRatio);
        }
        else {
            return (int) Math.round(getGameScene().getHeight());
        }
    }
}
