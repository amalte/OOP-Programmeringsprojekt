package edu.chalmers.view.game;

import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.services.Coords;
import edu.chalmers.utilities.CoordsCalculations;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Malte Åkvist
 * <p>
 * BuildView view that draws grid and placeable box for building UI
 */
public class BuildView {

    private List<Node> transparentRects = new ArrayList<>();
    private Rectangle mouseRect;

    public BuildView(int buildRange) {
        FXGL.getGameScene().addUINodes(createTransparentTiles(buildRange));
        FXGL.getGameScene().addUINodes(createMouseRect());
    }

    /**
     * Draws the tileOverlay (transparent boxes that shows where the build range is)
     */
    public void updateTileOverlay(List<Coords> reachableTiles) {
        for (Node node : transparentRects) {
            node.setVisible(false);     // Hide all since list will get updated
        }

        for (int i = 0; i < reachableTiles.size(); i++) {
            Point2D reachableTilePos = CoordsCalculations.tileToPos(reachableTiles.get(i));
            transparentRects.get(i).setTranslateX(reachableTilePos.getX());
            transparentRects.get(i).setTranslateY(reachableTilePos.getY());
            transparentRects.get(i).setVisible(true);   // Only the ones that should be seen are visible
        }
    }

    /**
     * Hides the build box UI
     */
    public void hideBuildUI() {
        mouseRect.setVisible(false);
    }

    /**
     * Shows the build box UI and draws a red or green box depending on if it's possible to build at the tile
     */
    public void showBuildUI(Point2D mousePos, boolean possibleToPlaceBlock) {
        mouseRect.setVisible(true);
        if (possibleToPlaceBlock) {
            mouseRect.setFill(Color.GREEN);
        } else {
            mouseRect.setFill(Color.RED);
        }
        Point2D followMousePos = CoordsCalculations.posToTilePos(mousePos);
        mouseRect.setTranslateX(followMousePos.getX());
        mouseRect.setTranslateY(followMousePos.getY());
    }

    private Node[] createTransparentTiles(int buildRangeTiles) {
        int tileWidth = buildRangeTiles * 2 + 1;
        int totalTiles = tileWidth * tileWidth;

        for (int i = 0; i < totalTiles; i++) {
            transparentRects.add(createTransparentRect());
        }
        return transparentRects.toArray(new Node[0]);
    }

    private Rectangle createTransparentRect() {
        Rectangle transparentRect = new Rectangle(60, 60);
        transparentRect.setStroke(Color.color(0, 0, 0, 1));
        transparentRect.setFill(Color.GRAY);
        transparentRect.setOpacity(0.2);
        return transparentRect;
    }

    private Rectangle createMouseRect() {
        mouseRect = new Rectangle(60, 60);
        mouseRect.setOpacity(0.5);
        return mouseRect;
    }
}
