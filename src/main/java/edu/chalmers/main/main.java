package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.controller.Controller;
import edu.chalmers.model.GameWorldFactory;
import edu.chalmers.model.Player;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class main extends GameApplication {

    Player p;
    Controller controller;

    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(15*70);
        gameSettings.setHeight(10*70);
        gameSettings.setTitle("Game Test");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameWorldFactory());
        FXGL.setLevelFromMap("map.tmx");
        controller = new Controller();
        p = new Player(0, 0);
        controller.initPlayerMovementInput(p);
    }
}
