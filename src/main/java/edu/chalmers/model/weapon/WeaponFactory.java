package edu.chalmers.model.weapon;

import edu.chalmers.model.weapon.weapontypes.Crossbow;
import edu.chalmers.model.weapon.weapontypes.Handgun;
import edu.chalmers.model.weapon.weapontypes.ThrowingKnife;

public class WeaponFactory {

    private static WeaponFactory instance;

    public static WeaponFactory getInstance() {
        if(instance == null) {
            instance = new WeaponFactory();
        }

        return instance;
    }

    public Weapon createWeapon(String weaponName) {

        if (weaponName.equalsIgnoreCase("ThrowingKnife")) {
            return new Weapon(new ThrowingKnife());
        }else if (weaponName.equalsIgnoreCase("Crossbow")) {
            return new Weapon(new Crossbow());
        }else if (weaponName.equalsIgnoreCase("Handgun")) {
            return new Weapon(new Handgun());
        }else {
            return null;
        }

    }
}
