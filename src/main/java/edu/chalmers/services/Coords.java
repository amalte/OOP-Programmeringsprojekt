package edu.chalmers.services;

import java.util.Objects;

/**
 * @author Malte Åkvist
 *
 * Coords class used for tiles in the game (game has 32*18 tiles)
 */
public class Coords {
    private int x;
    private int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get method for the x value
     * @return int x value
     */
    public int x() { return x; }
    /**
     * Get method for the y value
     * @return int y value
     */
    public int y() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x &&
                y == coords.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

