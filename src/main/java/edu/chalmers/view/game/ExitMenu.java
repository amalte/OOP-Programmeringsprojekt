package edu.chalmers.view.game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.utilities.Constants;
import edu.chalmers.view.IMenu;
import edu.chalmers.view.nodes.ActionButton;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static edu.chalmers.view.util.ViewUtil.*;

public class ExitMenu extends SubScene implements IMenu {

    private Text titleText;

    private Node exitButton;

    private AnchorPane backgroundPane;

    /**
     * Create the nodes for this menu.
     */
    @Override
    public void createNodes()
    {
        // Background
        this.backgroundPane = new AnchorPane();
        this.backgroundPane.setLayoutX(0);
        this.backgroundPane.setLayoutY(0);
        this.backgroundPane.setPrefSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        this.backgroundPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        addNode(this, this.backgroundPane, 0, 0);

        // Title
        this.titleText = new Text(getTitle());
        this.titleText.setFill(Color.RED);
        this.titleText.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");
        addNode(this, this.titleText,
                (FXGL.getAppWidth() / 2.0) - (getTitle().length() * 28.5) / 2.0,
                FXGL.getAppHeight() / 5.0);

        // Main buttons
        this.exitButton = addNode(this, createActionButton("Exit to main menu", () -> { }),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0) + (0.0 * (ActionButton.BUTTON_HEIGHT / 4.0)));
    }

    @Override
    public String getTitle() {
        return "Game Paused";
    }

    public Node getExitButton()
    {
        return this.exitButton;
    }
}
