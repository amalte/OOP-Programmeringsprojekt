package edu.chalmers.view;

import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.controller.Controller;
import edu.chalmers.model.GenericPlatformer;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class GamePlayView {

    private Controller controller;
    private GenericPlatformer game;

    public GamePlayView(Controller controller, GenericPlatformer game) {
        this.controller = controller;
        this.game = game;
    }

    /**
     * Initiate a game world builder.
     */
    public void initGameWorld(){
        getGameWorld().addEntityFactory(game.getGameWorldFactory());
    }

    /**
     * Change current level to new level map.
     * @param levelName Name of the tmx file that the view should load.
     */
    public void changeLevel(String levelName) {
     FXGL.setLevelFromMap(levelName);
    }
}
