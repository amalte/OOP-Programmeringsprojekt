package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import static edu.chalmers.view.main.MainViewUtil.*;

/**
 * Settings menu for the game.
 */
public class SettingsMenu extends FXGLMenu {
    /**
     * Font size of setting descriptions.
     */
    private static final double SETTING_FONT_SIZE = 16;

    /**
     * The main volume slider.
     */
    private Slider mainVolumeSlider;

    /**
     * The music volume slider.
     */
    private Slider musicVolumeSlider;

    /**
     * The sound effects volume slider.
     */
    private Slider sfxVolumeSlider;

    /**
     * Default constructor.
     */
    public SettingsMenu() {
        super(MenuType.MAIN_MENU);

        this.createControls();
    }

    /**
     * Creates the default controls for the settings menu.
     */
    private void createControls()
    {
        /**
         * NOTE: The methods createSettingSlider & modifySetting should be moved as soon as possible to an appropriate controller.
         */

        /**
         * The main volume slider.
         * Expected action: Increase/decrease the main volume.
         */
        this.mainVolumeSlider = addNode(this, createSettingSlider(0, 100, 100, "mainVolume"),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (0 * (ActionButton.BUTTON_WIDTH / 4)));
    }

    /**
     * Create a slider and associate its value with a setting (by its name).
     * @param min The minimum value of this slider
     * @param max The maximum value of this slider
     * @param startValue The start value of this slider
     * @param settingName The setting name
     * @return The slider that was created
     */
    private Slider createSettingSlider(int min, int max, int startValue, String settingName)
    {
        Slider slider = new Slider(min, max, startValue);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(25);

        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue))
                modifySetting(settingName, newValue);
        });

        return slider;
    }

    /**
     * Modify the value of a setting.
     * @param settingName The name of the setting
     * @param newValue The new value of the setting
     */
    private void modifySetting(String settingName, Object newValue)
    {

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
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param name N/A
     * @param action N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding name, @NotNull Runnable action) {
        return MainMenu.getInstance().createActionButton(name, action);
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
        return MainMenu.getInstance().createActionButton(name, action);
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
        return MainMenu.getInstance().createBackground(width, height);
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param profileName N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        return MainMenu.getInstance().createProfileView(profileName);
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param title N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createTitleView(@NotNull String title) {
        return MainMenu.getInstance().createTitleView(title);
    }

    /**
     * N/A. Uses delegation to pass calls to this method to the equivalent method in the MainMenu class instead.
     * @param version N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createVersionView(@NotNull String version) {
        return MainMenu.getInstance().createVersionView(version);
    }
}
