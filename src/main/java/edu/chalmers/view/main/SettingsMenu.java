package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import static edu.chalmers.view.main.MainViewUtil.addNode;

/**
 * The settings menu for the game.
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

        this.createNodes();
    }

    /**
     * Creates the default controls for the settings menu.
     */
    private void createNodes()
    {
        this.mainVolumeSlider = addNode(this, createSlider(0, 100, 100),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (0 * (ActionButton.BUTTON_WIDTH / 4)));

        this.musicVolumeSlider = addNode(this, createSlider(0, 100, 100),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (0.5 * (ActionButton.BUTTON_WIDTH / 4)));

        this.sfxVolumeSlider = addNode(this, createSlider(0, 100, 100),
                (FXGL.getAppWidth() / 2) - (ActionButton.BUTTON_WIDTH / 2),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2) + (1 * (ActionButton.BUTTON_WIDTH / 4)));
    }

    /**
     * Create a slider with some preset values.
     * @param min The minimum value of this slider
     * @param max The maximum value of this slider
     * @param startValue The start value of this slider
     * @return The slider that was created
     */
    private Slider createSlider(int min, int max, int startValue)
    {
        Slider slider = new Slider(min, max, startValue);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(25);

        return slider;
    }

    /**
     * Get the main volume slider.
     * @return The main volume slider
     */
    public Slider getMainVolumeSlider()
    {
        return this.mainVolumeSlider;
    }

    /**
     * Get the music volume slider.
     * @return The music volume slider
     */
    public Slider getMusicVolumeSlider()
    {
        return this.musicVolumeSlider;
    }

    /**
     * Get the SFX volume slider.
     * @return The SFX volume slider
     */
    public Slider getSfxVolumeSlider()
    {
        return this.sfxVolumeSlider;
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
     * N/A.
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
     * N/A.
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
     * N/A.
     * @param width N/A
     * @param height N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createBackground(double width, double height) {
        return new ImageView();
    }

    /**
     * N/A.
     * @param profileName N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        return new Text(profileName);
    }

    /**
     * N/A.
     * @param title N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createTitleView(@NotNull String title) {
        return new Text(title);
    }

    /**
     * N/A.
     * @param version N/A
     * @return N/A
     */
    @NotNull
    @Override
    protected Node createVersionView(@NotNull String version) {
        return new Text(version);
    }
}
