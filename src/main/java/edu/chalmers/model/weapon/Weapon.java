package edu.chalmers.model.weapon;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.weapon.weapontypes.IWeaponType;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * Weapon class. Hold the functionality of weapons.
 */
public class Weapon {

    IWeaponType weaponType;

    private int magazineSize;
    private int reloadTimerMilliseconds;
    private int damage;
    private int projectileSpeed;
    private int magazineCounter;

    private TimerAction timerAction;

    public Weapon(IWeaponType weaponType) {
        this.weaponType = weaponType;

        this.magazineSize = weaponType.getMagazineSize();
        this.reloadTimerMilliseconds = weaponType.getReloadTimeMilliseconds();
        this.damage = weaponType.getDamage();
        this.projectileSpeed = weaponType.getProjectileSpeed();
        magazineCounter = magazineSize;
        initTimer();
    }

    /**
     * Creates an instance of WeaponProjectile
     * @param x Players x-position
     * @param y Players y-position
     */
    public void shoot(double x, double y) {
        if (magazineCounter > 0) {
            magazineCounter--;
            new WeaponProjectile(x, y, mouseLocation(), projectileSpeed);
        }
    }

    /**
     * Resets the magazineAmmo counter after a delay specified by reloadTimerMilliseconds
     */
    public void reload() {
        if (timerAction.isExpired()) {
            timerAction = createReloadTimer();
        }
    }

    private TimerAction createReloadTimer() {
        return runOnce(() -> magazineCounter = magazineSize, Duration.millis(reloadTimerMilliseconds));
    }

    private void initTimer() {
        timerAction = runOnce(() -> {}, Duration.millis(0));
    }

    private Point2D mouseLocation() {
        return FXGL.getInput().getMousePositionWorld();

    }

    /**
     * Gets the weapons damage
     * @return The damage variable
     */
    public int getDamage(){
        return damage;
    }


}
