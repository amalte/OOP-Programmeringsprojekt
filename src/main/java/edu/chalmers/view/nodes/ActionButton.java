package edu.chalmers.view.nodes;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * @author Anwarr Shiervani
 * <p>
 * A clickable StackPane, with a default background and text.
 */
public class ActionButton extends StackPane {
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

    private String tag;
    private String backgroundPath;

    /**
     * Default constructor.
     * Creates controls for manipulating settings and binds the action to mouse clicks.
     *
     * @param buttonText The text of the button
     * @param action     The action of the button. It is ran when the button is clicked.
     */
    public ActionButton(String buttonText, Runnable action) {
        this.createControls(buttonText);
        this.setOnMouseClicked(mouseEvent -> action.run());
        this.setPrefWidth(BUTTON_WIDTH);
        this.setPrefHeight(BUTTON_HEIGHT);
    }

    /**
     * Second constructor.
     * Creates controls for manipulating settings and binds the action to mouse clicks.
     * Uses the specified image as a background instead of just a plain-colored background.
     *
     * @param buttonText     The text of the button
     * @param action         The action of the button. It is ran when the button is clicked.
     * @param backgroundPath The path to a background image.
     */
    public ActionButton(String buttonText, Runnable action, String backgroundPath) {
        this.backgroundPath = backgroundPath;
        this.createControls(buttonText);
        this.setOnMouseClicked(mouseEvent -> action.run());
        this.setPrefWidth(BUTTON_WIDTH);
        this.setPrefHeight(BUTTON_HEIGHT);
    }

    private void createControls(String buttonText) {
        if (this.backgroundPath != null && !this.backgroundPath.trim().isEmpty()) {
            ImageView backgroundImage = new ImageView(new Image(backgroundPath, BUTTON_WIDTH, BUTTON_HEIGHT, false, false));
            getChildren().add(backgroundImage);

            Text text = FXGL.getUIFactoryService().newText(buttonText, Color.BLACK, BUTTON_FONT_SIZE * 2.5);
            text.setStyle("-fx-font-weight: bold;");
            getChildren().add(text);
        } else {
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

    /**
     * @return The tag of this button.
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Set the tag of this button.
     *
     * @param tag The new tag.
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}