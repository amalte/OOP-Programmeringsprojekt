package edu.chalmers.controller;

import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.main.Main;
import edu.chalmers.view.IMenu;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * Class to be inherited from in menu controllers. Contains basic methods to simplify creating controllers for views extending FXGLMenu.
 * @param <T> Any class that extends FXGLMenu
 */
public class MenuController<T extends SubScene> {
    /**
     * The instance of a View that the controller is associated with.
     */
    protected T viewInstance;

    protected Main mainInstance;

    /**
     * The type of the menu.
     */
    private GameMenuType gameMenuType;

    private Boolean nodesCreated = false;

    /**
     * Whether or not the menu is visible.
     */
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
     * @param mainInstance
     * @param gameMenuType
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
     * Get the instance of the view associated with this class.
     * @return The instance of the view
     */
    public T getViewInstance()
    {
        return this.viewInstance;
    }

    /**
     * Get the menu type of the view
     * @return The menu type of the view
     */
    public GameMenuType getGameMenuType() {
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

    public Boolean isVisible()
    {
        return this.visible;
    }

    public CountDownLatch getVisibleLatch()
    {
        return this.visibleLatch.get();
    }
}
