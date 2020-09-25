package edu.chalmers.Utils;

import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.entity.level.tiled.TiledMap;
import javafx.geometry.Point2D;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class TileMap {
    private TiledMap tileMap;
    private int[][] tileData;
    private int tileWidth;
    private int tileHeight;

    public TileMap(String levelName) {
        setTileMap(levelName);
        tileData = getTileData();
    }

    public int[][] getTileData() {
        tileWidth = tileMap.getWidth();
        tileHeight = tileMap.getHeight();
        tileData = new int[tileHeight][tileWidth];

        List<Integer> dataList = tileMap.getLayerByName("Tile Layer 1").getData();  // Get 1D dataList

        for (int row = 0; row < tileHeight; row++) {    // 1D to 2D array
            for (int col = 0; col < tileWidth; col++) {
                tileData[row][col] = dataList.get(col + row * tileWidth);
            }
        }
        //System.out.println(Arrays.deepToString(tileData).replace("], ", "]\n"));  // Print array

        return tileData;
    }

    public void setTileMap(String levelName) {
        String fileName = "src/main/resources/assets/levels/";

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName + levelName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tileMap = new TMXLevelLoader().parse(fileInputStream);
    }

    public boolean tileContainsBlock(Point2D tile) {
        return tileData[(int) tile.getY()][(int) tile.getX()] == 0;   // 0 means tile is empty
    }
}
