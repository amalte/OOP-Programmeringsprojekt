package edu.chalmers.view.main;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

class ActionButton extends StackPane {
    public static final double BUTTON_WIDTH = 240;

    public static final double BUTTON_HEIGHT = 48;

    public static final double BUTTON_FONT_SIZE = 20;

    public ActionButton(String buttonText, Runnable action) {
        this.createControls(buttonText);
        this.setOnMouseClicked(mouseEvent -> action.run());
        this.setPrefWidth(BUTTON_WIDTH);
        this.setPrefHeight(BUTTON_HEIGHT);
    }

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