package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

/**
 * Settings menu for the game.
 */
public class SettingsMenu extends FXGLMenu {
    /**
     * Font size of setting descriptions.
     */
    private static final double SETTING_FONT_SIZE = 16;

    /**
     * Parent main menu of this menu.
     */
    private static MainMenu parentMainMenu;

    /**
     * Default constructor.
     * @param parentMainMenu The parent, main menu of this menu.
     */
    public SettingsMenu(MainMenu parentMainMenu) {
        super(MenuType.MAIN_MENU);

        this.parentMainMenu = parentMainMenu;
        this.createControls();
    }

    /**
     * Creates the default controls for the settings menu.
     */
    private void createControls()
    {
        // Create controls for each setting.
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param name N/A
     * @param action N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding name, @NotNull Runnable action) {
        if (parentMainMenu != null)
            return parentMainMenu.createActionButton(name, action);
        else
            return null;
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param name N/A
     * @param action N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Button createActionButton(@NotNull String name, @NotNull Runnable action) {
        if (parentMainMenu != null)
            return parentMainMenu.createActionButton(name, action);
        else
            return null;
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param width N/A
     * @param height N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createBackground(double width, double height) {
        if (parentMainMenu != null)
            return parentMainMenu.createBackground(width, height);
        else
            return null;
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param profileName N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        if (parentMainMenu != null)
            return parentMainMenu.createProfileView(profileName);
        else
            return null;
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param title N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createTitleView(@NotNull String title) {
        if (parentMainMenu != null)
            return parentMainMenu.createTitleView(title);
        else
            return null;
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param version N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createVersionView(@NotNull String version) {
        if (parentMainMenu != null)
            return parentMainMenu.createVersionView(version);
        else
            return null;
    }
}
