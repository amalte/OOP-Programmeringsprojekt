package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.EnemyFactory;
import edu.chalmers.model.Player;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class main extends GameApplication {

    Entity player;
    Entity enemy;

    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1300);
        gameSettings.setHeight(800);
        gameSettings.setTitle("2D Platformer Wave Game");
    }

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false"); // DO NOT REMOVE. Caps FPS at 60 across all computers
        launch(args);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(EnemyFactory.get());

        player = new Player(50, 50);
        enemy = getGameWorld().spawn(EnemyFactory.ENEMY, 300, 50);
        getGameWorld().spawn(EnemyFactory.ENEMY, 375, 50);
    }

    @Override
    protected void initInput() {
        // TODO
    }

    @Override
    protected void onUpdate(double tpf) {
        // TODO
    }
}
