package edu.chalmers.Utils;

import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.entity.level.tiled.TiledMap;
import edu.chalmers.Utilities.Constants;
import edu.chalmers.model.Building.Blocks.PermanentBlock;
import edu.chalmers.model.Building.IBlock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class TileMap {
    private String dataLayer = "Tile Layer 1";
    private int emptyTile = 0;

    public HashMap<Coords, IBlock> getBlockMapFromLevel(String levelName) {
        HashMap<Coords, IBlock> blockMap = new HashMap<>();
        List<Integer> dataList = getDataFromLevel(levelName);

        for(int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i) != emptyTile) {    // contains permanent that cant be removed (platforms)
                blockMap.put(new Coords(i% Constants.TILEMAP_WIDTH, i/Constants.TILEMAP_WIDTH), new PermanentBlock());
            }
        }

        return blockMap;
    }

    private List<Integer> getDataFromLevel(String levelName) {
        TiledMap tileMap = getTileMap(levelName);

        return tileMap.getLayerByName(dataLayer).getData();
    }

    private TiledMap getTileMap(String levelName) {
        String fileName = "src/main/resources/assets/levels/";

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName + levelName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new TMXLevelLoader().parse(fileInputStream);
    }
}
