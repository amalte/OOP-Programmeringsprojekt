package edu.chalmers.controller.main;

import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.MenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.MainMenu;

import static com.almasb.fxgl.dsl.FXGL.getGameController;

/**
 * The controller for the main menu view.
 */
public class MainMenuController extends MenuController<MainMenu> {
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

        viewInstance.getPlayButton().setOnMousePressed(mouseEvent -> {
            this.hide();
            this.mainInstance.getController(GameMenuType.Play).show();
        });

        viewInstance.getSettingsButton().setOnMousePressed(mouseEvent -> {
            this.hide();
            this.mainInstance.getController(GameMenuType.Settings).show();
        });

        viewInstance.getExitButton().setOnMousePressed(mouseEvent -> {
            getGameController().exit();
        });
    }
}
