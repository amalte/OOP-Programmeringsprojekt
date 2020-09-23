package edu.chalmers.model;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

/**
 * Player class. Wraps an entity object as a Player.
 */
public class PlayerComponent extends Component {

    private Weapon weapon = new Weapon();

    //Stats
    private int health = 100;
    private int moveSpeed = 150;
    private int jumpHeight = 350;
    private final int amountOfJumps = 1;
    private int jumps = amountOfJumps;
    private PhysicsComponent physics;

    public PlayerComponent(PhysicsComponent physics) {
        this.physics = physics;
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
    }

    /**
     * Get method used fot testing purposes.
     * @return integer jumps.
     */
    public int getJumps() {
        return jumps;
    }

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
        return weapon;
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
        weapon.shoot(entity.getX(), entity.getY());
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
     * Lower PlayerComponents health with damage.
     * @param damage amount of health points to be inflicted to player.
     */
    public void inflictDamage(int damage){
        health -= damage;
    }

}
