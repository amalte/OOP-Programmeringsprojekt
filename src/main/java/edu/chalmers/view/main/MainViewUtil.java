package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.Node;
import javafx.scene.control.Slider;
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
     * Create a menu button for this menu, using the ActionButton class.
     * @param text The text of the menu button
     * @param action The action of the menu button
     * @return The menu button that was created
     */
    public static StackPane createMenuButton(String text, Runnable action)
    {
        return new ActionButton(text, action);
    }


    /**
     * Create a slider with some preset values.
     * @param min The minimum value of this slider
     * @param max The maximum value of this slider
     * @param startValue The start value of this slider
     * @return The slider that was created
     */
    public static Slider createSlider(int min, int max, int startValue)
    {
        Slider slider = new Slider(min, max, startValue);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(25);

        return slider;
    }
}
