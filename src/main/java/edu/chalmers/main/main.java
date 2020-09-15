package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import edu.chalmers.controller.Controller;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.view.GamePlayView;

public class main extends GameApplication {

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false"); // DO NOT REMOVE. Caps FPS at 60 across all computers
        launch(args);
    }

    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
        gameSettings.setTitle("Generic Platformer");
    }

    @Override
    protected void initGame() {
        Controller controller = new Controller();
        GenericPlatformer game = new GenericPlatformer();
        GamePlayView gameView = new GamePlayView(controller, game);
        gameView.initGameWorld();
        gameView.changeLevel("map.tmx");
        controller.initPlayerMovementInput(game.getPlayer());
        game.initWaveManager();

        //Put in controller later
        //game.getWaveManager().generateNewWave();
    }
}
