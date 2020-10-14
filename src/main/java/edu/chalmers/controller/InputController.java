package edu.chalmers.controller;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import edu.chalmers.controller.game.ExitMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.view.GameUI;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class InputController {
    private static boolean initialized = false;

    public static Input InputInstance;

    private GenericPlatformer game;
    private Main mainInstance;

    public InputController(GenericPlatformer game, Main mainInstance) {
        this.game = game;
        this.mainInstance = mainInstance;
    }

    public void initPlayerMovementInput() {
        if (!initialized) {
            InputInstance = getInput();

            GameUI gameUI = new GameUI(game);
            gameUI.setNodes();

            InputInstance.addAction(new UserAction("Exit menu") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        ExitMenuController exitMenuController = (ExitMenuController) mainInstance.getController(GameMenuType.Exit);

                        if (!exitMenuController.doNotHandleEscape)
                            exitMenuController.show();
                        else
                            exitMenuController.doNotHandleEscape = false;
                    }
                }
            }, KeyCode.ESCAPE);

            InputInstance.addAction(new UserAction("Walk right") {
                @Override
                protected void onAction() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).moveRight();
                    }
                }

                @Override
                protected void onActionEnd() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).stop();
                    }
                }
            }, KeyCode.D);

            InputInstance.addAction(new UserAction("Walk left") {
                @Override
                protected void onAction() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).moveLeft();
                    }
                }

                @Override
                protected void onActionEnd() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).stop();
                    }
                }
            }, KeyCode.A);

            InputInstance.addAction(new UserAction("Jump") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).jump();
                    }
                }
            }, KeyCode.W);

            InputInstance.addAction(new UserAction("Shoot") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).shoot();
                    }
                }
            }, MouseButton.PRIMARY);

            InputInstance.addAction(new UserAction("PlaceBlock") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning())
                    {
                        if(game.getBuildManager().possibleToPlaceBlockOnPos(InputInstance.getMousePositionWorld(), EntityPos.getPosition(getPlayer()))) {
                            game.getBuildManager().placeBlock(InputInstance.getMousePositionWorld());
                            //getPlayer().getComponent(PlayerComponent.class).placeBlock(input.getMousePositionWorld());
                        }
                    }
                }

            }, MouseButton.SECONDARY);

            InputInstance.addAction(new UserAction("Reload") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).reload();
                    }
                }
            }, KeyCode.R);

            InputInstance.addEventHandler(MouseDragEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {   // For Building UI
                @Override
                public void handle(MouseEvent event) {
                    if (mainInstance.isGameRunning())
                    {
                        // Should only be called if entered new tile
                        if(game.getBuildManager().isInBuildRange(CoordsCalculations.posToTile(InputInstance.getMousePositionWorld()), CoordsCalculations.posToTile(EntityPos.getPosition(getPlayer())))) {
                            mainInstance.getBuildView().followMouse(InputInstance.getMousePositionWorld(), game.getBuildManager().possibleToPlaceBlockOnPos(InputInstance.getMousePositionWorld(), EntityPos.getPosition(getPlayer())));
                        }
                        else {
                            mainInstance.getBuildView().stopFollowMouse();
                        }

                        mainInstance.getBuildView().reachableTiles(game.getBuildManager().getEmptyReachableTiles(CoordsCalculations.posToTile(EntityPos.getPosition(getPlayer()))));

                        //buildView.followMouse(TileCalculations.posToTilePos(input.getMousePositionWorld(), Constants.TILE_SIZE), getPlayer().getComponent(PlayerComponent.class).getBuilding().possibleToPlaceBlockOnPos(input.getMousePositionWorld(), EntityPos.getPosition(getPlayer())));
                    }
                }
            });

            InputInstance.addAction(new UserAction("SwitchToFirstWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(0);
                    }
                }
            }, KeyCode.DIGIT1);

            InputInstance.addAction(new UserAction("SwitchToSecondWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(1);
                    }
                }
            }, KeyCode.DIGIT2);

            InputInstance.addAction(new UserAction("SwitchToThirdWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(2);
                    }
                }
            }, KeyCode.DIGIT3);

            initialized = true;
        }
    }
    
    private Entity getPlayer()
    {
        return game.getPlayer();
    }
}