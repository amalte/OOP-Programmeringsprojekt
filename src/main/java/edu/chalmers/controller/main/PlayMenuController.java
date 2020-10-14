package edu.chalmers.controller.main;

import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.MenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.PlayMenu;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;

public class PlayMenuController extends MenuController<PlayMenu> {

    /**
     * Default constructor for PlayMenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with. Class has to implement IMenu.
     * @param mainInstance
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
                this.mainInstance.getController(GameMenuType.Main).show();
            }
        });

        viewInstance.getLevel1Button().setOnMousePressed(mouseEvent -> selectLevel(1));
        viewInstance.getLevel2Button().setOnMousePressed(mouseEvent -> selectLevel(2));
        viewInstance.getLevel3Button().setOnMousePressed(mouseEvent -> selectLevel(3));
    }

    private void selectLevel(int levelIndex)
    {
        getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> {});
        this.hide();
        this.mainInstance.startGame(levelIndex);
    }
}
