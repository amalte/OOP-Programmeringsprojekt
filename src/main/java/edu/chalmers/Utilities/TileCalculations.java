package edu.chalmers.Utilities;

import javafx.geometry.Point2D;

public class TileCalculations {

    public static Point2D posToTile(Point2D pos, int tileSize) { // MousePos to Tile, example (50,150) = (0,2)
        int tileX = (int)(pos.getX()/tileSize);
        int tileY = (int)(pos.getY()/tileSize);

        return new Point2D(tileX, tileY);
    }

    public static Point2D tileToTilePos(Point2D tile, int tileSize) {  // Tile to TilePos, example (0,2) = (0,120)
        return new Point2D(tile.getX() * tileSize, tile.getY() * tileSize);
    }

    public static Point2D posToTilePos(Point2D pos, int tileSize) {  // MousePos to TilePos, example (50,150) = (0,120)
        Point2D tile = posToTile(pos, tileSize);

        return tileToTilePos(tile, tileSize);
    }
}
