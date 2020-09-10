package edu.chalmers.controller;

import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import edu.chalmers.model.Player;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Controller {


    public void initPlayerMovementInput(final Player p){
        UserAction walkRight = new UserAction("walk right") {
            @Override
            protected void onAction() {
                p.moveRight();
            }

            @Override
            protected void onActionEnd() {
                p.stop();
            }
        };

        UserAction walkLeft = new UserAction("walk left") {
            @Override
            protected void onAction() {
                p.moveLeft();
            }

            @Override
            protected void onActionEnd() {
                p.stop();
            }
        };

        UserAction jump = new UserAction("jump") {
            @Override
            protected void onAction() {
                p.jump();
            }

            @Override
            protected void onActionEnd() {
                p.stop();
            }
        };

        Input input = getInput();
        input.addAction(walkRight, KeyCode.D);
        input.addAction(walkLeft, KeyCode.A);
        input.addAction(jump, KeyCode.W);

    }
}
