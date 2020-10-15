package edu.chalmers.controller.main;

import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.MenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.MainMenu;
import edu.chalmers.view.main.SettingsMenu;

import static com.almasb.fxgl.dsl.FXGL.getGameController;

/**
 * The controller for the main menu view.
 */
public class MainMenuController extends MenuController<MainMenu> {
    private PlayMenuController playMenuController;
    private SettingsMenuController settingsMenuController;

    /**
     * Default constructor for MainMenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with.
     * @param mainInstance An instance of the Main class.
     */
    public MainMenuController(MainMenu viewInstance, Main mainInstance)
    {
        super(viewInstance, mainInstance, GameMenuType.Main);
    }

    /**
     * Initialize the nodes (make view create them, binds actions to them, etc.)
     */
    @Override
    protected void initializeNodes()
    {
        super.initializeNodes();

        getViewInstance().getPlayButton().setOnMousePressed(mouseEvent -> {
            this.hide();

            if (this.playMenuController != null)
                this.playMenuController.show();
        });

        getViewInstance().getSettingsButton().setOnMousePressed(mouseEvent -> {
            this.hide();

            if (this.settingsMenuController != null)
                this.settingsMenuController.show();
        });

        getViewInstance().getExitButton().setOnMousePressed(mouseEvent -> {
            getGameController().exit();
        });
    }

    /**
     * Set the instance of PlayMenuController.
     * @param playMenuController An instance of the PlayMenuController.
     */
    public void setPlayMenuController(PlayMenuController playMenuController)
    {
        this.playMenuController = playMenuController;
    }

    /**
     * @return The instance of the PlayMenuController.
     */
    public PlayMenuController getPlayMenuController()
    {
        return this.playMenuController;
    }

    /**
     * Set the instance of SettingsMenuController.
     * @param settingsMenuController An instance of the SettingsMenuController.
     */
    public void setSettingsMenuController(SettingsMenuController settingsMenuController)
    {
        this.settingsMenuController = settingsMenuController;
    }

    /**
     * @return The instance of the SettingsMenuController.
     */
    public SettingsMenuController getSettingsMenuController()
    {
        return this.settingsMenuController;
    }
}
