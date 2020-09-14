package edu.chalmers.model;

import com.almasb.fxgl.entity.SpawnData;

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

    public Enemy zombie(SpawnData spawnData, Player target) {
        return new Zombie(spawnData.getX(), spawnData.getY(), target);
    }
}
