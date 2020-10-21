package edu.chalmers.model.building;

/**
 * @author Malte Åkvist
 *
 * Interface for all blocks
 */
public interface IBlock {
    boolean canBeDestroyed();
    void remove();
    void inflictDamage(int damage);
}
