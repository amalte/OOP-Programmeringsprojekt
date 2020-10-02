package edu.chalmers.controller;

import com.almasb.fxgl.app.scene.FXGLMenu;

/**
 * Abstract class for use in menu controllers. Contains basic methods to simplify creating controllers for views extending FXGLMenu.
 * @param <T> Any class that extends FXGLMenu
 */
public abstract class MenuController<T extends FXGLMenu> {
    /**
     * The instance of a View that the controller is associated with.
     */
    protected T viewInstance;

    /**
     * Default constructor for MenuController.
     * @param viewInstance Instance of a view to associate the controller with.
     */
    protected MenuController(T viewInstance)
    {
        this.viewInstance = viewInstance;
        this.initializeNodes();
    }

    /**
     * Initialize the nodes (binds actions to them, etc.)
     */
    protected abstract void initializeNodes();

    /**
     * Get the instance of the view associated with this class.
     * @return The instance of the view
     */
    public T getViewInstance()
    {
        return this.viewInstance;
    }
}
