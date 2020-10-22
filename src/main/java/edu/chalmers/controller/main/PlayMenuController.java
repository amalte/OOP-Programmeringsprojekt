package edu.chalmers.controller.main;

import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.MenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.PlayMenu;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;

/**
 * @author Anwarr Shiervani
 *
 * The controller for the play menu view.
 */
public class PlayMenuController extends MenuController<PlayMenu> {
    private MainMenuController mainMenuController;

    /**
     * Default constructor for PlayMenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with.
     * @param mainInstance An instance of the Main class.
     */
    public PlayMenuController(PlayMenu viewInstance, Main mainInstance)
    {
        super(viewInstance, mainInstance, GameMenuType.Play);
    }

    /**
     * Initialize the nodes (make view create them, binds actions to them, etc.)
     */
    @Override
    protected void initializeNodes() {
        super.initializeNodes();

        getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
            {
                getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent2 -> { });
                this.hide();

                if (this.mainMenuController != null)
                    this.mainMenuController.show();
            }
        });

        getViewInstance().getLevel1Button().setOnMousePressed(mouseEvent -> selectLevel(1));
        getViewInstance().getLevel2Button().setOnMousePressed(mouseEvent -> selectLevel(2));
        getViewInstance().getLevel3Button().setOnMousePressed(mouseEvent -> selectLevel(3));
    }

    private void selectLevel(int levelIndex)
    {
        getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> {});
        this.hide();
        getMainInstance().startGame(levelIndex);
    }

    /**
     * Set the instance of MainMenuController.
     * @param mainMenuController An instance of the MainMenuController.
     */
    public void setMainMenuController(MainMenuController mainMenuController)
    {
        this.mainMenuController = mainMenuController;
    }

    /**
     * @return The instance of the MainMenuController.
     */
    public MainMenuController getMainMenuController()
    {
        return this.mainMenuController;
    }
}
