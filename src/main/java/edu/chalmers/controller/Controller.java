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
    private static Entity player = null;


    public void initPlayerMovementInput(final Entity p) {
        player = p;

        if (!initialized) {

            Input input = getInput();

            input.addAction(new UserAction("Walk right") {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).moveRight();
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
                    FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
                        @Override
                        protected void onCollisionBegin(Entity a, Entity b) {
                            player.getComponent(PlayerComponent.class).resetJumpAmounts();
                        }
                    });
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