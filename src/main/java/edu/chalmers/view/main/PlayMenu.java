package edu.chalmers.view.main;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import edu.chalmers.view.IMenu;
import edu.chalmers.view.nodes.ActionButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static edu.chalmers.view.util.ViewUtil.*;

/**
 * @author Anwarr Shiervani
 *
 * The play menu.
 */
public class PlayMenu extends SubScene implements IMenu {
    private Text titleText;
    private ActionButton level1Button;
    private ActionButton level2Button;
    private ActionButton level3Button;

    /**
     * Create the nodes for this menu.
     */
    @Override
    public void createNodes() {
        // Background
        addNode(this, getBackgroundNode(), 0, 0);

        // Title
        this.titleText = new Text(getTitle());
        this.titleText.setFill(Color.RED);
        this.titleText.setStyle("-fx-font-size: 64; -fx-font-weight: bold;");
        addNode(this, this.titleText,
                (FXGL.getAppWidth() / 2.0) - (getTitle().length() * 30) / 2.0,
                FXGL.getAppHeight() / 5.0);

        // Main buttons
        this.level1Button = addNode(this, createActionButton("Level 1", () -> { }, "/assets/levels/level1.png"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0) - (5.0 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0));
        this.level2Button = addNode(this, createActionButton("Level 2", () -> { }, "/assets/levels/level2.png"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0) + (0.0 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0));
        this.level3Button = addNode(this, createActionButton("Level 3", () -> { }, "/assets/levels/level3.png"),
                (FXGL.getAppWidth() / 2.0) - (ActionButton.BUTTON_WIDTH / 2.0) + (5 * (ActionButton.BUTTON_WIDTH / 4.0)),
                (FXGL.getAppHeight() / 2.5) - (ActionButton.BUTTON_HEIGHT / 2.0));
    }

    /**
     * @return The title of this view.
     */
    @Override
    public String getTitle() {
        return "Select a level";
    }

    /**
     * @return The level 1 button.
     */
    public ActionButton getLevel1Button()
    {
        return this.level1Button;
    }

    /**
     * @return The level 2 button.
     */
    public ActionButton getLevel2Button()
    {
        return this.level2Button;
    }

    /**
     * @return The level 3 button.
     */
    public ActionButton getLevel3Button()
    {
        return this.level3Button;
    }
}
