package edu.chalmers.view;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.UI;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class BuildView {
    Texture blockTexture;
    Rectangle mouseRect;

    Rectangle transparentRect;
    Texture outlineTexture;

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
        transparentRect.setFill(Color.AQUA);
        transparentRect.setOpacity(0.5);

        mouseRect = new Rectangle(60, 60);
        mouseRect.setOpacity(0.5);

        //FXGL.getGameScene().addUINode(blockTexture);
        //FXGL.getGameScene().addUINode(mouseRect);
        //FXGL.getGameScene().addUINode(transparentRect);
        //FXGL.getGameScene().addUINode(outlineTexture);
    }

    public void followMouse(Point2D mousePos, boolean possibleToPlaceBlock) {
        //blockTexture.setTranslateX(mousePos.getX());
        //blockTexture.setTranslateY(mousePos.getY());
        if(possibleToPlaceBlock) {
            mouseRect.setFill(Color.GREEN);
        }
        else {
            mouseRect.setFill(Color.RED);
        }
        mouseRect.setTranslateX(mousePos.getX());
        mouseRect.setTranslateY(mousePos.getY());

        //transparentRect.setTranslateX(mousePos.getX());
        //transparentRect.setTranslateY(mousePos.getY());

        //outlineTexture.setTranslateX(mousePos.getX());
        //outlineTexture.setTranslateY(mousePos.getY());
    }

    public void setUpTransparentTiles() {
        int tileWidth = 3*2+1;
        int totalTiles = tileWidth*tileWidth;

        for(int i = 0; i < totalTiles; i++) {
            Rectangle transparentRect = new Rectangle(60, 60);
            transparentRect.setStroke(Color.color(0, 0, 0, 1));
            transparentRect.setFill(Color.AQUA);
            transparentRect.setOpacity(0.5);
            FXGL.getGameScene().addUINode(transparentRect);
        }
    }

    public void reachableTiles(List<Point2D> reachableTiles) {
        for(int i = 0; i < reachableTiles.size(); i++) {
            FXGL.getGameScene().getUiNodes().get(i).setTranslateX(reachableTiles.get(i).getX());
            FXGL.getGameScene().getUiNodes().get(i).setTranslateY(reachableTiles.get(i).getY());
        }
    }
}
