package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import edu.chalmers.controller.BuildUIController;
import edu.chalmers.controller.GameMenuType;
import edu.chalmers.controller.InputController;
import edu.chalmers.controller.MenuController;
import edu.chalmers.controller.game.ExitMenuController;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.controller.main.PlayMenuController;
import edu.chalmers.controller.main.SettingsMenuController;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.utilities.Constants;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * The entrypoint for this game.
 */
public class Main extends GameApplication {
    private static AtomicReference<CountDownLatch> initializedLatch = new AtomicReference<>();

    private List<MenuController> controllerList = new ArrayList<>();
    private AnchorPane backgroundPane;
    private GenericPlatformer game;
    private BuildUIController buildUIController;
    private InputController inputController;
    private BuildView buildView;
    private GameUI gameUI;
    private Boolean gameRunning = false;
    private Boolean gameShutdown = false;

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
        this.gameRunning = false;
        this.gameShutdown = false;

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
            synchronized (this.controllerList)
            {
                if (this.controllerList.isEmpty())
                {
                    MainMenuController mainMenuController = new MainMenuController(new MainMenu(), this);

                    SettingsMenuController settingsMenuController = new SettingsMenuController(new SettingsMenu(), this);
                    settingsMenuController.setMainMenuController(mainMenuController);

                    PlayMenuController playMenuController = new PlayMenuController(new PlayMenu(), this);
                    playMenuController.setMainMenuController(mainMenuController);

                    mainMenuController.setPlayMenuController(playMenuController);
                    mainMenuController.setSettingsMenuController(settingsMenuController);

                    ExitMenuController exitMenuController = new ExitMenuController(new ExitMenu(), this);
                    exitMenuController.setInputController(inputController);

                    this.controllerList.add(mainMenuController);
                    this.controllerList.add(settingsMenuController);
                    this.controllerList.add(playMenuController);
                    this.controllerList.add(exitMenuController);

                    mainMenuController.show();
                }
            }

            if (getInitializedLatch() != null && getInitializedLatch().getCount() > 0)
                getInitializedLatch().countDown();
        }, Duration.seconds(0.5));
    }

    /**
     * Runs update method that runs every tick
     * @param tpf tpf
     */
    @Override
    protected void onUpdate(double tpf) {
        if(buildUIController != null)
        buildUIController.updateBuildTileUI();   // Constantly update the build UI overlay
    }

    /**
     * Shuts the game down.
     */
    public void shutdown() {
        this.gameRunning = false;
        this.gameShutdown = true;

        getGameController().exit();
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
        if (!this.getGameRunning())
        {
            game.remove();
            game.initializeGame("level" + levelIndex + ".tmx");

            runOnce(() -> {
                getGameScene().clearUINodes();
                this.initExtraViews();

                buildUIController = new BuildUIController(game, buildView);

                this.gameRunning = true;
            }, Duration.seconds(0.5));
        }
    }

    /**
     * Stop the game, if it is running.
     */
    public void stopGame()
    {
        if (this.getGameRunning())
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
    public Boolean getGameRunning()
    {
        return this.gameRunning;
    }

    private void initExtraViews()
    {
        this.gameUI = new GameUI(game);
        this.gameUI.setNodes();
        game.getPlayerComponent().addObserver(gameUI);
        //game.getWaveManager().addObserver(gameUI);

        this.buildView = new BuildView(game.getPlayerComponent().getBuildRangeTiles());
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
     * @return Whether or not the game has been shutdown through the shutdown() method.
     */
    public Boolean getGameShutdown() { return this.gameShutdown; }

    /**
     * Set the initializedLatch for Main.
     * @param countDownLatch The instance of CountDownLatch to set initializedLatch to. This latch will be counted down, if its count is over 0, once that the initUI method has been ran.
     */
    public static void setInitializedLatch(CountDownLatch countDownLatch) { initializedLatch.set(countDownLatch); }

    /**
     * @return The initializedLatch for Main.
     */
    public static CountDownLatch getInitializedLatch() { return initializedLatch.get(); }
}