package edu.chalmers.controller.main;

import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.Trigger;
import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.InputController;
import edu.chalmers.controller.MenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.SettingsMenu;
import edu.chalmers.view.nodes.ActionButton;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getInput;

/**
 * The controller for the settings menu.
 */
public class SettingsMenuController extends MenuController<SettingsMenu> {
    private ActionButton activatedButton;
    private MainMenuController mainMenuController;

    /**
     * Default constructor for SettingsMenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with.
     * @param mainInstance An instance of the Main class.
     */
    public SettingsMenuController(SettingsMenu viewInstance, Main mainInstance)
    {
        super(viewInstance, mainInstance, GameMenuType.Settings);
    }

    /**
     * Initialize the nodes (make view create them, binds actions to them, etc.)
     */
    @Override
    protected void initializeNodes() {
        super.initializeNodes();

        getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> {
            if (!rebindControl(keyEvent.getCode()) && keyEvent.getCode() == KeyCode.ESCAPE)
                this.returnToMain();
        });

        getViewInstance().getControlJumpButton().setOnMousePressed(mouseEvent -> handleControlButtonPress((ActionButton)mouseEvent.getSource()));
        getViewInstance().getControlWalkLeftButton().setOnMousePressed(mouseEvent -> handleControlButtonPress((ActionButton)mouseEvent.getSource()));
        getViewInstance().getControlWalkRightButton().setOnMousePressed(mouseEvent -> handleControlButtonPress((ActionButton)mouseEvent.getSource()));
        getViewInstance().getControlReloadButton().setOnMousePressed(mouseEvent -> handleControlButtonPress((ActionButton)mouseEvent.getSource()));

        getViewInstance().getControlFirstWeaponButton().setOnMousePressed(mouseEvent -> handleControlButtonPress((ActionButton)mouseEvent.getSource()));
        getViewInstance().getControlSecondWeaponButton().setOnMousePressed(mouseEvent -> handleControlButtonPress((ActionButton)mouseEvent.getSource()));
        getViewInstance().getControlThirdWeaponButton().setOnMousePressed(mouseEvent -> handleControlButtonPress((ActionButton)mouseEvent.getSource()));

        getViewInstance().getBackButton().setOnMousePressed(mouseEvent -> returnToMain());
    }

    private void returnToMain()
    {
        getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> { });
        this.restoreActivatedButton();
        this.hide();

        if (this.mainMenuController != null)
            this.mainMenuController.show();
    }

    /**
     * Set the instance of MainMenuController.
     * @param mainMenuController An instance of the MainMenuController
     */
    public void setMainMenuController(MainMenuController mainMenuController)
    {
        this.mainMenuController = mainMenuController;
    }

    private void restoreActivatedButton()
    {
        if (this.activatedButton != null)
        {
            synchronized (this.activatedButton)
            {
                String tag = activatedButton.getTag();
                String keyDescription = getViewInstance().resolveKeyDescription(tag);

                KeyCode oldKeyCode = KeyCode.getKeyCode(InputController.getInputInstance().getTriggerName(keyDescription));

                ((Text)activatedButton.getChildren().get(1)).setText(String.format("%s - %s", oldKeyCode.toString(), tag));
            }

            this.activatedButton = null;
        }
    }

    private void handleControlButtonPress(ActionButton activatedButton)
    {
        if (this.activatedButton == null)
        {
            synchronized (activatedButton)
            {
                ((Text)activatedButton.getChildren().get(1)).setText("Press any key..");
            }

            this.activatedButton = activatedButton;
        }
    }

    private Boolean rebindControl(KeyCode newKeyCode)
    {
        if (this.activatedButton != null)
        {
            synchronized (this.activatedButton)
            {
                String tag = this.activatedButton.getTag();
                String keyDescription = getViewInstance().resolveKeyDescription(tag);
                KeyCode oldKeyCode = KeyCode.getKeyCode(InputController.getInputInstance().getTriggerName(keyDescription));

                if (oldKeyCode != newKeyCode)
                {
                    for (Trigger trigger : InputController.getInputInstance().getAllBindings().values())
                    {
                        if (trigger instanceof KeyTrigger)
                        {
                            KeyTrigger keyTrigger = (KeyTrigger)trigger;

                            if (keyTrigger.component1() == newKeyCode)
                                return true;
                        }
                    }
                }

                InputController.getInputInstance().rebind(InputController.getInputInstance().getActionByName(keyDescription), newKeyCode);
                ((Text)this.activatedButton.getChildren().get(1)).setText(String.format("%s - %s", newKeyCode.toString(), tag));
            }

            activatedButton = null;

            return true;
        }
        else {
            return false;
        }
    }
}
