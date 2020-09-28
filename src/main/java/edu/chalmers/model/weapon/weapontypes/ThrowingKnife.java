package edu.chalmers.model.weapon.weapontypes;

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
        return 2500;
    }

    @Override
    public int getProjectileSpeed() {
        return 450;
    }
}
