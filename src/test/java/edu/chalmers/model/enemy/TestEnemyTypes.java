package edu.chalmers.model.enemy;

import edu.chalmers.model.enemy.enemytypes.Blob;
import edu.chalmers.model.enemy.enemytypes.Rex;
import edu.chalmers.model.enemy.enemytypes.Zombie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEnemyTypes {


    @Test
    public void testGetZombieClass(){
        assertEquals(Zombie.class, EnemyTypes.getZombieClass());
    }

    @Test
    public void testGetRexClass(){
        assertEquals(Rex.class, EnemyTypes.getRexClass());
    }

    @Test
    public void testGetBlobClass(){
        assertEquals(Blob.class, EnemyTypes.getBlobClass());
    }
}
