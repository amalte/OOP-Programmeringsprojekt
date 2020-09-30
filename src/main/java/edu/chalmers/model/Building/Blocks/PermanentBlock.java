package edu.chalmers.model.Building.Blocks;

import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.model.Building.IBlock;

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
