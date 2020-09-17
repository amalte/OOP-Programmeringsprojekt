package edu.chalmers.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Controller {
    private static boolean initialized = false;

    public void initPlayerMovementInput(final Entity p){
        UserAction walkRight = new UserAction("Walk right") {
            @Override
            protected void onAction() {
                p.getComponent(PlayerComponent.class).moveRight();
            }

            @Override
            protected void onActionEnd() {
                p.getComponent(PlayerComponent.class).stop();
            }
        };

        UserAction walkLeft = new UserAction("Walk left") {
            @Override
            protected void onAction() {
                p.getComponent(PlayerComponent.class).moveLeft();
            }

            @Override
            protected void onActionEnd() {
                p.getComponent(PlayerComponent.class).stop();
            }
        };

        UserAction jump = new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
                    @Override
                    protected void onCollisionBegin(Entity a, Entity b) {
                        p.getComponent(PlayerComponent.class).resetJumpAmounts();
                    }
                });
                p.getComponent(PlayerComponent.class).jump();
            }
        };
        UserAction shoot = new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                p.getComponent(PlayerComponent.class).shoot();
            }
        };

        if (!initialized)
        {
            Input input = getInput();
            input.addAction(walkRight, KeyCode.D);
            input.addAction(walkLeft, KeyCode.A);
            input.addAction(jump, KeyCode.W);
            input.addAction(shoot, MouseButton.PRIMARY);

            initialized = true;
        }

    }
}
