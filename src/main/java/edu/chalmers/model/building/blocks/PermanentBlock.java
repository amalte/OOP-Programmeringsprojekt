package edu.chalmers.model.building.blocks;

import edu.chalmers.model.building.IBlock;

/**
 * @author Malte Åkvist
 *
 * PermanentBlock that can't be removed (platforms in the game)
 */
public class PermanentBlock implements IBlock {

    @Override
    public boolean canBeDestroyed() {
        return false;
    }

    @Override
    public void remove() {
       // Can't be removed.
    }

    @Override
    public void inflictDamage(int damage) {
        // No damage for permanent blocks
    }
}
