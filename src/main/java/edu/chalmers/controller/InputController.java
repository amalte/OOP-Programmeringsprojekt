package edu.chalmers.controller;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import edu.chalmers.controller.game.ExitMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.model.AnimationComponent;
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

/**
 * This class handles player movement input.
 */
public class InputController {
    private static boolean initialized = false;
    private GenericPlatformer game;
    private Main mainInstance;
    private Boolean doNotHandleEscape = false;

    /**
     * Default constructor for InputController.
     *
     * @param game An instance of the GenericPlatformer class.
     * @param mainInstance An instance of the Main class.
     */
    public InputController(GenericPlatformer game, Main mainInstance) {
        this.game = game;
        this.mainInstance = mainInstance;
    }

    /**
     * Initialize player input. Bind actions to different keys.
     */
    public void initPlayerInput() {
        if (!initialized) {
            GameUI gameUI = new GameUI(game);
            gameUI.setNodes();

            getInput().addAction(new UserAction("Exit menu") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        ExitMenuController exitMenuController = (ExitMenuController) mainInstance.getController(GameMenuType.Exit);

                        if (!InputController.this.getDoNotHandleEscape())
                            exitMenuController.show();
                        else
                            InputController.this.setDoNotHandleEscape(false);
                    }
                }
            }, KeyCode.ESCAPE);

            getInput().addAction(new UserAction("Walk right") {
                @Override
                protected void onAction() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).moveRight();
                        getPlayer().getComponent(AnimationComponent.class).moveRight();
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

            getInput().addAction(new UserAction("Walk left") {
                @Override
                protected void onAction() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).moveLeft();
                        getPlayer().getComponent(AnimationComponent.class).moveLeft();
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

            getInput().addAction(new UserAction("Jump") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).jump();
                    }
                }
            }, KeyCode.W);

            getInput().addAction(new UserAction("Shoot") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).shoot();
                    }
                }
            }, MouseButton.PRIMARY);

            getInput().addAction(new UserAction("PlaceBlock") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning())
                    {
                        if(game.getBuildManager().possibleToPlaceBlockOnPos(getInput().getMousePositionWorld(), EntityPos.getPosition(getPlayer()))) {
                            game.getBuildManager().placeBlock(getInput().getMousePositionWorld());
                            //getPlayer().getComponent(PlayerComponent.class).placeBlock(input.getMousePositionWorld());
                        }
                    }
                }

            }, MouseButton.SECONDARY);

            getInput().addAction(new UserAction("Reload") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).reload();
                    }
                }
            }, KeyCode.R);

            getInput().addEventHandler(MouseDragEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {   // For Building UI
                @Override
                public void handle(MouseEvent event) {
                    if (mainInstance.isGameRunning())
                    {
                        // Should only be called if entered new tile
                        if(game.getBuildManager().isInBuildRange(CoordsCalculations.posToTile(getInput().getMousePositionWorld()), CoordsCalculations.posToTile(EntityPos.getPosition(getPlayer())))) {
                            mainInstance.getBuildView().followMouse(getInput().getMousePositionWorld(), game.getBuildManager().possibleToPlaceBlockOnPos(getInput().getMousePositionWorld(), EntityPos.getPosition(getPlayer())));
                        }
                        else {
                            mainInstance.getBuildView().stopFollowMouse();
                        }

                        mainInstance.getBuildView().reachableTiles(game.getBuildManager().getEmptyReachableTiles(CoordsCalculations.posToTile(EntityPos.getPosition(getPlayer()))));

                        //buildView.followMouse(TileCalculations.posToTilePos(input.getMousePositionWorld(), Constants.TILE_SIZE), getPlayer().getComponent(PlayerComponent.class).getBuilding().possibleToPlaceBlockOnPos(input.getMousePositionWorld(), EntityPos.getPosition(getPlayer())));
                    }
                }
            });

            getInput().addAction(new UserAction("SwitchToFirstWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(0);
                    }
                }
            }, KeyCode.DIGIT1);

            getInput().addAction(new UserAction("SwitchToSecondWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.isGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(1);
                    }
                }
            }, KeyCode.DIGIT2);

            getInput().addAction(new UserAction("SwitchToThirdWeapon") {
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

    /**
     * Set the next escape key event not to be handled.
     * @param doNotHandleEscape Whether or not the next escape key event should be handled in initPlayerInput().
     */
    public void setDoNotHandleEscape(Boolean doNotHandleEscape)
    {
        this.doNotHandleEscape = doNotHandleEscape;
    }

    /**
     * @return Whether or not the next escape key event should be handled in initPlayerInput().
     */
    public Boolean getDoNotHandleEscape()
    {
        return this.doNotHandleEscape;
    }
}