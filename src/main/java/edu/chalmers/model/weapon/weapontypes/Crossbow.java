package edu.chalmers.model.weapon.weapontypes;

/**
 * Crossbow class. A type of weapon.
 */
public class Crossbow implements IWeaponType {

    @Override
    public int getMagazineSize() {
        return 1;
    }

    @Override
    public int getDamage() {
        return 75;
    }

    @Override
    public int getReloadTimeMilliseconds() {
        return 1000;
    }

    @Override
    public int getProjectileSpeed() {
        return 700;
    }

    @Override
    public String getName() {
        return "Crossbow";
    }
}
