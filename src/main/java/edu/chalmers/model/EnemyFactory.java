package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;

public class EnemyFactory {

    private static int instanceAmount;
    private static EnemyFactory instance;

    public EnemyFactory() {

        // DEBUG
        instanceAmount++;
        System.out.println("EnemyFactory instances: " + instanceAmount);
    }

    public static EnemyFactory getInstance() {
        if(instance == null) {
            instance = new EnemyFactory();
        }

        return instance;
    }

    public static Zombie zombie(SpawnData spawnData, Player target) {
        return new Zombie(spawnData.getX(), spawnData.getY(), target);
    }
}
