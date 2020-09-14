package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import edu.chalmers.controller.Controller;
import edu.chalmers.model.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class main extends GameApplication {

    static Player p;
    Controller controller;

    WaveManager waveManager;

    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
        gameSettings.setTitle("2D Platformer Wave Game");
    }

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false"); // DO NOT REMOVE. Caps FPS at 60 across all computers
        launch(args);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameWorldFactory());
        FXGL.setLevelFromMap("map.tmx");
        controller = new Controller();
        p = new Player(0, 0);
        controller.initPlayerMovementInput(p);
        waveManager = new WaveManager(p);
        waveManager.generateNewWave();

        Enemy zombie = EnemyFactory.zombie(new SpawnData(400, 400), p);
    }
}
