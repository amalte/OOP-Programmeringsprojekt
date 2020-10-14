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
 * The settings menu.
 */
public class SettingsMenu extends SubScene implements IMenu {
    private Text titleText;
    private Node controlJumpButton;
    private Node controlWalkLeftButton;
    private Node controlWalkRightButton;
    private Node controlReloadButton;
    private Node controlFirstWeaponButton;
    private Node controlSecondWeaponButton;
    private Node controlThirdWeaponButton;
    private Node backButton;

    /**
     * Create the nodes for this menu.
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

    /**
     * @return The title of this view.
     */
    @Override
    public String getTitle() {
        return "Press one of the buttons below to change the keybindings";
    }

    private Node createControlButton(String text)
    {
        String keyDescription = resolveKeyDescription(text);
        ActionButton controlButton = createActionButton(String.format("%s - %s", KeyCode.getKeyCode(InputController.getInputInstance().getTriggerName(keyDescription)), text), () -> { });

        controlButton.setTag(text);

        return controlButton;
    }

    /**
     * Resolve the description of a keybinding to a name registered in FXGL.
     * @param keyDescription The description of the keybinding.
     * @return The name of the keybinding registered in FXGL. Does no additional check to check if it is really registered in FXGL.
     */
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
     * @return The control button for the Jump key.
     */
    public Node getControlJumpButton()
    {
        return this.controlJumpButton;
    }

    /**
     * @return The control button for the Walk Left key.
     */
    public Node getControlWalkLeftButton()
    {
        return this.controlWalkLeftButton;
    }

    /**
     * @return The control button for the Walk Right key.
     */
    public Node getControlWalkRightButton()
    {
        return this.controlWalkRightButton;
    }

    /**
     * @return The control button for the Reload key.
     */
    public Node getControlReloadButton()
    {
        return this.controlReloadButton;
    }

    /**
     * @return The control button for the First Weapon key.
     */
    public Node getControlFirstWeaponButton() { return this.controlFirstWeaponButton; }

    /**
     * @return The control button for the Second Weapon key.
     */
    public Node getControlSecondWeaponButton() { return this.controlSecondWeaponButton; }

    /**
     * @return The control button for the Third Weapon key.
     */
    public Node getControlThirdWeaponButton() { return this.controlThirdWeaponButton; }

    /**
     * @return The back button.
     */
    public Node getBackButton()
    {
        return this.backButton;
    }
}
