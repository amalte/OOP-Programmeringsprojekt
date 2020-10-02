package edu.chalmers.controller.main;

import edu.chalmers.controller.MenuController;
import edu.chalmers.view.main.SettingsMenu;
import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * The controller for the settings menu.
 */
public class SettingsMenuController extends MenuController<SettingsMenu> {

    /**
     * Default constructor for SettingsMenuController.
     * @param settingsMenu Instance of the SettingsMenu class to associate this controller with
     */
    public SettingsMenuController(SettingsMenu settingsMenu)
    {
        super(settingsMenu);
    }

    /**
     * Initialize the nodes (binds actions to them, etc.)
     */
    @Override
    protected void initializeNodes() {
        viewInstance.getMainVolumeSlider().valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue))
                modifySetting("mainVolume", newValue);
        });

        viewInstance.getMusicVolumeSlider().valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue))
                modifySetting("musicVolume", newValue);
        });

        viewInstance.getSfxVolumeSlider().valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue))
                modifySetting("sfxVolume", newValue);
        });

        // TODO: Bind actions to controls that manipulate the input
    }

    /**
     * Modify the value of a setting.
     * @param settingName The name of the setting
     * @param newValue The new value of the setting
     */
    private void modifySetting(String settingName, Object newValue)
    {
        // TODO: Change input settings via getGameScene().getInput()

        // TODO: Look into how sound settings can be modified
    }
}
