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
 * The main menu for the game.
 */
public class MainMenu extends SubScene implements IMenu {
    /**
     * The label containing the title for this menu.
     */
    private Text titleText;

    /**
     * The play button.
     */
    private Node playButton;

    /**
     * The settings button.
     */
    private Node settingsButton;

    /**
     * The exit button.
     */
    private Node exitButton;

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
        this.titleText.setStyle("-fx-font-size: 64; -fx-font-weight: bold;");
        addNode(this, this.titleText,
                (FXGL.getAppWidth() / 2.0) - (getTitle().length() * 32) / 2.0,
                FXGL.getAppHeight() / 5.0);

        // Main buttons
        this.playButton = addNode(this, createActionButton("Play", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0) + (0.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.settingsButton = addNode(this, createActionButton("Settings", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0) + (5.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
        this.exitButton = addNode(this, createActionButton("Exit", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0) + (10.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
    }

    @Override
    public String getTitle() {
        return "Generic Platformer";
    }

    /**
     * Get the play button
     * @return The play button
     */
    public Node getPlayButton()
    {
        return this.playButton;
    }

    /**
     * Get the settings button
     * @return The settings button
     */
    public Node getSettingsButton()
    {
        return this.settingsButton;
    }

    /**
     * Get the exit button
     * @return The exit button
     */
    public Node getExitButton()
    {
        return this.exitButton;
    }
}
