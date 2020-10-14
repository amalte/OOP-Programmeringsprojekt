package edu.chalmers.controller.game;

import com.almasb.fxgl.core.Disposable;
import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.InputController;
import edu.chalmers.controller.MenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.game.ExitMenu;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;

/**
 * The controller for the exit menu view.
 */
public class ExitMenuController extends MenuController<ExitMenu> {

    public Boolean doNotHandleEscape = false;

    /**
     * Default constructor for ExitMenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with. Class has to implement IMenu.
     * @param mainInstance
     */
    public ExitMenuController(ExitMenu viewInstance, Main mainInstance) {
        super(viewInstance, mainInstance, GameMenuType.Exit);
    }

    /**
     * Initialize the nodes (make view create them, binds actions to them, etc.)
     */
    @Override
    protected void initializeNodes()
    {
        super.initializeNodes();

        getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
            {
                getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent2 -> { });
                this.hide();

                // Workaround for InputController handling the key event
                doNotHandleEscape = true;
            }
        });

        this.viewInstance.getExitButton().setOnMousePressed(mouseEvent -> {
            getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> { });
            this.mainInstance.stopGame();
        });
    }
}
