package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
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
 * Main menu for the game.
 */
public class MainMenu extends FXGLMenu {
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
     * "Last" instance of this class
     */
    private static MainMenu instance;

    /**
     * Default constructor.
     * Creates default controls.
     */
    MainMenu() {
        super(MenuType.MAIN_MENU);

        this.createControls();
    }

    /**
     * Get the last instance of this class.
     * @return Instance of this class
     */
    public static MainMenu getInstance()
    {
        if (instance == null)
            instance = new MainMenu();

        return instance;
    }

    /**
     * Creates the default buttons for the main menu.
     * Actions have to be set up from the controller.
     */
    private void createControls()
    {
        /**
         * NOTE: Set all actions to () -> { } (empty code-block) when controller is created for this class.
         * And bind this::fireNewGame, etc. over there instead.
        */

        /**
         * Play button.
         * Expected action: this::fireNewGame
         */
        this.playButton = addNode(this, createMenuButton("Play", this::fireNewGame),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (0 * (ActionButton.BUTTON_WIDTH / 4)));

        /**
         * Settings button.
         * Expected action: Open the Settings menu.
         */
        this.settingsButton = addNode(this, createMenuButton("Settings", () -> { }),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (1 * (ActionButton.BUTTON_WIDTH / 4)));

        /**
         * Exit button.
         * Expected action: this::fireExit
         */
        this.exitButton = addNode(this, createMenuButton("Exit", this::fireExit),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (2 * (ActionButton.BUTTON_WIDTH / 4)));
    }

    /**
     * Create a menu button for this menu, using the ActionButton class.
     * @param text The text of the menu button
     * @param action The action of the menu button
     * @return The action button that was created
     */
    private StackPane createMenuButton(String text, Runnable action)
    {
        return new ActionButton(text, action);
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

    /**
     * Creates a background for the main menu.
     * @param width Width of the background
     * @param height Height of the background
     * @return ImageView that contains the background of the main menu.
     */
    @NotNull
    @Override
    protected Node createBackground(double width, double height) {
        return new ImageView(new Image("/assets/background.png", width, height, false, false));
    }

    /**
     * Create the title text for the menu.
     * @param title The title of the program
     * @return The Text node that was created
     */
    @NotNull
    @Override
    protected Node createTitleView(@NotNull String title) {
        Text text = new Text((FXGL.getAppWidth() / 2) - (TITLE_FONT_SIZE * title.length() * 0.3), (FXGL.getAppHeight() / 4) - (TITLE_FONT_SIZE / 4), title);
        text.setFill(Color.CORAL);
        text.setStyle("-fx-font-size: " + TITLE_FONT_SIZE + "; -fx-font-weight: bold; -fx-alignment: center;");

        return text;
    }

    /**
     * Create the version text for the menu.
     * @param version The version of the program
     * @return The Text node that was created
     */
    @NotNull
    @Override
    protected Node createVersionView(@NotNull String version) {
        Text text = new Text(FXGL.getAppWidth() - ((12 * version.length()) / 1.5), FXGL.getAppHeight() - (TITLE_FONT_SIZE / 4), version);
        text.setFill(Color.RED);
        text.setStyle("-fx-font-size: " + VERSION_FONT_SIZE + ";");

        return text;
    }

    /**
     * N/A
     * @param name N/A
     * @param action N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding name, @NotNull Runnable action) {
        return new Button(name.get());
    }

    /**
     * N/A
     * @param name N/A
     * @param action N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Button createActionButton(@NotNull String name, @NotNull Runnable action) {
        return new Button(name);
    }

    /**
     * N/A
     * @param profileName N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        return new Text(profileName);
    }
}
