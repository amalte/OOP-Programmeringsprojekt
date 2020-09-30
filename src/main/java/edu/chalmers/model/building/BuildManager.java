package edu.chalmers.model.building;

import edu.chalmers.utilities.Constants;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.services.Coords;
import edu.chalmers.services.TileMap;
import edu.chalmers.model.building.blocks.Block;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class BuildManager {
    private MapManager mapManager;
    private int tileSize = Constants.TILE_SIZE;
    private int buildRangeTiles;

    public BuildManager(int buildRangeTiles) {
        //this.mapManager = mapManager;
        this.buildRangeTiles = buildRangeTiles;
        mapManager = new MapManager(new TileMap().getBlockMapFromLevel("map2.tmx"));
    }

    public void placeBlock(Point2D mousePos) {
        mapManager.addBlockToMap(CoordsCalculations.posToTile(mousePos), new Block(mousePos));
    }

    public MapManager getMapManager() { return mapManager; }

    public boolean possibleToPlaceBlockOnPos(Point2D mousePos, Point2D playerPos) {
        Coords buildTile = CoordsCalculations.posToTile(mousePos);
        Coords playerTile = CoordsCalculations.posToTile(playerPos);

        if(!isInBuildRange(playerTile, buildTile)) {    // Has to be in range to place block
            return false;
        }
        else if(playerTile.equals(buildTile)) {  // Can't place block on player
            return false;
        }
        else if(!mapManager.isTileEmpty(buildTile)) {   // Can't build if tile is occupied
            return false;
        }
        else if(!mapManager.isTileConnected(buildTile)) {   // Can't build if tile isn't connected to another tile
            return false;
        }

        return true;
    }

    public List<Coords> getReachableTiles(Coords playerTile) {
        Coords startTile = new Coords(playerTile.x()-buildRangeTiles, playerTile.y()-buildRangeTiles);

        List<Coords> reachableTiles = new ArrayList<>();

        int tileInRangeSize = buildRangeTiles*2+1;      // Width/Height of total tiles in range
        int tilesInRange = tileInRangeSize*tileInRangeSize;   // Width * Height (width = height)

        for(int i = 0; i < tilesInRange; i++) {

            Coords tile = new Coords(startTile.x() + (i/tileInRangeSize), startTile.y() + i%tileInRangeSize);   // reachableTile

            if(tileIsInsideMap(tile)) {  // Tile cant be outside of map
                reachableTiles.add(tile);
            }
        }

        return reachableTiles;
    }

    public List<Coords> getEmptyReachableTiles(Coords playerTile) {
        List<Coords> emptyReachableTiles = new ArrayList<>();
        List<Coords> reachableTiles = getReachableTiles(playerTile);

        for(int i = 0; i < reachableTiles.size(); i++) {
            if(mapManager.isTileEmpty(reachableTiles.get(i))) {
                emptyReachableTiles.add(reachableTiles.get(i));
            }
        }
        return emptyReachableTiles;
    }

    public boolean isInBuildRange(Coords buildTile, Coords playerTile) {
        if(Math.abs(buildTile.x() - playerTile.x()) > buildRangeTiles) return false;
        if(Math.abs(buildTile.y() - playerTile.y()) > buildRangeTiles) return false;

        return true;
    }

    private boolean tileIsInsideMap(Coords tile) {
        return 0 <= tile.x() && tile.y() < Constants.TILEMAP_WIDTH && 0 <= tile.y() && tile.y() < Constants.TILEMAP_HEIGHT;
    }

    public int getBuildRangeTiles() { return buildRangeTiles; }

    /*private boolean collidesWithSomething(Point2D tilePos) {
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
    }*/
}
