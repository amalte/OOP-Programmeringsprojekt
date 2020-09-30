package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import edu.chalmers.controller.Controller;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.utilities.Constants;
import edu.chalmers.view.GamePlayView;
import edu.chalmers.view.main.MainMenu;

public class Main extends GameApplication {

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false"); // DO NOT REMOVE. Caps FPS at 60 across all computers
        launch(args);
    }

    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setPreserveResizeRatio(true);
        gameSettings.setManualResizeEnabled(true);
        gameSettings.setFullScreenAllowed(true);
        //gameSettings.setFullScreenFromStart(true);    // Start game in fullscreen
        gameSettings.setWidth(Constants.GAME_WIDTH);
        gameSettings.setHeight(Constants.GAME_HEIGHT);
        gameSettings.setTitle("Generic Platformer");
        gameSettings.setVersion("1.0");

        gameSettings.setMainMenuEnabled(true);
        gameSettings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return MainMenu.getInstance();
            }
        });
    }

    @Override
    protected void initGame() {
        GenericPlatformer game = new GenericPlatformer();
        Controller controller = new Controller(game);
        GamePlayView gameView = new GamePlayView(game);
        gameView.initGameWorld();
        gameView.changeLevel("map2.tmx");

        controller.initPlayerMovementInput();
        game.initCollisionDetection();
        game.initWaveManager();

        game.getWaveManager().generateNewWave();
    }
}
