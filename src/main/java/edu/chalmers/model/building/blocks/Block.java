package edu.chalmers.model.building.blocks;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.utilities.Constants;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.model.building.IBlock;
import edu.chalmers.model.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block implements IBlock {
    Entity currentBlock;
    private PhysicsComponent physics = new PhysicsComponent();
    private int tileSize = Constants.TILE_SIZE;

    public Block(Point2D mousePos) {
        Point2D blockPosition = CoordsCalculations.posToTilePos(mousePos);

        physics.setBodyType(BodyType.STATIC);
        currentBlock = FXGL.entityBuilder()
                .type(EntityType.BLOCK)
                .at((blockPosition.getX()),(blockPosition.getY()))
                .viewWithBBox(new Rectangle(tileSize, tileSize, Color.DARKGREY))
                .with(physics)
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    /*public void removeBlock() {
        currentBlock.removeFromWorld();
    }*/

    @Override
    public boolean canBeDestroyed() {
        return true;
    }

    @Override
    public void remove() {
        if(canBeDestroyed()) {
            FXGL.getGameWorld().removeEntity(currentBlock);
        }
    }
}
