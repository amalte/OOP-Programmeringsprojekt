package edu.chalmers.model;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.weapon.Weapon;
import edu.chalmers.model.weapon.WeaponFactory;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

/**
 * Player class. Wraps an entity object as a Player.
 */
public class PlayerComponent extends Component {

    private List<Weapon> weapons = new ArrayList<>();

    //Stats
    private int health = 100;
    private int moveSpeed = 150;
    private int jumpHeight = 375;
    private final int AMOUNT_OF_JUMPS = 1;
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
     * Get method used fot testing purposes.
     * @return integer jumps.
     */
    public int getJumps() {
        return jumps;
    }

    public int getBuildRangeTiles() { return buildRangeTiles; }

    /**
     * Getter for PlayerComponents health.
     * @return Integer of PlayerComponents health.
     */
    public int getHealth(){
        return health;
    }

    /**
     *Getter for variable weapon.
     * @return The weapon currently selected by the PlayerComponent.
     */
    public Weapon getActiveWeapon(){
        return weapons.get(activeWeapon);
    }

    /**
     * Sets the active weapon.
     * @param activeWeapon Integer to replace the current activeWeapon.
     */
    public void setActiveWeapon(int activeWeapon) {

        this.activeWeapon = activeWeapon;
    }

    /**
     * Getter for variable moveSpeed (intended for testing)
     * @return PlayerComponents unchangeable movementSpeed.
     */
    public int getMoveSpeed(){
        return moveSpeed;
    }

    /**
     * Method moves players Entity left (negative x).
     */
    public void moveLeft(){
        physics.setVelocityX(-moveSpeed);
    }

    /**
     * Method moves players Entity right (positive x).
     */
    public void moveRight(){
        physics.setVelocityX(moveSpeed);
    }

    /**
     * Method moves players Entity up (negative y) with jumpHeight if the player have any jumps left.
     * Reduce amount of jumps left by 1.
     */
    public void jump(){
        if(jumps != 0) {
            physics.setVelocityY(-jumpHeight);
            jumps--;
        }
    }

    /**
     * Calls method shoot from PlayerComponent's selected weapon.
     */
    public void shoot() {
        weapons.get(activeWeapon).shoot(entity.getX(), entity.getY());
    }

    /**
     * Calls method reload from PlayerComponent's selected weapon.
     */
    public void reload() {
        weapons.get(activeWeapon).reload();
    }

    /**
     * Method stop players Entity in the x direction.
     */
    public void stop(){
        physics.setVelocityX(0);
    }

    /**
     * Resets players jumps to be equal to amountOfJumps variable.
     */
    public void resetJumpAmounts(){
        jumps = AMOUNT_OF_JUMPS;
    }

    /**
     * Lower PlayerComponents health with damage if player have not taken damage within 1 second.
     * @param damage amount of health points to be inflicted to player.
     */
    public void inflictDamage(int damage){
        if(timer.isExpired()){
            timer = runOnce(() -> health -= damage, Duration.seconds(1));
        }
    }

    /**
     * Initiate damage delay timer.
     */
    private void initTimer(){
        timer = runOnce(() -> {}, Duration.seconds(0));
    }

    // -------- GETTERS -------- //

    /**
     * Getter for onGround variable.
     * @return True (Player is on the ground *or* has most recently not touched a platform) or False (Enemy has not touched the ground since touching a platform).
     */
    public boolean isOnGround() {
        return onGround;
    }

    /**
     * Getter for isAirborne variable.
     * @return True (Player is in the air) or False (Player is on platform or ground).
     */
    public boolean isAirborne() {
        return isAirborne;
    }

    // -------- SETTERS -------- //

    /**
     * Setter for onGround variable.
     * @param onGround True or False.
     */
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    /**
     * Setter for isAirborne variable.
     * @param airborne True or False.
     */
    public void setAirborne(boolean airborne) {
        isAirborne = airborne;
    }
}
