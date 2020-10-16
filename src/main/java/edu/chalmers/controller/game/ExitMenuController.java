package edu.chalmers.controller.game;

import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.InputController;
import edu.chalmers.controller.MenuController;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.game.ExitMenu;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;

/**
 * The controller for the exit menu view.
 */
public class ExitMenuController extends MenuController<ExitMenu> {
    private InputController inputController;

    /**
     * Default constructor for ExitMenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with.
     * @param mainInstance An instance of the Main class.
     */
    public ExitMenuController(ExitMenu viewInstance, Main mainInstance)
    {
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
                if (this.inputController != null)
                    this.inputController.setDoNotHandleEscape(true);
            }
        });

        getViewInstance().getExitButton().setOnMousePressed(mouseEvent -> {
            getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> { });
            this.hide();

            getMainInstance().stopGame();
        });
    }

    /**
     * Set the instance of InputController.
     * @param inputController An instance of the InputController.
     */
    public void setInputController(InputController inputController)
    {
        this.inputController = inputController;
    }

    /**
     * @return The instance of the InputController.
     */
    public InputController getInputController()
    {
        return this.inputController;
    }
}
