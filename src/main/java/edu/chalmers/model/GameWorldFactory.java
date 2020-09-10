package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;


public class GameWorldFactory implements EntityFactory {


    /**
     * Method used to spawn in "platform" types from tmx level files.
     * @param spawnData information brought over from the tmx file which contains value such as width, length, x-value and y-value.
     * @return An Entity object with physics and hit box with Enum type PLATFORM.
     */
    @Spawns("platform")
    public Entity newPlatform(SpawnData spawnData){
        return entityBuilder().type(EntityType.PLATFORM).from(spawnData).bbox(new HitBox(BoundingShape.box(spawnData.<Integer>get("width"), spawnData.<Integer>get("height")))).with(new PhysicsComponent()).build();
    }

}