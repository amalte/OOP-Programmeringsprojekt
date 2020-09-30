package edu.chalmers.model.building.blocks;

import edu.chalmers.model.building.IBlock;

public class PermanentBlock implements IBlock {
    @Override
    public boolean canBeDestroyed() {
        return false;
    }

    @Override
    public void remove() {
        if(canBeDestroyed()) {
            //FXGL.getGameWorld().removeEntity(currentBlock);
        }
    }
}
