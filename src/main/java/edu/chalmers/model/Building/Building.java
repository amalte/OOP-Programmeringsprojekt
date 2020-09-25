package edu.chalmers.model.Building;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.model.EntityType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Building {
    private int tileSize = 60;
    private int buildRange = 200;
    private Point2D playerPos;
    private Point2D mousePos;

    public void placeBlock(Point2D mousePos, Point2D playerPos) {
        this.playerPos = playerPos;
        this.mousePos = mousePos;

        //collidesWithSomething(posToTilePos(mousePoint));  // places block and prints what it collides with

        if(possibleToPlaceBlockOnPos(mousePos)) {
            Point2D blockPosition = posToTilePos(mousePos);
            new WoodBlock(blockPosition, tileSize);
        }
    }

    private Point2D posToTile(Point2D pos) { // MousePos to Tile, example (50,150) = (0,2)
        int tileX = (int)(pos.getX()/tileSize);
        int tileY = (int)(pos.getY()/tileSize);

        return new Point2D(tileX, tileY);
    }

    private Point2D posToTilePos(Point2D pos) {  // MousePos to TilePos, example (50,150) = (0,120)
        Point2D tile = posToTile(pos);

        return tileToTilePos(tile);
    }

    private Point2D tileToTilePos(Point2D tile) {  // Tile to TilePos, example (0,2) = (0,120)
        return new Point2D(tile.getX() * tileSize, tile.getY() * tileSize);
    }

    private boolean possibleToPlaceBlockOnPos(Point2D pos) {
        Point2D tile = posToTile(pos);
        if(pos.distance(playerPos) > buildRange) {    // If out of range of placing block
            return false;
        }
        else if(posToTile(playerPos).equals(tile)) {  // If trying to place block on player
            return false;
        }

        // TODO check if block already exists on pos

        return true;
    }

    public void collidesWithSomething(Point2D tilePos) {
        Rectangle2D range = new Rectangle2D(tilePos.getX()+1, tilePos.getY()+1, 56, 56);
        System.out.println("ENTITIES IN RANGE: " + FXGL.getGameWorld().getEntitiesInRange(range).toString());

        Entity woodBlock;
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        Entity hello = FXGL.entityBuilder()
                .type(EntityType.BLOCK)
                .at((range.getMinX()),(range.getMinY()))
                .viewWithBBox(new Rectangle(56, 56, Color.BLACK))
                .with(physics)
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }
}
