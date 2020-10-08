package edu.chalmers.view;

import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.model.GenericPlatformer;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class GamePlayView {

    private GenericPlatformer game;

    public GamePlayView(GenericPlatformer game) {
        this.game = game;
    }

    /**
     * Initiate a game world builder.
     */
    public void initGameWorld(){ FXGL.getGameWorld().addEntityFactory(game.getGameWorldFactory()); }

    /**
     * Change current level to new level map.
     * @param levelName Name of the tmx file that the view should load.
     */
    public void changeLevel(String levelName) {
     FXGL.setLevelFromMap(levelName);
    }
}
