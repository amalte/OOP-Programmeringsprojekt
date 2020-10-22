package edu.chalmers.view;

/**
 * @author Anwarr Shiervani
 * <p>
 * Interface to be inherited by the menu views.
 */
public interface IMenu {
    /**
     * Create the nods for this menu.
     */
    void createNodes();

    /**
     * @return The title of the menu.
     */
    String getTitle();
}
