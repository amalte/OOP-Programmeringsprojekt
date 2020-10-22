package edu.chalmers.controller;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import edu.chalmers.controller.game.ExitMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.utilities.EntityPos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

/**
 * @author Oscar Arvidson och Erik Wetter
 *
 * This class handles player movement input.
 */
public class InputController {
    private static boolean initialized = false;
    private static Input inputInstance;

    private GenericPlatformer game;
    private Main mainInstance;
    private Boolean doNotHandleEscape = false;

    /**
     * Default constructor for InputController.
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
            inputInstance = getInput();

            inputInstance.addAction(new UserAction("Exit menu") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning()) {
                        ExitMenuController exitMenuController = (ExitMenuController) mainInstance.getController(GameMenuType.Exit);

                        if (!InputController.this.getDoNotHandleEscape())
                            exitMenuController.show();
                        else
                            InputController.this.setDoNotHandleEscape(false);
                    }
                }
            }, KeyCode.ESCAPE);

            inputInstance.addAction(new UserAction("Walk right") {
                @Override
                protected void onAction() {
                    if (mainInstance.getGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).moveRight();
                    }
                }

                @Override
                protected void onActionEnd() {
                    if (mainInstance.getGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).stop();
                    }
                }
            }, KeyCode.D);

            inputInstance.addAction(new UserAction("Walk left") {
                @Override
                protected void onAction() {
                    if (mainInstance.getGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).moveLeft();
                    }
                }

                @Override
                protected void onActionEnd() {
                    if (mainInstance.getGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).stop();
                    }
                }
            }, KeyCode.A);

            inputInstance.addAction(new UserAction("Jump") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning())
                    {
                        getPlayer().getComponent(PlayerComponent.class).jump();
                    }
                }
            }, KeyCode.W);

            inputInstance.addAction(new UserAction("Shoot") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).shoot();
                        mainInstance.getGameUI().updateAmmunition();
                    }
                }
            }, MouseButton.PRIMARY);

            inputInstance.addAction(new UserAction("PlaceBlock") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning())
                    {
                        if(game.getBuildManager().possibleToPlaceBlockOnPos(inputInstance.getMousePositionWorld(), EntityPos.getPosition(getPlayer()))) {
                            game.getBuildManager().placeBlock(inputInstance.getMousePositionWorld());
                        }
                    }
                }

            }, MouseButton.SECONDARY);

            inputInstance.addAction(new UserAction("Reload") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).getActiveWeapon();

                        if (getPlayer().getComponent(PlayerComponent.class).getActiveWeapon().getMagazineCounter() < getPlayer().getComponent(PlayerComponent.class).getActiveWeapon().getMagazineSize())
                            getPlayer().getComponent(PlayerComponent.class).reload();
                    }
                }
            }, KeyCode.R);

            inputInstance.addAction(new UserAction("SwitchToFirstWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(0);
                        mainInstance.getGameUI().updateActiveWeapon();
                        mainInstance.getGameUI().updateAmmunition();
                        mainInstance.getGameUI().updateReloading();
                    }
                }
            }, KeyCode.DIGIT1);

            inputInstance.addAction(new UserAction("SwitchToSecondWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(1);
                        mainInstance.getGameUI().updateActiveWeapon();
                        mainInstance.getGameUI().updateAmmunition();
                        mainInstance.getGameUI().updateReloading();
                    }
                }
            }, KeyCode.DIGIT2);

            inputInstance.addAction(new UserAction("SwitchToThirdWeapon") {
                @Override
                protected void onActionBegin() {
                    if (mainInstance.getGameRunning()) {
                        getPlayer().getComponent(PlayerComponent.class).setActiveWeapon(2);
                        mainInstance.getGameUI().updateActiveWeapon();
                        mainInstance.getGameUI().updateAmmunition();
                        mainInstance.getGameUI().updateReloading();
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

    /**
     * @return Get the associated instance of the Input class.
     */
    public static Input getInputInstance() { return inputInstance; }
}