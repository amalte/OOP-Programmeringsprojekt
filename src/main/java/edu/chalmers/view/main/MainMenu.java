package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import static edu.chalmers.view.main.MainViewUtil.*;

/**
 * The main menu for the game.
 */
public class MainMenu extends SubScene {
    /**
     * The font size of the title text.
     */
    private static final double TITLE_FONT_SIZE = 54;

    /**
     * The font size of the version text.
     */
    private static final double VERSION_FONT_SIZE = 12;

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
     * Default constructor.
     * Creates default controls.
     */
    public MainMenu() {
        this.createNodes();
    }

    /**
     * Creates the default buttons for the main menu.
     * Actions have to be set up from the controller.
     */
    private void createNodes()
    {
        // Main buttons
        this.playButton = addNode(this, createMenuButton("Play", () -> { }),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (0 * (ActionButton.BUTTON_WIDTH / 4)));
        this.settingsButton = addNode(this, createMenuButton("Settings", () -> { }),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (1 * (ActionButton.BUTTON_WIDTH / 4)));
        this.exitButton = addNode(this, createMenuButton("Exit", () -> { }),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (2 * (ActionButton.BUTTON_WIDTH / 4)));
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
