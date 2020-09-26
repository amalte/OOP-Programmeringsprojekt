package edu.chalmers.model.weapon.weapontypes;

public interface IWeaponType {
    int getMagazineSize();
    int getDamage();
    int getReloadTimeMilliseconds();
    int getProjectileSpeed();

}
