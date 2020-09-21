package edu.chalmers.view.main;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * A clickable StackPane, with a default background and text.
 */
class ActionButton extends StackPane {
    /**
     * The width of the action button.
     */
    public static final double BUTTON_WIDTH = 240;

    /**
     * The height of the action button.
     */
    public static final double BUTTON_HEIGHT = 48;

    /**
     * The font size of the text inside of the action button.
     */
    public static final double BUTTON_FONT_SIZE = 20;

    /**
     * Default constructor.
     * Creates controls for manipulating settings and binds the action to mouse clicks.
     * @param buttonText The text of the button
     * @param action The action of the button. It is ran when the button is clicked.
     */
    public ActionButton(String buttonText, Runnable action) {
        this.createControls(buttonText);
        this.setOnMouseClicked(mouseEvent -> action.run());
        this.setPrefWidth(BUTTON_WIDTH);
        this.setPrefHeight(BUTTON_HEIGHT);
    }

    /**
     * Creates a background and a text for the button.
     * @param buttonText The text of the button
     */
    private void createControls(String buttonText)
    {
        Rectangle background = new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT);
        background.setStroke(Color.GREEN);
        background.fillProperty().bind(
                Bindings.when(hoverProperty()).then(Color.LIGHTGREEN).otherwise(Color.GREEN)
        );
        getChildren().add(background);

        Text text = FXGL.getUIFactoryService().newText(buttonText, Color.BLACK, BUTTON_FONT_SIZE);
        getChildren().add(text);
    }
}