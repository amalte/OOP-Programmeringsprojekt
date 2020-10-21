package edu.chalmers.controller;

import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.main.Main;
import edu.chalmers.view.IMenu;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * @author Anwarr Shiervani
 *
 * Class to be inherited from in menu controllers. Contains basic methods to simplify creating controllers for views extending FXGLMenu.
 * @param <T> Any class that extends SubScene. Class has to implement IMenu for nodes to be created.
 */
public class MenuController<T extends SubScene> {
    private Main mainInstance;
    private T viewInstance;
    private GameMenuType gameMenuType;
    private Boolean nodesCreated = false;
    private volatile Boolean visible = false;

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
     * @return The main instance associated with this controller.
     */
    public Main getMainInstance() { return this.mainInstance; }

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
        if (!this.isVisible())
        {
            this.initializeNodes();

            getSceneService().popSubScene();
            getSceneService().pushSubScene(viewInstance);

            this.visible = true;
        }
    }

    /**
     * Hide the view.
     */
    public void hide() {
        if (this.isVisible())
        {
            getSceneService().popSubScene();

            this.visible = false;
        }
    }

    /**
     * @return Whether or not the view, associated with this controller, is visible.
     */
    public final Boolean isVisible()
    {
        return this.visible;
    }
}
