package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Utils for usage in the main views.
 */
public class MainViewUtil {
    /**
     * Perform validation on a Node and then add it to the content root of this menu.
     * @param node Node to be added
     * @param x X-coordinate of the Node
     * @param y Y-coordinate of the Node
     * @return The node that was added to the menu
     */
    public static <T extends Node> T addNode(FXGLMenu menu, T node, double x, double y)
    {
        if (node != null)
        {
            double width = node.getLayoutBounds().getWidth();
            double height = node.getLayoutBounds().getHeight();

            if (!(x > FXGL.getAppWidth() - (width / 2) || y > FXGL.getAppHeight() - (height / 2)))
            {
                node.setLayoutX(x);
                node.setLayoutY(y);

                menu.getContentRoot().getChildren().add(node);

                return node;
            }
        }

        return null;
    }

    /**
     * Create a menu button for this menu, using the ActionButton class.
     * @param text The text of the menu button
     * @param action The action of the menu button
     * @return The menu button that was created
     */
    private static StackPane createMenuButton(String text, Runnable action)
    {
        return new ActionButton(text, action);
    }
}
