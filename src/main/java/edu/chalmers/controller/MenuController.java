package edu.chalmers.controller;

import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.main.Main;
import edu.chalmers.view.IMenu;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * Class to be inherited from in menu controllers. Contains basic methods to simplify creating controllers for views extending FXGLMenu.
 * @param <T> Any class that extends SubScene. Class has to implement IMenu for nodes to be created.
 */
public class MenuController<T extends SubScene> {
    /**
     * The instance of the SubScene class that the controller is associated with.
     */
    protected T viewInstance;

    /**
     * The instance of the Main class that the controller is associated with.
     */
    protected Main mainInstance;

    private GameMenuType gameMenuType;
    private Boolean nodesCreated = false;
    private volatile Boolean visible = false;

    /**
     * Counted down to 0 when the menu is displayed.
     * Reset to 1 when the menu is hidden.
     * AtomicReference so that different processor cores do not have different instances of the CountDownLatch.
     */
    private final AtomicReference<CountDownLatch> visibleLatch = new AtomicReference<CountDownLatch>();

    /**
     * Default constructor for MenuController.
     *
     * @param viewInstance Instance of a view to associate the controller with.
     * @param mainInstance An instance of the Main class.
     * @param gameMenuType The type of the associated menu view.
     */
    protected MenuController(T viewInstance, Main mainInstance, GameMenuType gameMenuType)
    {
        this.viewInstance = viewInstance;
        this.mainInstance = mainInstance;
        this.gameMenuType = gameMenuType;

        this.visibleLatch.set(new CountDownLatch(1));
    }

    /**
     * Initialize the nodes (make view create them, binds actions to them, etc.)
     */
    protected void initializeNodes()
    {
        if (this.viewInstance instanceof IMenu && !nodesCreated)
        {
            ((IMenu)this.viewInstance).createNodes();
            nodesCreated = true;
        }
    }

    /**
     * @return The instance of the view.
     */
    public final T getViewInstance()
    {
        return this.viewInstance;
    }

    /**
     * @return The menu type of the view.
     */
    public final GameMenuType getGameMenuType() {
        return this.gameMenuType;
    }

    /**
     * Show the view.
     */
    public void show() {
        this.initializeNodes();

        getSceneService().popSubScene();
        getSceneService().pushSubScene(viewInstance);

        this.visible = true;
        this.visibleLatch.get().countDown();
    }

    /**
     * Hide the view.
     */
    public void hide() {
        getSceneService().popSubScene();

        this.visible = false;
        this.visibleLatch.set(new CountDownLatch(1));
    }

    /**
     * @return Whether or not the view, associated with this controller, is visible.
     */
    public final Boolean isVisible()
    {
        return this.visible;
    }

    /**
     * @return The CountDownLatch for the visibility of the view associated with this controller.
     */
    public final CountDownLatch getVisibleLatch()
    {
        return this.visibleLatch.get();
    }
}
