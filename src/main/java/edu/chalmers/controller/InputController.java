package edu.chalmers.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.services.Coords;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.view.BuildView;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class InputController {
    private static boolean initialized = false;
    private static Entity player = null;
    private GenericPlatformer game;

    public InputController(GenericPlatformer game) {
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

            input.addAction(new UserAction("PlaceBlock") {
                @Override
                protected void onActionBegin() {
                    if(game.getBuildManager().possibleToPlaceBlockOnPos(input.getMousePositionWorld(), EntityPos.getPosition(player))) {
                        game.getBuildManager().placeBlock(input.getMousePositionWorld());
                        //player.getComponent(PlayerComponent.class).placeBlock(input.getMousePositionWorld());
                    }
                }

            }, MouseButton.SECONDARY);

            input.addAction(new UserAction("Reload") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).reload();
                }
            }, KeyCode.R);

            BuildView buildView = new BuildView();
            buildView.buildStateSelected();
            buildView.setUpTransparentTiles();

            input.addEventHandler(MouseDragEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {   // For Building UI
                @Override
                public void handle(MouseEvent event) {
                    // Should only be called if entered new tile
                    if(game.getBuildManager().isInBuildRange(CoordsCalculations.posToTile(input.getMousePositionWorld()), CoordsCalculations.posToTile(EntityPos.getPosition(player)))) {
                        buildView.followMouse(input.getMousePositionWorld(), game.getBuildManager().possibleToPlaceBlockOnPos(input.getMousePositionWorld(), EntityPos.getPosition(player)));
                    }
                    else {
                        buildView.stopFollowMouse();
                    }
                    buildView.reachableTiles(game.getBuildManager().getEmptyReachableTiles(CoordsCalculations.posToTile(EntityPos.getPosition(player))));

                    //buildView.followMouse(TileCalculations.posToTilePos(input.getMousePositionWorld(), Constants.TILE_SIZE), player.getComponent(PlayerComponent.class).getBuilding().possibleToPlaceBlockOnPos(input.getMousePositionWorld(), EntityPos.getPosition(player)));
                }
            });


            input.addAction(new UserAction("SwitchWeapon0") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).setActiveWeapon(0);
                }
            }, KeyCode.DIGIT1);

            input.addAction(new UserAction("SwitchWeapon1") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).setActiveWeapon(1);
                }
            }, KeyCode.DIGIT2);

            input.addAction(new UserAction("SwitchWeapon2") {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).setActiveWeapon(2);
                }
            }, KeyCode.DIGIT3);

            initialized = true;
        }
    }
}