package edu.chalmers.view.main;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.view.IMenu;
import edu.chalmers.view.nodes.ActionButton;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static edu.chalmers.view.util.ViewUtil.*;

/**
 * @author Anwarr Shiervani
 *
 * The settings menu.
 */
public class SettingsMenu extends SubScene implements IMenu {
    private Text titleText;
    private ActionButton controlJumpButton;
    private ActionButton controlWalkLeftButton;
    private ActionButton controlWalkRightButton;
    private ActionButton controlReloadButton;
    private ActionButton controlFirstWeaponButton;
    private ActionButton controlSecondWeaponButton;
    private ActionButton controlThirdWeaponButton;
    private ActionButton backButton;

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
        this.controlJumpButton = addNode(this, createActionButton("", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (0.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlWalkLeftButton = addNode(this, createActionButton("", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (5.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlWalkRightButton = addNode(this, createActionButton("", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (10.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlReloadButton = addNode(this, createActionButton("", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  - (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (15.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));

        this.controlFirstWeaponButton = addNode(this, createActionButton("", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  + (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (0.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlSecondWeaponButton = addNode(this, createActionButton("", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0)  + (2.5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 3.0) - (ActionButton.BUTTON_HEIGHT / 2.0) + (5.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.controlThirdWeaponButton = addNode(this, createActionButton("", () -> { }),
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

    /**
     * @return The control button for the Jump key.
     */
    public ActionButton getControlJumpButton()
    {
        return this.controlJumpButton;
    }

    /**
     * @return The control button for the Walk Left key.
     */
    public ActionButton getControlWalkLeftButton()
    {
        return this.controlWalkLeftButton;
    }

    /**
     * @return The control button for the Walk Right key.
     */
    public ActionButton getControlWalkRightButton()
    {
        return this.controlWalkRightButton;
    }

    /**
     * @return The control button for the Reload key.
     */
    public ActionButton getControlReloadButton()
    {
        return this.controlReloadButton;
    }

    /**
     * @return The control button for the First Weapon key.
     */
    public ActionButton getControlFirstWeaponButton() { return this.controlFirstWeaponButton; }

    /**
     * @return The control button for the Second Weapon key.
     */
    public ActionButton getControlSecondWeaponButton() { return this.controlSecondWeaponButton; }

    /**
     * @return The control button for the Third Weapon key.
     */
    public ActionButton getControlThirdWeaponButton() { return this.controlThirdWeaponButton; }

    /**
     * @return The back button.
     */
    public ActionButton getBackButton()
    {
        return this.backButton;
    }
}
