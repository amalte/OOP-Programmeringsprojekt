package edu.chalmers.model.weapon.weapontypes;

/**
 * ThrowingKnife class. A type of weapon.
 */
public class ThrowingKnife implements IWeaponType{

    @Override
    public int getMagazineSize() {
        return 15;
    }

    @Override
    public int getDamage() {
        return 35;
    }

    @Override
    public int getReloadTimeMilliseconds() {
        return 2000;
    }

    @Override
    public int getProjectileSpeed() {
        return 600;
    }

    @Override
    public String getName() {
        return "Throwing Knife";
    }
}
