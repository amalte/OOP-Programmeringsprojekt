package edu.chalmers.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.Player;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Controller {


    public void initPlayerMovementInput(final Player p){
        UserAction walkRight = new UserAction("Walk right") {
            @Override
            protected void onAction() {
                p.moveRight();
            }

            @Override
            protected void onActionEnd() {
                p.stop();
            }
        };

        UserAction walkLeft = new UserAction("Walk left") {
            @Override
            protected void onAction() {
                p.moveLeft();
            }

            @Override
            protected void onActionEnd() {
                p.stop();
            }
        };

        UserAction jump = new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
                    @Override
                    protected void onCollisionBegin(Entity a, Entity b) {
                        p.resetJumpAmounts();
                    }
                });
                p.jump();
            }
        };
        UserAction shoot = new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                p.shoot();
            }
        };

        Input input = getInput();
        input.addAction(walkRight, KeyCode.D);
        input.addAction(walkLeft, KeyCode.A);
        input.addAction(jump, KeyCode.W);
        input.addAction(shoot, MouseButton.PRIMARY);

    }
}
