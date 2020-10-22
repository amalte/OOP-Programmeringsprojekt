package edu.chalmers.model.weapon.weapontypes;

/**
 * @author Erik Wetter
 * <p>
 * Handgun class. A type of weapon.
 */
public class Handgun implements IWeaponType {

    @Override
    public int getMagazineSize() {
        return 10;
    }

    @Override
    public int getDamage() {
        return 45;
    }

    @Override
    public int getReloadTimeMilliseconds() {
        return 1500;
    }

    @Override
    public int getProjectileSpeed() {
        return 900;
    }

    @Override
    public String getName() {
        return "Handgun";
    }
}
