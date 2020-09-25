package edu.chalmers.controller;

import com.almasb.fxgl.app.FXGLApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import edu.chalmers.GameSize;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.PlayerComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Controller {
    private static boolean initialized = false;
    private static Entity player = null;
    private GenericPlatformer game;

    private GameSize gameSize = new GameSize();

    public Controller(GenericPlatformer game) {
        this.game = game;
    }

    public void initPlayerMovementInput() {
        player = game.getPlayer();

        if (!initialized) {

            Input input = getInput();

            input.addAction(new UserAction("Walk right") {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).moveRight();
                    System.out.println("Width: " + gameSize.getWidth());
                    System.out.println("Height: " + gameSize.getHeight());
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, KeyCode.D);

            input.addAction(new UserAction("Walk left") {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).moveLeft();
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, KeyCode.A);

            input.addAction(new UserAction("Jump") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).jump();
                }
            }, KeyCode.W);

            input.addAction(new UserAction("Shoot") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).shoot();
                }
            }, MouseButton.PRIMARY);

            input.addAction(new UserAction("Reload") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).reload();
                }
            }, KeyCode.R);

            initialized = true;
        }
    }
}