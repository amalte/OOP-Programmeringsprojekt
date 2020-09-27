package edu.chalmers.model.Building;

import com.almasb.fxgl.entity.level.tiled.Tile;
import javafx.geometry.Point2D;

public class BuildManager {
    private int[][] map;
    private int emptyTile;

    public BuildManager(int[][] map, int emptyTile) {
        this.map = map;
        this.emptyTile = emptyTile;
    }

    private boolean isTileEmpty(Point2D tile) {
        return map[(int) tile.getX()][(int) tile.getY()] == emptyTile;
    }

    private void addBlockToMap(Point2D tile) {
        map[(int) tile.getX()][(int) tile.getY()] = 1;
    }

    private void removeBlockFromMap(Point2D tile) {
        map[(int) tile.getX()][(int) tile.getY()] = emptyTile;
    }
}
