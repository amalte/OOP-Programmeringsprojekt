package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameWorldFactory implements EntityFactory {

    /**
     * Method used to spawn in "platform" types from tmx level files.
     * @param spawnData information brought over from the tmx file which contains value such as width, length, x-value and y-value.
     * @return An Entity object with physics and hit box with Enum type PLATFORM.
     */
    @Spawns("platform")
    public Entity newPlatform(SpawnData spawnData){
        return FXGL.entityBuilder().type(EntityType.PLATFORM).bbox(new HitBox(BoundingShape.box(spawnData.<Integer>get("width"), spawnData.<Integer>get("height")))).with(new CollidableComponent(true)).with(new PhysicsComponent()).build();
    }

    @Spawns("platformSide")
    public Entity newPlatformSide(SpawnData spawnData){
        return FXGL.entityBuilder().type(EntityType.PLATFORMSIDE).bbox(new HitBox(BoundingShape.box(spawnData.<Integer>get("width"), spawnData.<Integer>get("height")))).with(new PhysicsComponent()).build();
    }

    @Spawns("platformBottom")
    public Entity newPlatformBottom(SpawnData spawnData){
        return FXGL.entityBuilder().type(EntityType.PLATFORMBOTTOM).bbox(new HitBox(BoundingShape.box(spawnData.<Integer>get("width"), spawnData.<Integer>get("height")))).with(new PhysicsComponent()).build();
    }

    @Spawns("worldBorder")
    public Entity newWorldBorder(SpawnData spawnData){
        return FXGL.entityBuilder().type(EntityType.WORLDBORDER).bbox(new HitBox(BoundingShape.box(spawnData.<Integer>get("width"), spawnData.<Integer>get("height")))).with(new PhysicsComponent()).build();
    }

    @Spawns("player")
    public Entity newPLayer(SpawnData spawnData){
        PhysicsComponent physics = new PhysicsComponent();
        return FXGL.entityBuilder().type(EntityType.PLAYER).at(spawnData.getX(),spawnData.getY())
                .viewWithBBox(new Rectangle(50, 50, Color.BLUE)).with(physics)
                .with(new PlayerComponent(physics)).with(new CollidableComponent(true)).build();
    }
}
