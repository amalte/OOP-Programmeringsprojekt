package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

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
     * Default constructor.
     * Creates default controls.
     */
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        this.createControls();
    }

    /**
     * Creates the default buttons for the main menu.
     */
    private void createControls()
    {
        // Play button
        addNode(createMenuButton("Play", this::fireNewGame),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (0 * (ActionButton.BUTTON_WIDTH / 4)));

        // Settings button
        addNode(createMenuButton("Settings", () -> openSettings()),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (1 * (ActionButton.BUTTON_WIDTH / 4)));

        // Exit button
        addNode(createMenuButton("Exit", this::fireExit),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (2 * (ActionButton.BUTTON_WIDTH / 4)));
    }

    /**
     *
     * @param node
     * @param x
     * @param y
     */
    private void addNode(Node node, double x, double y)
    {
        if (node != null)
        {
            double width = node.getLayoutBounds().getWidth();
            double height = node.getLayoutBounds().getHeight();

            if (!(x > FXGL.getAppWidth() - (width / 2) || y > FXGL.getAppHeight() - (height / 2)))
            {
                node.setLayoutX(x);
                node.setLayoutY(y);

                getMenuContentRoot().getChildren().add(node);
            }
        }
    }

    /**
     *
     * @param name
     * @param action
     * @return
     */
    private StackPane createMenuButton(String name, Runnable action)
    {
        return new ActionButton(name, action);
    }

    /**
     * Open the settings menu.
     */
    private void openSettings()
    {

    }

    /**
     *
     * @param name
     * @param action
     * @return
     */
    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding name, @NotNull Runnable action) {
        return new Button(name.get());
    }

    /**
     *
     * @param name
     * @param action
     * @return
     */
    @NotNull
    @Override
    protected Button createActionButton(@NotNull String name, @NotNull Runnable action) {
        return new Button(name);
    }

    /**
     *
     * @param width
     * @param height
     * @return
     */
    @NotNull
    @Override
    protected Node createBackground(double width, double height) {
        return new ImageView("/assets/background.png");
    }

    /**
     *
     * @param profileName
     * @return
     */
    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        return new Text(profileName);
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
}
