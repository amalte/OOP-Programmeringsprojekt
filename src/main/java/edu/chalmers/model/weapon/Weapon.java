package edu.chalmers.model.weapon;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.IObservable;
import edu.chalmers.model.IObserver;
import edu.chalmers.model.weapon.weapontypes.IWeaponType;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * Weapon class. Hold the functionality of weapons.
 */
public class Weapon implements IObservable {

    private IWeaponType weaponType;
    private int magazineSize;
    private int reloadTimerMilliseconds;
    private int damage;
    private int projectileSpeed;
    private int magazineCounter;
    private boolean reloading = false;
    private boolean testing = false;

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
        if (magazineCounter > 0 && !reloading) {
            magazineCounter--;
            new WeaponProjectile(new Point2D(x,y), mouseLocation(), projectileSpeed,false);
        }
    }

    /**
     * Resets the magazineAmmo counter after a delay specified by reloadTimerMilliseconds
     */
    public void reload() {
        reloading = true;
        notifyObserver();
        if(!testing) {
            if (timerAction.isExpired()) {
            timerAction = runOnce(() -> resetMagazine(), Duration.millis(reloadTimerMilliseconds));
            }
        }else {
            resetMagazine();
        }
    }

    private void resetMagazine() {
        magazineCounter = magazineSize;
        reloading = false;
        notifyObserver();
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

    public int getMagazineCounter() {return magazineCounter;}

    /**
     * @return The size of the weapon's magazine.
     */
    public int getMagazineSize() { return magazineSize; }

    public IWeaponType getWeaponType() {
        return weaponType;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void notifyObserver() {
        for(IObserver o : observers){
            o.update();
        }
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

}
