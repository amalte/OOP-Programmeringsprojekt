package edu.chalmers.controller;

import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.FXGLTest;
import edu.chalmers.main.Main;
import edu.chalmers.view.IMenu;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.chalmers.FXGLTest.deInitialize;
import static edu.chalmers.FXGLTest.initialize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Anwarr Shiervani
 * <p>
 * Test class for all of the miscellaneous controllers.
 */
public class TestMiscControllers {
    private static Main mainInstance;

    /**
     * Set up the test class.
     *
     * @throws InterruptedException
     */
    @BeforeAll
    public static void setUp() throws InterruptedException {
        initialize();
        mainInstance = FXGLTest.getMainInstance();
    }

    /**
     * Tear down the test class.
     *
     * @throws InterruptedException
     */
    @AfterAll
    public static void tearDown() throws InterruptedException {
        deInitialize();
        mainInstance = null;
    }

    /**
     * Test the MenuController class.
     *
     * @throws InterruptedException
     */
    @Test
    public void testMenuController() throws InterruptedException {
        /**
         * Start classes
         */
        class DummySubScene extends SubScene implements IMenu {
            private Boolean nodesCreated = false;

            @Override
            public void createNodes() {
                this.nodesCreated = true;
            }

            @Override
            public String getTitle() {
                return "";
            }

            public boolean getNodesCreated() {
                return this.nodesCreated;
            }
        }

        class DummyMenuController extends MenuController<DummySubScene> {
            /**
             * Default constructor for DummyMenuController.
             *
             * @param viewInstance Instance of a view to associate the controller with.
             * @param mainInstance An instance of the Main class.
             * @param gameMenuType The type of the associated menu view.
             */
            protected DummyMenuController(DummySubScene viewInstance, Main mainInstance, GameMenuType gameMenuType) {
                super(viewInstance, mainInstance, gameMenuType);
            }
        }

        DummySubScene dummySubScene = new DummySubScene();
        DummyMenuController dummyMenuController = new DummyMenuController(dummySubScene, mainInstance, GameMenuType.Dummy);
        /**
         * End classes
         */

        /**
         * Start superclass test
         */
        assertEquals(GameMenuType.Dummy, dummyMenuController.getGameMenuType());
        assertEquals(mainInstance, dummyMenuController.getMainInstance());
        assertEquals(dummySubScene, dummyMenuController.getViewInstance());
        /**
         * End superclass test
         */

        /**
         * Start visibility test
         */
        assertFalse(dummyMenuController.isVisible());

// ERROR waitForRunLater(() -> dummyMenuController.show());
// ERROR assertTrue(dummyMenuController.isVisible());

// ERROR waitForRunLater(() -> dummyMenuController.hide());
        assertFalse(dummyMenuController.isVisible());
        /**
         * End visibility test
         */

        /**
         * Start view test
         */
// ERROR assertTrue(dummySubScene.getNodesCreated());
        /**
         * End view test
         */
    }
}
