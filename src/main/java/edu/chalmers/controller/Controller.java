package edu.chalmers.controller;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.PlayerComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Controller {
    private static boolean initialized = false;
    private static Entity player = null;
    private GenericPlatformer game;

    public Controller(GenericPlatformer game) {
        this.game = game;
    }

    public void initPlayerMovementInput() {
        player = game.getPlayer();

        if (!initialized) {
            UserAction walkRight = new UserAction("Walk right") {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).moveRight();
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            };

            UserAction walkLeft = new UserAction("Walk left") {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).moveLeft();
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            };

            UserAction jump = new UserAction("Jump") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).jump();
                }
            };
            UserAction shoot = new UserAction("Shoot") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).shoot();
                }
            };

            Input input = getInput();
            input.addAction(walkRight, KeyCode.D);
            input.addAction(walkLeft, KeyCode.A);
            input.addAction(jump, KeyCode.W);
            input.addAction(shoot, MouseButton.PRIMARY);

            initialized = true;
        }
    }
}