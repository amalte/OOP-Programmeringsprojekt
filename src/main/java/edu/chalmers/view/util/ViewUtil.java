package edu.chalmers.view.util;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.utilities.Constants;
import edu.chalmers.view.nodes.ActionButton;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Utility methods for use in views.
 */
public class ViewUtil {
    /**
     * Perform validation on a Node and then add it to the content root of this menu.
     * @param subScene The sub scene to add this node to
     * @param node Node to be added
     * @param x X-coordinate of the Node
     * @param y Y-coordinate of the Node
     * @return The node that was added to the menu.
     */
    public static <T extends Node> T addNode(SubScene subScene, T node, double x, double y)
    {
        if (node != null)
        {
            double width = node.getLayoutBounds().getWidth();
            double height = node.getLayoutBounds().getHeight();

            if (!(x > FXGL.getAppWidth() - (width / 2) || y > FXGL.getAppHeight() - (height / 2)))
            {
                node.setLayoutX(x);
                node.setLayoutY(y);

                subScene.getContentRoot().getChildren().add(node);

                return node;
            }
        }

        return null;
    }

    /**
     * Create an action button.
     * @param text The text of the action button
     * @param action The associated action for the action button
     * @return The action button that was created.
     */
    public static ActionButton createActionButton(String text, Runnable action)
    {
        return new ActionButton(text, action);
    }

    /**
     * Create an action button, with a background image.
     * @param text The text of the action button
     * @param action The associated action for the action button
     * @param backgroundPath The path to a background image.
     * @return The action button that was created
     */
    public static ActionButton createActionButton(String text, Runnable action, String backgroundPath)
    {
        return new ActionButton(text, action, backgroundPath);
    }

    /**
     * Get the background node for the application.
     * @return ImageView loaded with an image from /assets/background.png.
     */
    public static Node getBackgroundNode()
    {
        return new ImageView(new Image("/assets/background2.png", Constants.GAME_WIDTH, Constants.GAME_HEIGHT, false, false));
    }
}
