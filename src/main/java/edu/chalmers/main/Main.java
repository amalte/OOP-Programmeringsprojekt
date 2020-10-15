package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.InputController;
import edu.chalmers.controller.MenuController;
import edu.chalmers.controller.game.ExitMenuController;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.controller.main.PlayMenuController;
import edu.chalmers.controller.main.SettingsMenuController;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.utilities.Constants;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.view.game.BuildView;
import edu.chalmers.view.game.GameUI;
import edu.chalmers.view.game.ExitMenu;
import edu.chalmers.view.main.MainMenu;
import edu.chalmers.view.main.PlayMenu;
import edu.chalmers.view.main.SettingsMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * The entrypoint for this game.
 */
public class Main extends GameApplication {
    private Boolean controllersInitialized = false;
    private List<MenuController> controllerList = new ArrayList<>();
    private AnchorPane backgroundPane;
    private GenericPlatformer game;
    private InputController inputController;
    private BuildView buildView;
    private GameUI gameUI;
    private Boolean gameRunning = false;

    /**
     * Main method. Called when running the program.
     * @param args Arguments to be passed onto FXGL.
     */
    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false"); // DO NOT REMOVE. Caps FPS at 60 across all computers
        launch(args);
    }

    /**
     * Set good defaults for our game.
     * @param gameSettings A parameter to be specified by FXGL itself. Contains a reference to the an instance of the GameSettings class.
     */
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setPreserveResizeRatio(true);
        gameSettings.setManualResizeEnabled(false);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setFullScreenFromStart(false);
        gameSettings.setWidth(Constants.GAME_WIDTH);
        gameSettings.setHeight(Constants.GAME_HEIGHT);
        gameSettings.setTitle("Generic Platformer");
        gameSettings.setVersion("1.0");
        gameSettings.setMenuKey(KeyCode.PAUSE);
    }

    /**
     * Initialize our game. Player input, loading levels, etc.
     */
    @Override
    protected void initGame() {
        game = new GenericPlatformer();
        inputController = new InputController(game, this);

        game.initializeGame("level2.tmx");
        inputController.initPlayerInput();

        this.createBackground();
        this.showBackground();
    }

    /**
     * Initialize the UI of our game.
     */
    @Override
    protected void initUI() {
        runOnce(() -> {
            if (!this.controllersInitialized)
            {
                controllerList.add(new MainMenuController(new MainMenu(), this));
                controllerList.add(new SettingsMenuController(new SettingsMenu(), this));
                controllerList.add(new PlayMenuController(new PlayMenu(), this));
                controllerList.add(new ExitMenuController(new ExitMenu(), this));

                this.controllersInitialized = true;
            }

            if (!this.isGameRunning())
            {
                getController(GameMenuType.Main).show();
            }
        }, Duration.seconds(0.5));
    }

    private void createBackground()
    {
        if (this.backgroundPane == null)
        {
            this.backgroundPane = new AnchorPane();
            this.backgroundPane.setLayoutX(0);
            this.backgroundPane.setLayoutY(0);
            this.backgroundPane.setPrefSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            this.backgroundPane.setStyle("-fx-background-color: #000000;");
        }
    }

    private void showBackground()
    {
        if (!getGameScene().getUiNodes().contains(this.backgroundPane))
        {
            getGameScene().addUINode(this.backgroundPane);
        }
    }

    /**
     * Get a registered controller that has the same specified game menu type.
     * @param gameMenuType The game menu type to search for.
     * @return The controller with the game menu type. May be null, if not found.
     */
    public MenuController getController(GameMenuType gameMenuType)
    {
        for (MenuController menuController : controllerList)
        {
            if (menuController.getGameMenuType() == gameMenuType)
            {
                return menuController;
            }
        }

        return null;
    }

    /**
     * Start the game.
     * @param levelIndex What level index to use when loading the TMX file. Format: "level{levelIndex}.tmx"
     */
    public void startGame(int levelIndex)
    {
        if (!this.isGameRunning())
        {
            game.remove();
            game.initializeGame("level" + levelIndex + ".tmx");

            runOnce(() -> {
                getGameScene().clearUINodes();
                this.initExtraViews();

                this.gameRunning = true;
            }, Duration.seconds(0.5));
        }
    }

    /**
     * Stop the game, if it is running.
     */
    public void stopGame()
    {
        if (this.isGameRunning())
        {
            this.showBackground();
            getController(GameMenuType.Exit).hide();
            getController(GameMenuType.Main).show();
            this.gameRunning = false;
        }
    }

    /**
     * @return Whether or not the game is running.
     */
    public Boolean isGameRunning()
    {
        return this.gameRunning;
    }

    private void initExtraViews()
    {
        this.gameUI = new GameUI(game);
        this.gameUI.setNodes();
        game.getCollisionDetection().addObserver(gameUI);

        this.buildView = new BuildView();
        this.buildView.buildStateSelected();
        this.buildView.setUpTransparentTiles();
        this.buildView.stopFollowMouse();
        this.buildView.reachableTiles(game.getBuildManager().getEmptyReachableTiles(CoordsCalculations.posToTile(EntityPos.getPosition(game.getPlayer()))));
    }

    /**
     * @return The instance of the BuildView class associated with our Main class.
     */
    public BuildView getBuildView()
    {
        return this.buildView;
    }

    /**
     * @return The instance of the GameUI class associated with our Main class.
     */
    public GameUI getGameUI()
    {
        return this.gameUI;
    }

    /**
     * @return The instance of the InputController class associated with our Main class.
     */
    public InputController getInputController() { return this.inputController; }

    /**
     * @return Whether or not the controllers have been initialized yet.
     */
    public Boolean getControllersInitialized() { return this.controllersInitialized; }
}