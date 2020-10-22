package edu.chalmers.model.building;

import edu.chalmers.services.Coords;

import java.util.*;

/**
 * @author Malte Åkvist
 *
 * MapManager handles the map in the game (map of where all buildable blocks and permanent blocks are)
 */
public class MapManager implements IMapObserver {
    private HashMap<Coords, IBlock> blockMap;

    public MapManager(HashMap<Coords, IBlock> blockMap) {
        this.blockMap = blockMap;
    }

    private void removeAllLevitatingTiles() {
        HashSet<Coords> levitatingTiles = new HashSet<>();

        for (Coords tile : blockMap.keySet()) {
            if(!levitatingTiles.contains(tile) && isTileLevitatingDFS(tile)) {
                levitatingTiles.addAll(getConnectedTiles(tile));
            }
        }

        for (Coords levitatingTile : levitatingTiles) {    // Removes all levitating blocks
            blockMap.get(levitatingTile).remove();
            removeBlockFromMap(levitatingTile);
        }
    }

    private boolean isTileLevitatingDFS(Coords tile) {
        if(blockMap.get(tile) == null) return true;

        return isTileLevitatingDFS(tile, new HashSet<>());
    }

    // Method checks if a tile with a block on it in any way is connected to a permanent block
    private boolean isTileLevitatingDFS(Coords tile, HashSet<Coords> visitedTiles) {
        if(!blockMap.get(tile).canBeDestroyed()) {   // Found a path if the block is indestructible
            return false;
        }
        visitedTiles.add(tile);

        List<Coords> popNeighbours = getPopulatedNeighbourTiles(tile);
        for (Coords neighbour : popNeighbours) {
            if(!visitedTiles.contains(neighbour)) {   // Check tile if neighbour is not already visited
                if(!isTileLevitatingDFS(neighbour, visitedTiles)) {
                    return false;
                }
            }
        }

        return true; // if no path to a permanent block is found
    }

    /**
     * Method that checks if a tile is empty
     * @param tile the tile to check
     * @return boolean
     */
    boolean isTileEmpty(Coords tile) {
        return blockMap.get(tile) == null;
    }

    /**
     * Method that checks if a tile has any neighbours
     * @param tile the tile to check
     * @return boolean
     */
    boolean isTileConnected(Coords tile) {
        return blockMap.containsKey(getTileAbove(tile)) || blockMap.containsKey(getTileRight(tile)) || blockMap.containsKey(getTileBelow(tile)) || blockMap.containsKey(getTileLeft(tile));
        //return !getTileAbove(tile).equals(null) || !getTileRight(tile).equals(null) || !getTileBelow(tile).equals(null) || !getTileLeft(tile).equals(null);
    }

    /**
     * Adds a block to the blockMap
     * @param tile the position of the block
     * @param block instance of the block to add to blockMap
     */
    void addBlockToMap(Coords tile, IBlock block) {
        blockMap.putIfAbsent(tile, block);
    }

    /* Removes a block from the blockMap
     @param tile the position of the block to remove */
    private void removeBlockFromMap(Coords tile) {
        blockMap.remove(tile);
    }

    private HashSet<Coords> getConnectedTiles(Coords tile) {
        return getConnectedTiles(tile, new HashSet<>());
    }

    private HashSet<Coords> getConnectedTiles(Coords tile, HashSet<Coords> connectedTiles) {
        connectedTiles.add(tile);

        List<Coords> popNeighbours = getPopulatedNeighbourTiles(tile);
        for (Coords neighbour : popNeighbours) {
            if(!connectedTiles.contains(neighbour)) {  // Check the neighbours of neighbour if it isn't null and it is not already visited
                getConnectedTiles(neighbour, connectedTiles);
            }
        }

        return connectedTiles;
    }

    private List<Coords> getPopulatedNeighbourTiles(Coords tile) {
        List<Coords> populatedNeighbourTiles = new ArrayList<>();

        for (Coords coords : getNeighbourTiles(tile)) {
            if (blockMap.get(coords) != null) populatedNeighbourTiles.add(coords);
        }
        return populatedNeighbourTiles;
    }

    private List<Coords> getNeighbourTiles(Coords tile) {
        return Arrays.asList(getTileAbove(tile), getTileRight(tile), getTileBelow(tile), getTileLeft(tile));
    }

    private Coords getTileAbove(Coords tile) { return new Coords(tile.x(), tile.y()-1); }
    private Coords getTileRight(Coords tile) { return new Coords(tile.x()+1, tile.y()); }
    private Coords getTileBelow(Coords tile) { return new Coords(tile.x(), tile.y()+1); }
    private Coords getTileLeft(Coords tile) { return new Coords(tile.x()-1, tile.y()); }

    /**
     * Method is called when a block has died (enemy has killed it) and updates the map with correct info about blocks
     * @param tileRemoved the position of the block that has died
     */
    @Override
    public void update(Coords tileRemoved) {    // block has died
        removeBlockFromMap(tileRemoved);
        removeAllLevitatingTiles();
    }
}
