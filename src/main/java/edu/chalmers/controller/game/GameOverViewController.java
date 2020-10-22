package edu.chalmers.controller.game;

import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.MenuController;
import edu.chalmers.main.Main;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.IObserver;
import edu.chalmers.view.game.GameOverView;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;

/**
 * @author Oscar Arvidson
 * <p>
 * The controller for the exit menu view.
 */
public class GameOverViewController extends MenuController<GameOverView> implements IObserver {

    private GenericPlatformer game;

    /**
     * Default constructor for ExitMenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with.
     * @param mainInstance An instance of the Main class.
     */
    public GameOverViewController(GameOverView viewInstance, Main mainInstance, GenericPlatformer game) {
        super(viewInstance, mainInstance, GameMenuType.GameOver);
        this.game = game;
    }

    /**
     * Initialize the nodes (make view create them, binds actions to them, etc.)
     */
    @Override
    protected void initializeNodes() {
        super.initializeNodes();

        getViewInstance().getExitButton().setOnMousePressed(mouseEvent -> {
            getGameScene().getRoot().getScene().setOnKeyPressed(keyEvent -> {
            });
            this.hide();

            getMainInstance().stopGame();
        });
    }

    @Override
    public void update() {
        if (game.getPlayerComponent().getHealth() <= 0) {
            this.show();
        }
    }
}
