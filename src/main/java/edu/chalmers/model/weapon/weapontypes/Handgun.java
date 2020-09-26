package edu.chalmers.model.weapon.weapontypes;

public class Handgun implements IWeaponType{

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
        return 2000;
    }

    @Override
    public int getProjectileSpeed() {
        return 600;
    }
}
