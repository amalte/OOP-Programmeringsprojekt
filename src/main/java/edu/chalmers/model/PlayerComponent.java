package edu.chalmers.model;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import edu.chalmers.model.building.blocks.Block;
import javafx.geometry.Point2D;
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

    List<Weapon> weaponArrayList = new ArrayList<>();

    //Stats
    private int health = 100;
    private int moveSpeed = 150;
    private int jumpHeight = 375;
    private final int amountOfJumps = 1;
    private int jumps = amountOfJumps;
    private int buildRangeTiles = 3;
    private PhysicsComponent physics;
    private int activeWeapon = 0;
    private TimerAction timer;

    public PlayerComponent(PhysicsComponent physics) {
        this.physics = physics;
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        initTimer();

        weaponArrayList.add(0, WeaponFactory.getInstance().createWeapon("Handgun"));
        weaponArrayList.add(1, WeaponFactory.getInstance().createWeapon("Crossbow"));
        weaponArrayList.add(2, WeaponFactory.getInstance().createWeapon("ThrowingKnife"));
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
    public Weapon getWeapon(){
        return weaponArrayList.get(activeWeapon);
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
        weaponArrayList.get(activeWeapon).shoot(entity.getX(), entity.getY());
    }

    public void placeBlock(Point2D mousePos) { new Block(mousePos); }

    /**
     * Calls method reload from PlayerComponent's selected weapon.
     */
    public void reload() {
        weaponArrayList.get(activeWeapon).reload();
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
        jumps = amountOfJumps;
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
}
