package edu.chalmers.utilities;

import edu.chalmers.services.Coords;
import javafx.geometry.Point2D;

/**
 * @author Malte Åkvist
 * <p>
 * CoordsCalculations used to convert between tiles (Coords) and points.
 */
public final class CoordsCalculations {

    private CoordsCalculations() {
    }

    /**
     * Method converts a position to a tile on the map
     *
     * @param pos the position to convert
     * @return a coordinate of a tile
     */
    public static Coords posToTile(Point2D pos) { // MousePos to tile, example (50,150) = (0,2)
        int tileX = (int) (pos.getX() / Constants.TILE_SIZE);
        int tileY = (int) (pos.getY() / Constants.TILE_SIZE);

        return new Coords(tileX, tileY);
    }

    /**
     * Method converts a tile on the map to a position
     *
     * @param tile the tile to convert
     * @return a position with x and y value
     */
    public static Point2D tileToPos(Coords tile) {  // Coords to TilePos, example (0,2) = (0,120)
        return new Point2D(tile.x() * Constants.TILE_SIZE, tile.y() * Constants.TILE_SIZE);
    }

    /**
     * Method converts a position to the responding tile's position
     *
     * @param pos the position to convert
     * @return the corresponding tile's position
     */
    public static Point2D posToTilePos(Point2D pos) {  // MousePos to TilePos, example (50,150) = (0,120)
        Coords tile = posToTile(pos);

        return tileToPos(tile);
    }
}
