package edu.chalmers.model;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.weapon.Weapon;
import edu.chalmers.model.weapon.WeaponFactory;
import edu.chalmers.utilities.EntityPos;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * @author Oscar Arvidson
 * <p>
 * Player class. Wraps an entity object as a Player.
 */
public class PlayerComponent extends Component implements IObservable {

    private final int AMOUNT_OF_JUMPS = 1;
    private List<Weapon> weapons = new ArrayList<>();
    private boolean testing = false; //Boolean used for testing
    //Stats
    private int maxHealth = 100;
    private int health = maxHealth;
    private int moveSpeed = 175;
    private int jumpHeight = 400;
    private int jumps = AMOUNT_OF_JUMPS;
    private int buildRangeTiles = 3;
    private PhysicsComponent physics;
    private int activeWeapon = 0;
    private TimerAction timer;
    private boolean onGround;
    private boolean isAirborne;

    public PlayerComponent(PhysicsComponent physics) {
        this.physics = physics;
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        initTimer();

        weapons.add(0, WeaponFactory.getInstance().createWeapon("Handgun"));
        weapons.add(1, WeaponFactory.getInstance().createWeapon("Crossbow"));
        weapons.add(2, WeaponFactory.getInstance().createWeapon("ThrowingKnife"));
    }

    /**
     * Method moves players Entity left (negative x).
     * Calls method moveLeft from animationComponent.
     */
    public void moveLeft() {
        physics.setVelocityX(-moveSpeed);
        entity.getComponent(AnimationComponent.class).moveLeft();
    }

    /**
     * Method moves players Entity right (positive x).
     * Calls method moveRight from animationComponent.
     */
    public void moveRight() {
        physics.setVelocityX(moveSpeed);
        entity.getComponent(AnimationComponent.class).moveRight();
    }

    /**
     * Method moves players Entity up (negative y) with jumpHeight if the player have any jumps left.
     * Reduce amount of jumps left by 1.
     * Calls method jump from animationComponent.
     */
    public void jump() {
        if (jumps != 0) {
            physics.setVelocityY(-jumpHeight);
            entity.getComponent(AnimationComponent.class).jump();
            jumps--;
        }
    }

    /**
     * Calls method shoot from PlayerComponent's selected weapon.
     */
    public void shoot() { weapons.get(activeWeapon).shoot(entity.getX(), entity.getY()); }

    /**
     * Calls method reload from PlayerComponent's selected weapon.
     */
    public void reload() {
        weapons.get(activeWeapon).reload();
    }

    /**
     * Method stop players Entity in the x direction.
     */
    public void stop() {
        physics.setVelocityX(0);
    }

    /**
     * Calls method landed from AnimationComponent.
     * Calls method resetJumpAmounts().
     */
    public void landed() {
        entity.getComponent(AnimationComponent.class).landed();
        resetJumpAmounts();
    }

    /**
     * Resets players jumps to be equal to amountOfJumps variable.
     */
    public void resetJumpAmounts() {
        jumps = AMOUNT_OF_JUMPS;
    }

    /**
     * Lower PlayerComponents health with damage if player have not taken damage within 1 second.
     *
     * @param damage amount of health points to be inflicted to player.
     */
    public void inflictDamage(int damage) {
        if (!testing) {
            if (timer.isExpired()) {
                health -= damage;
                notifyObserver();
                timer = runOnce(() -> {
                }, Duration.seconds(1));
            }
        } else {
            health -= damage;
        }
    }

    /**
     * Initiate damage delay timer.
     */
    private void initTimer() {
        timer = runOnce(() -> {
        }, Duration.seconds(0));
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void notifyObserver() {
        for (IObserver o : observers) {
            o.update();
        }
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    // -------- GETTERS -------- //

    /**
     * Getter for onGround variable.
     *
     * @return True (Player is on the ground *or* has most recently not touched a platform) or False (Enemy has not touched the ground since touching a platform).
     */
    public boolean isOnGround() {
        return onGround;
    }

    /**
     * Setter for onGround variable.
     *
     * @param onGround True or False.
     */
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    /**
     * Getter for isAirborne variable.
     *
     * @return True (Player is in the air) or False (Player is on platform or ground).
     */
    public boolean isAirborne() {
        return isAirborne;
    }

    /**
     * Setter for isAirborne variable.
     *
     * @param airborne True or False.
     */
    public void setAirborne(boolean airborne) {
        isAirborne = airborne;
    }

    /**
     * Getter for the list weapons used for testing purposes.
     *
     * @return List of Weapon "weapons".
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Getter for variable jumpHeight (intended for testing)
     *
     * @return PlayerComponents unchangeable jumpHeight.
     */
    public int getJumpHeight() {
        return jumpHeight;
    }

    /**
     * Get method used fot testing purposes.
     *
     * @return integer jumps.
     */
    public int getJumps() {
        return jumps;
    }

    /**
     * Getter for variable buildRangeTiles.
     *
     * @return Integer for the max range of block the player can build.
     */
    public int getBuildRangeTiles() {
        return buildRangeTiles;
    }

    /**
     * Getter for PlayerComponents health.
     *
     * @return Integer of PlayerComponents health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for variable weapon.
     *
     * @return The weapon currently selected by the PlayerComponent.
     */
    public Weapon getActiveWeapon() {
        return weapons.get(activeWeapon);
    }

    // -------- SETTERS -------- //

    /**
     * Sets the active weapon.
     *
     * @param activeWeapon Integer to replace the current activeWeapon.
     */
    public void setActiveWeapon(int activeWeapon) {

        this.activeWeapon = activeWeapon;
    }

    /**
     * Getter for variable moveSpeed (intended for testing)
     *
     * @return PlayerComponents unchangeable movementSpeed.
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Getter for variable maxHealth.
     *
     * @return Integer max health for PlayerComponent.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Setter for testing variable used to test time based methods.
     *
     * @param state True or False.
     */
    public void setTesting(boolean state) {
        testing = state;
    }
}
