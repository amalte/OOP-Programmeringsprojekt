package edu.chalmers.view;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.services.Coords;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class BuildView {
    Texture blockTexture;
    Rectangle mouseRect;

    Rectangle transparentRect;
    Texture outlineTexture;

    List<Node> transparentRects = new ArrayList<>();

    public void buildStateSelected() {
        //FXGL.getGameScene().addUI(new UI());

        blockTexture = FXGL.getAssetLoader().loadTexture("woodBlock.png");
        blockTexture.toColor(Color.rgb(255, 0, 0, 0.1));
        blockTexture.setTranslateX(350);
        blockTexture.setTranslateY(800);

        outlineTexture = FXGL.getAssetLoader().loadTexture("outline.png");
        outlineTexture.setFitWidth(60);
        outlineTexture.setFitHeight(60);
        outlineTexture.toColor(Color.rgb(255, 255, 255, 0.1));

        transparentRect = new Rectangle(60, 60);
        transparentRect.setStroke(Color.color(0, 0, 0, 1));
        transparentRect.setFill(Color.GRAY);
        transparentRect.setOpacity(0.5);

        mouseRect = new Rectangle(60, 60);
        mouseRect.setOpacity(0.5);

        //FXGL.getGameScene().addUINode(blockTexture);
        FXGL.getGameScene().addUINode(mouseRect);
        //FXGL.getGameScene().addUINode(transparentRect);
        //FXGL.getGameScene().addUINode(outlineTexture);
    }

    public void stopFollowMouse() {
        mouseRect.setVisible(false);
    }

    public void followMouse(Point2D mousePos, boolean possibleToPlaceBlock) {
        mouseRect.setVisible(true);
        //blockTexture.setTranslateX(mousePos.getX());
        //blockTexture.setTranslateY(mousePos.getY());
        if(possibleToPlaceBlock) {
            mouseRect.setFill(Color.GREEN);
        }
        else {
            mouseRect.setFill(Color.RED);
        }
        Point2D followMousePos = CoordsCalculations.posToTilePos(mousePos);
        mouseRect.setTranslateX(followMousePos.getX());
        mouseRect.setTranslateY(followMousePos.getY());

        //transparentRect.setTranslateX(mousePos.getX());
        //transparentRect.setTranslateY(mousePos.getY());

        //outlineTexture.setTranslateX(mousePos.getX());
        //outlineTexture.setTranslateY(mousePos.getY());
    }

    private Rectangle createTransparentRect() {
        Rectangle transparentRect = new Rectangle(60, 60);
        transparentRect.setStroke(Color.color(0, 0, 0, 1));
        transparentRect.setFill(Color.GRAY);
        transparentRect.setOpacity(0.2);
        return transparentRect;
    }

    public void setUpTransparentTiles() {
        int tileWidth = 3*2+1;  // should get from player: buildRangeTiles
        int totalTiles = tileWidth*tileWidth;

        for(int i = 0; i < totalTiles; i++) {
            transparentRects.add(createTransparentRect());
        }
        FXGL.getGameScene().addUINodes(transparentRects.toArray(new Node[0]));
    }

    public void reachableTiles(List<Coords> reachableTiles) {
        for (Node node: transparentRects) {
            node.setVisible(false);     // Hide all since list will get updated
        }

        for(int i = 0; i < reachableTiles.size(); i++) {
            Point2D reachableTilePos = CoordsCalculations.tileToPos(reachableTiles.get(i));
            transparentRects.get(i).setTranslateX(reachableTilePos.getX());
            transparentRects.get(i).setTranslateY(reachableTilePos.getY());
            transparentRects.get(i).setVisible(true);   // Only the ones that should be seen are visible
        }
    }
}
