package edu.chalmers.model.Building;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import edu.chalmers.model.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WoodBlock {
    Entity woodBlock;
    private PhysicsComponent physics = new PhysicsComponent();

    public WoodBlock(Point2D blockPosition, int tileSize) {

        physics.setBodyType(BodyType.STATIC);
        woodBlock = FXGL.entityBuilder()
                .type(EntityType.BLOCK)
                .at((blockPosition.getX()),(blockPosition.getY()))
                .viewWithBBox(new Rectangle(tileSize, tileSize, Color.BLUE))
                .with(physics)
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }
}
