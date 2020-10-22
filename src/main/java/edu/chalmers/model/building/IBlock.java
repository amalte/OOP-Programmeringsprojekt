package edu.chalmers.model.building;

/**
 * @author Malte Åkvist
 * <p>
 * Interface for all blocks
 */
public interface IBlock {
    boolean canBeDestroyed();

    void remove();
}
