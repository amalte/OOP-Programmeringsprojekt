package edu.chalmers.services;

import java.util.Objects;

/**
 * @author Malte Åkvist
 * <p>
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
     *
     * @return int x value
     */
    public int getX() {
        return x;
    }

    /**
     * Get method for the y value
     *
     * @return int y value
     */
    public int getY() { return y; }

    /**
     * Set method for the x value
     *
     * @param x X value
     */
    public void setX(int x) { this.x = x; }

    /**
     * Set method for the y value
     *
     * @param y Y value
     */
    public void setY(int y) { this.y = y; }

    /**
     * Equals method to check if this object is equal to another
     *
     * @param o Object to check if equal to
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x &&
                y == coords.y;
    }

    /**
     * Get hash code
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

