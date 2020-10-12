package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import static edu.chalmers.view.main.MainViewUtil.*;

/**
 * The settings menu for the game.
 */
public class SettingsMenu extends SubScene {
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
        this.createNodes();
    }

    /**
     * Creates the default controls for the settings menu.
     */
    private void createNodes()
    {
        // Sound sliders
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
}
