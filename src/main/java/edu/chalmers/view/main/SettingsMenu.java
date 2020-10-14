package edu.chalmers.view.main;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.controller.InputController;
import edu.chalmers.view.IMenu;
import edu.chalmers.view.nodes.ActionButton;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static edu.chalmers.view.util.ViewUtil.*;

/**
 * The settings menu for the game.
 */
public class SettingsMenu extends SubScene implements IMenu {
    /**
     * The label containing the title for this menu.
     */
    private Text titleText;

    /**
     * The control button for the "Jump" key.
     */
    private Node controlJumpButton;

    /**
     * The control button for the "Walk left" key.
     */
    private Node controlWalkLeftButton;

    /**
     * The control button for the "Walk right" key.
     */
    private Node controlWalkRightButton;

    /**
     * The control button for the "Reload" key.
     */
    private Node controlReloadButton;

    private Node controlFirstWeaponButton;

    private Node controlSecondWeaponButton;

    private Node controlThirdWeaponButton;

    /**
     * The back button.
     */
    private Node backButton;

    /**
     * Create the nodes for the settings menu.
     */
    @Override
    public void createNodes()
    {
        // Background
        addNode(this, getBackgroundNode(), 0, 0);

        // Title
        this.titleText = new Text(getTitle());
        this.titleText.setFill(Color.RED);
        this.titleText.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        addNode(this, this.titleText,
                (FXGL.getAppWidth() / 2.0) - (getTitle().length() * 16) / 2.0,
                FXGL.getAppHeight() / 4.0);

        // Control buttons
        this.controlJumpButton = addNode(this, createControlButton("Jump"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (0.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlWalkLeftButton = addNode(this, createControlButton("Walk left"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (5.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlWalkRightButton = addNode(this, createControlButton("Walk right"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (10.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlReloadButton = addNode(this, createControlButton("Reload"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (15.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));

        this.controlFirstWeaponButton = addNode(this, createControlButton("Weapon 1"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  + (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (0.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlSecondWeaponButton = addNode(this, createControlButton("Weapon 2"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  + (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (5.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlThirdWeaponButton = addNode(this, createControlButton("Weapon 3"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  + (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (10.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));

        // Main buttons
        this.backButton = addNode(this, createActionButton("Back", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (25.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
    }

    @Override
    public String getTitle() {
        return "Press one of the buttons below to change the keybindings";
    }

    private Node createControlButton(String text)
    {
        String keyDescription = resolveKeyDescription(text);
        ActionButton controlButton = createActionButton(String.format("%s - %s", KeyCode.getKeyCode(InputController.InputInstance.getTriggerName(keyDescription)), text), () -> { });

        controlButton.setTag(text);

        return controlButton;
    }

    public String resolveKeyDescription(String keyDescription)
    {
        switch (keyDescription)
        {
            case "Weapon 1":
                return "SwitchToFirstWeapon";
            case "Weapon 2":
                return "SwitchToSecondWeapon";
            case "Weapon 3":
                return "SwitchToThirdWeapon";
            default:
                return keyDescription;
        }
    }

    /**
     * Get the control button for the "Jump" key.
     * @return The control button
     */
    public Node getControlJumpButton()
    {
        return this.controlJumpButton;
    }

    /**
     * Get the control button for the "Walk left" key.
     * @return The control button
     */
    public Node getControlWalkLeftButton()
    {
        return this.controlWalkLeftButton;
    }

    /**
     * Get the control button for the "Walk right" key.
     * @return The control button
     */
    public Node getControlWalkRightButton()
    {
        return this.controlWalkRightButton;
    }

    /**
     * Get the control button for the "Reload" key.
     * @return The control button
     */
    public Node getControlReloadButton()
    {
        return this.controlReloadButton;
    }

    public Node getControlFirstWeaponButton() { return this.controlFirstWeaponButton; }

    public Node getControlSecondWeaponButton() { return this.controlSecondWeaponButton; }

    public Node getControlThirdWeaponButton() { return this.controlThirdWeaponButton; }

    /**
     * Get the description text node.
     * @return The description text node
     */
    public Text getTitleText()
    {
        return this.titleText;
    }

    /**
     * Get the back button
     * @return The back button
     */
    public Node getBackButton()
    {
        return this.backButton;
    }
}
