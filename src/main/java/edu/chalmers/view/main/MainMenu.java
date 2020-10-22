package edu.chalmers.view.main;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.view.IMenu;
import edu.chalmers.view.nodes.ActionButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static edu.chalmers.view.util.ViewUtil.*;

/**
 * @author Anwarr Shiervani
 * <p>
 * The main menu.
 */
public class MainMenu extends SubScene implements IMenu {
    private Text titleText;
    private ActionButton playButton;
    private ActionButton settingsButton;
    private ActionButton exitButton;

    /**
     * Create the nodes for this menu.
     */
    @Override
    public void createNodes() {
        // Background
        addNode(this, getBackgroundNode(), 0, 0);

        // Title
        this.titleText = new Text(getTitle());
        this.titleText.setFill(Color.RED);
        this.titleText.setStyle("-fx-font-size: 64; -fx-font-weight: bold;");
        addNode(this, this.titleText,
                (FXGL.getAppWidth() / 2.0) - (getTitle().length() * 32) / 2.0,
                FXGL.getAppHeight() / 5.0);

        // Main buttons
        this.playButton = addNode(this, createActionButton("Play", () -> {
                }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0) + (0.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.settingsButton = addNode(this, createActionButton("Settings", () -> {
                }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0) + (5.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.exitButton = addNode(this, createActionButton("Exit", () -> {
                }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0) + (10.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
    }

    /**
     * @return The title of this view.
     */
    @Override
    public String getTitle() {
        return "Generic Platformer";
    }

    /**
     * @return The play button.
     */
    public ActionButton getPlayButton() {
        return this.playButton;
    }

    /**
     * @return The settings button.
     */
    public ActionButton getSettingsButton() {
        return this.settingsButton;
    }

    /**
     * @return The exit button.
     */
    public ActionButton getExitButton() {
        return this.exitButton;
    }
}
