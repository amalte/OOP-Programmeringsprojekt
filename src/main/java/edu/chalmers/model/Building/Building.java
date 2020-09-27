package edu.chalmers.model.Building;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.Utilities.Constants;
import edu.chalmers.Utilities.TileCalculations;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private int tileSize = Constants.TILE_SIZE;
    private int buildRangeTiles = 3;

    public void placeBlock(Point2D mousePos, Point2D playerPos) {

        //collidesWithSomething(posToTilePos(mousePoint));  // places block and prints what it collides with

        getReachableTiles(TileCalculations.posToTile(playerPos, tileSize));

        if(possibleToPlaceBlockOnPos(mousePos, playerPos)) {
            Point2D blockPosition = TileCalculations.posToTilePos(mousePos, tileSize);
            new WoodBlock(blockPosition, tileSize);
        }
    }

    private boolean isInBuildRange(Point2D buildTile, Point2D playerTile) {
        if(Math.abs(buildTile.getX() - playerTile.getX()) > buildRangeTiles) return false;
        if(Math.abs(buildTile.getY() - playerTile.getY()) > buildRangeTiles) return false;

        return true;
    }

    private boolean isInBuildRangeOPTION2(Point2D buildTile, Point2D playerTile) {
        return buildTile.distance(playerTile) > buildRangeTiles;
    }

    public boolean possibleToPlaceBlockOnPos(Point2D buildPos, Point2D playerPos) {
        Point2D buildTile = TileCalculations.posToTile(buildPos, tileSize);
        Point2D playerTile = TileCalculations.posToTile(playerPos, tileSize);

        if(!isInBuildRange(playerTile, buildTile)) {    // If out of range of placing block
            return false;
        }
        else if(playerTile.equals(buildTile)) {  // If trying to place block on player
            return false;
        }

        FXGL.getGameWorld().getEntitiesByComponent(PlayerComponent.class).get(0).getPosition();

        // TODO check if block already exists on pos

        return true;
    }

    public List<Point2D> getReachableTiles(Point2D playerTile) {
        Point2D startTile = new Point2D(playerTile.getX()-buildRangeTiles, playerTile.getY()-buildRangeTiles);

        List<Point2D> reachableTiles = new ArrayList<>();

        int tileInRangeSize = buildRangeTiles*2+1;      // Width/Height of total tiles in range
        int tilesInRange = tileInRangeSize*tileInRangeSize;   // Width * Height (width = height)

        /*for(int i = 0; i < buildRange; i++) {
            for(int j = 0; j < buildRange; j++) {
                startPos[i][j] = Calculations.posToTile(
            }
        }*/
        for(int i = 0; i < tilesInRange; i++) {

            Point2D tile = new Point2D(startTile.getX() + (i/tileInRangeSize), startTile.getY() + i%tileInRangeSize);   // reachableTile

            if(tileIsInsideMap(tile)) {  // Tile cant be outside of map
                reachableTiles.add(tile);
            }
        }

        return reachableTiles;
    }

    private boolean tileIsInsideMap(Point2D tile) {
        return 0 <= tile.getX() && tile.getX() < Constants.TILEMAP_WIDTH && 0 <= tile.getY() && tile.getY() < Constants.TILEMAP_HEIGHT;
    }

    private boolean collidesWithSomething(Point2D tilePos) {
        Rectangle2D range = new Rectangle2D(tilePos.getX()+1, tilePos.getY()+1, tileSize-4, tileSize-4);    // Make rectangle a pixel smaller on each side
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

        return FXGL.getGameWorld().getEntitiesInRange(range).size() >= 1;
    }
}
