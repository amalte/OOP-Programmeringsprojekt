package edu.chalmers.controller.main;

import edu.chalmers.controller.MenuController;
import edu.chalmers.view.main.MainMenu;
import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * The controller for the main menu view.
 */
public class MainMenuController extends MenuController<MainMenu> {
    /**
     * Default constructor for MainMenuController.
     * @param mainMenu Instance of the MainMenu class to associate this controller with
     */
    public MainMenuController(MainMenu mainMenu)
    {
        super(mainMenu);
    }

    /**
     * Initialize the nodes (binds actions to them, etc.)
     */
    @Override
    protected void initializeNodes()
    {
        viewInstance.getPlayButton().setOnMousePressed(mouseEvent -> {
            getGameController().startNewGame();
        });

        viewInstance.getSettingsButton().setOnMousePressed(mouseEvent -> {
            // TODO: Make tihs open the settings menu
        });

        viewInstance.getExitButton().setOnMousePressed(mouseEvent -> {
            getGameController().exit();
        });
    }
}
