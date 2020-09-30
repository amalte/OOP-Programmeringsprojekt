package edu.chalmers.model.weapon;

import edu.chalmers.model.weapon.weapontypes.Crossbow;
import edu.chalmers.model.weapon.weapontypes.Handgun;
import edu.chalmers.model.weapon.weapontypes.ThrowingKnife;

/**
 * A factory used to create different types of weapons.
 */
public class WeaponFactory {

    private static WeaponFactory instance;

    /**
     * Singleton. Gets instance of this class, and creates one if instance doesn't already exist.
     * @return Returns the singleton instance of the class.
     */
    public static WeaponFactory getInstance() {
        if(instance == null) {
            instance = new WeaponFactory();
        }

        return instance;
    }

    /**
     * Creates a weapon with a type.
     * @param weaponName Name of the type of weapon that should be created.
     * @return A weapon object with attributes specified by the type.
     */
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
