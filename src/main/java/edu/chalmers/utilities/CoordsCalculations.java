package edu.chalmers.utilities;

import edu.chalmers.services.Coords;
import javafx.geometry.Point2D;

public class CoordsCalculations {

    public static Coords posToTile(Point2D pos) { // MousePos to tile, example (50,150) = (0,2)
        int tileX = (int)(pos.getX()/Constants.TILE_SIZE);
        int tileY = (int)(pos.getY()/Constants.TILE_SIZE);

        return new Coords(tileX, tileY);
    }

    public static Point2D tileToPos(Coords tile) {  // Coords to TilePos, example (0,2) = (0,120)
        return new Point2D(tile.x() * Constants.TILE_SIZE, tile.y() * Constants.TILE_SIZE);
    }

    public static Point2D posToTilePos(Point2D pos) {  // MousePos to TilePos, example (50,150) = (0,120)
        Coords tile = posToTile(pos);

        return tileToPos(tile);
    }
}
