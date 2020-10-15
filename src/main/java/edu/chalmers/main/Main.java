package edu.chalmers.main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.input.Input;
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
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.view.BuildView;
import edu.chalmers.view.GameUI;
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

public class Main extends GameApplication {
    private Boolean controllersInitialized = false;
    private List<MenuController> controllerList = new ArrayList<>();
    private AnchorPane backgroundPane;

    private GenericPlatformer game;
    private InputController inputController;
    private BuildUIController buildUIController;

    private BuildView buildView;
    private GameUI gameUI;

    private Boolean gameRunning = false;

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false"); // DO NOT REMOVE. Caps FPS at 60 across all computers
        launch(args);
    }

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

    @Override
    protected void initGame() {
        game = new GenericPlatformer();
        inputController = new InputController(game, this);

        game.initializeGame("level2.tmx");

        this.initExtraViews();
        inputController.initPlayerMovementInput();
        buildUIController = new BuildUIController(game, buildView);

        this.createBackground();
        this.showBackground();
    }

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

    @Override
    protected void onUpdate(double tpf) {
        buildUIController.updateBuildTileUI();   // Constantly update the build UI overlay
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

    private void hideBackground()
    {
        if (getGameScene().getUiNodes().contains(this.backgroundPane))
        {
            getGameScene().removeUINode(this.backgroundPane);
        }
    }

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

    public void startGame(int levelIndex)
    {
        if (!this.isGameRunning())
        {
            game.remove();
            game.initializeGame("level" + levelIndex + ".tmx");

            runOnce(() -> {
                this.hideBackground();

                this.gameRunning = true;
            }, Duration.seconds(0.5));
        }
    }

    public void stopGame()
    {
        if (this.isGameRunning())
        {
            getController(GameMenuType.Exit).hide();
            getController(GameMenuType.Main).show();
            this.gameRunning = false;
        }
    }

    public Boolean isGameRunning()
    {
        return this.gameRunning;
    }

    private void initExtraViews()
    {
        this.gameUI = new GameUI(game);
        this.gameUI.setNodes();

        this.buildView = new BuildView(game.getPlayerComponent().getBuildRangeTiles());
    }

    public BuildView getBuildView()
    {
        return this.buildView;
    }

    public GameUI getGameUI()
    {
        return this.gameUI;
    }
}