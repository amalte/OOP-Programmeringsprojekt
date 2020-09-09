package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class EnemyFactory implements EntityFactory {

    private static int instanceAmount;
    private static EnemyFactory instance;

    // ENEMY STRING CONSTANTS
    public static String ENEMY = "enemy";

    public EnemyFactory() {

        // DEBUG
        instanceAmount++;
        System.out.println("EnemyFactory instances: " + instanceAmount);
    }

    public static EntityFactory get() {
        if(instance == null) {
            instance = new EnemyFactory();
        }

        return instance;
    }

    @Spawns("enemy")
    public Entity enemy(SpawnData spawnData) {
        return new Enemy(spawnData.getX(), spawnData.getY());
    }
}
