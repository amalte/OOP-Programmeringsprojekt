package edu.chalmers.controller;

import com.almasb.fxgl.input.Input;
import edu.chalmers.FXGLTest;
import edu.chalmers.controller.main.MainMenuController;
import edu.chalmers.controller.main.PlayMenuController;
import edu.chalmers.controller.main.SettingsMenuController;
import edu.chalmers.main.Main;
import edu.chalmers.view.main.MainMenu;
import edu.chalmers.view.main.PlayMenu;
import edu.chalmers.view.main.SettingsMenu;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Anwarr Shiervani
 *
 * Test class for all of the main controllers.
 */
public class TestMainControllers {
    private static Main mainInstance;

    /**
     * Set up the test class.
     * @throws InterruptedException
     */
    @BeforeClass
    public static void setUp() throws InterruptedException {
        initialize();
        mainInstance = FXGLTest.getMainInstance();
    }

    /**
     * Test the MainMenuController class.
     * @throws InterruptedException
     */
    @Test
    public void testMainMenuController() throws InterruptedException {
        /**
         * Start retrieve controller
         */
        MenuController menuController = mainInstance.getController(GameMenuType.Main);
        assertNotNull(menuController);
        assertTrue(menuController instanceof MainMenuController);
        MainMenuController mainMenuController = (MainMenuController)menuController;
        /**
         * End retrieve controller
         */

        /**
         * Start visibility test
         */
        waitForRunLater(mainMenuController::hide);
        assertFalse(mainMenuController.isVisible());
        waitForRunLater(mainMenuController::show);
        assertTrue(mainMenuController.isVisible());
        /**
         * End visibility test
         */

        /**
         * Start superclass test
         */
        assertEquals(mainInstance, mainMenuController.getMainInstance());
        assertTrue(mainMenuController.getViewInstance() instanceof MainMenu);
        assertEquals(GameMenuType.Main, mainMenuController.getGameMenuType());
        /**
         * End superclass test
         */

        /**
         * Start controller set/get
         */
        PlayMenuController playMenuController = (PlayMenuController) mainInstance.getController(GameMenuType.Play);
        mainMenuController.setPlayMenuController(null);
        assertNull(mainMenuController.getPlayMenuController());
        mainMenuController.setPlayMenuController(playMenuController);
        assertEquals(playMenuController, mainMenuController.getPlayMenuController());

        SettingsMenuController settingsMenuController = (SettingsMenuController) mainInstance.getController(GameMenuType.Settings);
        mainMenuController.setSettingsMenuController(null);
        assertNull(mainMenuController.getSettingsMenuController());
        mainMenuController.setSettingsMenuController(settingsMenuController);
        assertEquals(settingsMenuController, mainMenuController.getSettingsMenuController());
        /**
         * End controller set/get
         */

        /**
         * Start actions
         */
        MainMenu mainMenu = mainMenuController.getViewInstance();

        assertNotNull(mainMenu.getPlayButton().getOnMousePressed());
        assertNotNull(mainMenu.getSettingsButton().getOnMousePressed());
        assertNotNull(mainMenu.getExitButton().getOnMousePressed());

        // Play
        waitForRunLater(() -> mainMenu.getPlayButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertTrue(playMenuController.isVisible());

        // Settings
        waitForRunLater(() -> mainMenu.getSettingsButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertTrue(settingsMenuController.isVisible());
        waitForRunLater(() -> settingsMenuController.hide());

        // Exit
        CountDownLatch gameRunningLatch = new CountDownLatch(1);
        mainInstance.setGameRunningLatch(gameRunningLatch);
        waitForRunLater(() -> mainInstance.startGame(1));
// ERROR assertTrue(gameRunningLatch.await(AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS));

        waitForRunLater(() -> mainMenu.getExitButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertFalse(mainInstance.getGameRunning());
        /**
         * End actions
         */
    }

    /**
     * Test the PlayMenuController class.
     * @throws InterruptedException
     */
    @Test
    public void testPlayMenuController() throws InterruptedException {
        /**
         * Start retrieve controller
         */
        MenuController menuController = mainInstance.getController(GameMenuType.Play);
        assertNotNull(menuController);
        assertTrue(menuController instanceof PlayMenuController);
        PlayMenuController playMenuController = (PlayMenuController)menuController;
        /**
         * End retrieve controller
         */

        /**
         * Start visibility test
         */
        waitForRunLater(playMenuController::hide);
        assertFalse(playMenuController.isVisible());
        waitForRunLater(playMenuController::show);
        assertTrue(playMenuController.isVisible());
        /**
         * End visibility test
         */

        /**
         * Start superclass test
         */
        assertEquals(mainInstance, playMenuController.getMainInstance());
        assertTrue(playMenuController.getViewInstance() instanceof PlayMenu);
        assertEquals(GameMenuType.Play, playMenuController.getGameMenuType());
        /**
         * End superclass test
         */

        /**
         * Start controller set/get
         */
        MainMenuController mainMenuController = (MainMenuController) mainInstance.getController(GameMenuType.Main);
        playMenuController.setMainMenuController(null);
        assertNull(playMenuController.getMainMenuController());
        playMenuController.setMainMenuController(mainMenuController);
        assertEquals(mainMenuController, playMenuController.getMainMenuController());
        /**
         * End controller set/get
         */

        /**
         * Start actions
         */
        PlayMenu playMenu = playMenuController.getViewInstance();

        // Level 1
        assertNotNull(playMenu.getLevel1Button().getOnMousePressed());
        waitForRunLater(() -> playMenu.getLevel1Button().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertEquals("level1.tmx", mainInstance.getCurrentLevel());

        // Level 2
        assertNotNull(playMenu.getLevel2Button().getOnMousePressed());
        waitForRunLater(() -> playMenu.getLevel2Button().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertEquals("level2.tmx", mainInstance.getCurrentLevel());

        // Level 3
        assertNotNull(playMenu.getLevel3Button().getOnMousePressed());
        waitForRunLater(() -> playMenu.getLevel3Button().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        assertEquals("level3.tmx", mainInstance.getCurrentLevel());

        mainInstance.stopGame();
        assertNotNull(getGameScene().getRoot().getScene().getOnKeyPressed());

        // Escape
        waitForRunLater(() -> playMenuController.show());
        waitForRunLater(() -> getGameScene().getRoot().getScene().getOnKeyPressed().handle(new KeyEvent(new EventType<>("ESC-0001"), "", "", KeyCode.ESCAPE, false, false, false, false)));
        assertFalse(playMenuController.isVisible());
        assertTrue(mainMenuController.isVisible());
        /**
         * End actions
         */
    }

    /**
     * Test the SettingsMenuController class.
     * @throws InterruptedException
     */
    @Test
    public void testSettingsMenuController() throws InterruptedException {
        /**
         * Start retrieve controller
         */
        MenuController menuController = mainInstance.getController(GameMenuType.Settings);
        assertNotNull(menuController);
        assertTrue(menuController instanceof SettingsMenuController);
        SettingsMenuController settingsMenuController = (SettingsMenuController) menuController;
        /**
         * End retrieve controller
         */

        /**
         * Start visibility test
         */
        waitForRunLater(settingsMenuController::hide);
        assertFalse(settingsMenuController.isVisible());
        waitForRunLater(settingsMenuController::show);
        assertTrue(settingsMenuController.isVisible());
        /**
         * End visibility test
         */

        /**
         * Start specific prerequisite
         */
        assertNotNull(InputController.getInputInstance());

        Input input = InputController.getInputInstance();
        /**
         * End specific prerequisite
         */

        /**
         * Start superclass test
         */
        assertEquals(mainInstance, settingsMenuController.getMainInstance());
        assertTrue(settingsMenuController.getViewInstance() instanceof SettingsMenu);
        assertEquals(GameMenuType.Settings, settingsMenuController.getGameMenuType());
        /**
         * End superclass test
         */

        /**
         * Start controller set/get
         */
        MainMenuController mainMenuController = (MainMenuController) mainInstance.getController(GameMenuType.Main);
        settingsMenuController.setMainMenuController(null);
        assertNull(settingsMenuController.getMainMenuController());
        settingsMenuController.setMainMenuController(mainMenuController);
        assertEquals(mainMenuController, settingsMenuController.getMainMenuController());
        /**
         * End controller set/get
         */

        /**
         * Start actions
         */
        SettingsMenu settingsMenu = settingsMenuController.getViewInstance();
        EventHandler<? super KeyEvent> keyEventHandler = getGameScene().getRoot().getScene().getOnKeyPressed();

        assertNotNull(keyEventHandler);

        // Jump
        assertNotNull(settingsMenu.getControlJumpButton().getOnMousePressed());
        waitForRunLater(() -> settingsMenu.getControlJumpButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlJumpButton(), settingsMenu.getControlJumpButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("GAME_A-0001"), "", "", KeyCode.GAME_A, false, false, false, false));
        assertEquals("Game A", input.getTriggerName("Jump"));

        // Walk left
        assertNotNull(settingsMenu.getControlWalkLeftButton().getOnMousePressed());
        waitForRunLater(() -> settingsMenu.getControlWalkLeftButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlWalkLeftButton(), settingsMenu.getControlWalkLeftButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("COMPOSE-0001"), "", "", KeyCode.COMPOSE, false, false, false, false));
        assertEquals("Compose", input.getTriggerName("Walk left"));

        // Walk right
        assertNotNull(settingsMenu.getControlWalkRightButton().getOnMousePressed());
        waitForRunLater(() -> settingsMenu.getControlWalkRightButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlWalkRightButton(), settingsMenu.getControlWalkRightButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("CLEAR-0001"), "", "", KeyCode.CLEAR, false, false, false, false));
        assertEquals("Clear", input.getTriggerName("Walk right"));

        // Reload
        assertNotNull(settingsMenu.getControlReloadButton().getOnMousePressed());
        waitForRunLater(() -> settingsMenu.getControlReloadButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlReloadButton(), settingsMenu.getControlReloadButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("HELP-0001"), "", "", KeyCode.HELP, false, false, false, false));
        assertEquals("Help", input.getTriggerName("Reload"));

        // Weapon 1
        assertNotNull(settingsMenu.getControlFirstWeaponButton().getOnMousePressed());
        waitForRunLater(() -> settingsMenu.getControlFirstWeaponButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlFirstWeaponButton(), settingsMenu.getControlFirstWeaponButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("HIRAGANA-0001"), "", "", KeyCode.HIRAGANA, false, false, false, false));
        assertEquals("Hiragana", input.getTriggerName("SwitchToFirstWeapon"));

        // Weapon 2
        assertNotNull(settingsMenu.getControlSecondWeaponButton().getOnMousePressed());
        waitForRunLater(() -> settingsMenu.getControlSecondWeaponButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlSecondWeaponButton(), settingsMenu.getControlSecondWeaponButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("META-0001"), "", "", KeyCode.META, false, false, false, false));
        assertEquals("Meta", input.getTriggerName("SwitchToSecondWeapon"));

        // Weapon 3
        assertNotNull(settingsMenu.getControlThirdWeaponButton().getOnMousePressed());
        waitForRunLater(() -> settingsMenu.getControlThirdWeaponButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlThirdWeaponButton(), settingsMenu.getControlThirdWeaponButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("KANA-0001"), "", "", KeyCode.KANA, false, false, false, false));
        assertEquals("Kana", input.getTriggerName("SwitchToThirdWeapon"));

        // Weapon 2 -> Weapon 3
        waitForRunLater(() -> settingsMenu.getControlSecondWeaponButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlSecondWeaponButton(), settingsMenu.getControlSecondWeaponButton(),
                MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                false, false, true, false, false, true, false, true,
                null)));
        keyEventHandler.handle(new KeyEvent(new EventType<>("KANA-0002"), "", "", KeyCode.KANA, false, false, false, false));
        assertNotEquals("Kana", input.getTriggerName("SwitchToSecondWeapon"));
        settingsMenuController.restoreActivatedButton();

        // Escape
        waitForRunLater(() -> settingsMenu.getControlThirdWeaponButton().getOnMousePressed().handle(new MouseEvent(settingsMenu.getControlThirdWeaponButton(), settingsMenu.getControlThirdWeaponButton(),
                    MouseEvent.MOUSE_CLICKED,0,0, 0, 0, MouseButton.PRIMARY, 1, false, false,
                    false, false, true, false, false, true, false, true,
                    null)));
        waitForRunLater(() -> getGameScene().getRoot().getScene().getOnKeyPressed().handle(new KeyEvent(new EventType<>("ESC-0002"), "", "", KeyCode.ESCAPE, false, false, false, false)));
        assertFalse(settingsMenuController.isVisible());
        assertTrue(mainMenuController.isVisible());

        // Back button
        assertNotNull(settingsMenu.getBackButton().getOnMousePressed());
        waitForRunLater(() -> {
            settingsMenuController.show();
            settingsMenu.getBackButton().getOnMousePressed().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                    0,0, 0, 0, MouseButton.PRIMARY, 1, false, false, false,
                    false, true, false, false, true, false, true, null));
            getGameScene().getRoot().getScene().getOnKeyPressed().handle(new KeyEvent(new EventType<>("ESC-0003"), "", "", KeyCode.ESCAPE, false, false, false, false));
        });
        assertFalse(settingsMenuController.isVisible());
        assertTrue(mainMenuController.isVisible());

        /**
         * End actions
         */
    }

    /**
     * Tear down the test class.
     * @throws InterruptedException
     */
    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
        mainInstance = null;
    }
}
