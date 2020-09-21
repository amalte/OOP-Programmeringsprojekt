package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class SettingsMenu extends FXGLMenu {
    /**
     * Default constructor.
     * @param parentMenu The parent of this menu. Expected to be an instance of the class MainMenu.
     */
    public SettingsMenu(FXGLMenu parentMenu) {
        super(MenuType.MAIN_MENU);

        this.createControls();
    }

    /**
     * Creates the default controls for the settings menu.
     */
    private void createControls()
    {

    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding stringBinding, @NotNull Runnable runnable) {
        return null;
    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull String s, @NotNull Runnable runnable) {
        return null;
    }

    @NotNull
    @Override
    protected Node createBackground(double v, double v1) {
        return null;
    }

    @NotNull
    @Override
    protected Node createProfileView(@NotNull String s) {
        return null;
    }

    @NotNull
    @Override
    protected Node createTitleView(@NotNull String s) {
        return null;
    }

    @NotNull
    @Override
    protected Node createVersionView(@NotNull String s) {
        return null;
    }
}
