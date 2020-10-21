package edu.chalmers.model.weapon.weapontypes;

/**
 * @author Erik Wetter
 *
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
        return 500;
    }

    @Override
    public int getProjectileSpeed() {
        return 800;
    }

    @Override
    public String getName() {
        return "Crossbow";
    }
}
