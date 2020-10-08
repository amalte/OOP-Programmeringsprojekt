package edu.chalmers.model.enemy;

import edu.chalmers.model.enemy.enemytypes.Blob;
import edu.chalmers.model.enemy.enemytypes.Rex;
import edu.chalmers.model.enemy.enemytypes.Zombie;

/**
 * EnemyTypes class. Holds a reference to all Enemy type classes. Acts as a facade for the different Enemy types.
 */
public class EnemyTypes {

    // Not instantiable.
    private EnemyTypes(){}

    public static Class getZombieClass() {
        return Zombie.class;
    }

    public static Class getRexClass() {
        return Rex.class;
    }

    public static Class getBlobClass() {
        return Blob.class;
    }
}
