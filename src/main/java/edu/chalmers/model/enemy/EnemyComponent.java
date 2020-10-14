package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import edu.chalmers.model.AnimationComponent;
import edu.chalmers.model.enemy.enemytypes.IEnemyType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * EnemyComponent class. When added to entity it becomes an Enemy.
 */
public class EnemyComponent extends Component {

    private IEnemyType enemyType;
    private PhysicsComponent physics;
    private final int AMOUNT_OF_JUMPS = 1;
    private int jumps = 0;
    private boolean onGround = false;
    private boolean isAirborne = false;

    // STATS
    private int health;
    private int damage;
    private int blockDamage;
    private int moveSpeed;
    private int jumpHeight;

    public EnemyComponent(IEnemyType enemyType, StatMultiplier statMultiplier) {
        this.enemyType = enemyType;

        physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        this.health = (int) Math.round(enemyType.getHealth() * statMultiplier.getHealthMultiplier());
        this.damage = (int) Math.round(enemyType.getDamage() * statMultiplier.getDmgMultiplier());
        this.blockDamage = enemyType.getBlockDamage();
        this.moveSpeed = (int) Math.round(enemyType.getMoveSpeed() * statMultiplier.getSpeedMultiplier());
        this.jumpHeight = (int) Math.round(enemyType.getJumpHeight() * statMultiplier.getJmpHeightMultiplier());
    }

    /**
     * Method moves enemy Entity left (negative x).
     */
    public void moveLeft(){
        physics.setVelocityX(-moveSpeed);
        if(entity.hasComponent(AnimationComponent.class)) {
            entity.getComponent(AnimationComponent.class).moveLeft();
        }
    }

    /**
     * Method moves enemy Entity right (positive x).
     */
    public void moveRight(){
        physics.setVelocityX(moveSpeed);
        if(entity.hasComponent(AnimationComponent.class)) {
            entity.getComponent(AnimationComponent.class).moveRight();
        }
    }

    /**
     * Method moves enemy Entity up (negative y).
     */
    public void jump(){
        if(jumps != 0) {
            physics.setVelocityY(-jumpHeight);
            jumps--;
        }
    }

    /**
     * Method stop enemy Entity in the x direction.
     */
    public void stop(){
        physics.setVelocityX(0);
    }

    /**
     * Method kills the Entity and removes it from the game world.
     */
    public void die() {
        getGameWorld().removeEntity(entity);
    }

    /**
     * Resets enemy's jumps to be equal to amountOfJumps variable.
     */
    public void resetJumpAmounts(){
        jumps = AMOUNT_OF_JUMPS;
    }

    /**
     * Lower Enemy's health with damage.
     * @param damage The amount of incoming damage.
     */
    public void inflictDamage(int damage){
        health -= damage;
        checkHealth();
    }

    /**
     * Kills Enemy if its health becomes 0 or lower.
     */
    private void checkHealth() {
        if(health <= 0) {
            die();
        }
    }

    // ---------- GETTERS ---------- //

    /**
     * Gets the enemy entity's X-position.
     * @return Enemy X-position.
    */
    public double getX() {
        return entity.getX();
    }

    /**
     * Gets the enemy entity's right side X-position.
     * @return Enemy right X-position.
    */
    public double getRightX() {
        return entity.getRightX();
    }

    /**
     * Gets the enemy entity's Y-position.
     * @return Enemy Y-position.
    */
    public double getY() {
        return entity.getY();
    }

    /**
     * Gets the enemy entity's bottom side Y-position.
     * @return Enemy bottom Y-position.
    */
    public double getBottomY() {
        return entity.getBottomY();
    }

    /**
     * Gets the PhysicsComponent attached to this Component.
     * @return
     */
    public PhysicsComponent getPhysics() {
        return physics;
    }

    /**
     * Getter for the variable enemyType.
     * @return The type of Enemy.
     */
    public IEnemyType getEnemyType() {
        return enemyType;
    }

    /**
     * Getter for variable damage.
     * @return The amount of damage the enemy can inflict on an other Entity.
     */
    public int getDamage(){
        return damage;
    }

    /**
     * Getter for variable blockDamage.
     * @return The amount of damage the enemy can inflict on Block.
     */
    public int getBlockDamage() {
        return blockDamage;
    }

    /**
     * Getter for variable health.
     * @return The health of the Enemy.
     */
    public int getHealth(){
        return health;
    }

    /**
     * Getter for variable moveSpeed.
     * @return The move speed of the Enemy.
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Getter for variable jumpHeight.
     * @return The jump height of the Enemy.
     */
    public int getJumpHeight() {
        return jumpHeight;
    }

    /**
     * Getter for variable jumps.
     * @return The amount og jumps left.
     */
    public int getJumps() {
        return jumps;
    }

    /**
     * Getter for onGround variable.
     * @return True (Enemy is on the ground *or* has most recently not touched a platform) or False (Enemy has not touched the ground since touching a platform).
     */
    public boolean isOnGround() {
        return onGround;
    }

    /**
     * Getter for isAirborne variable.
     * @return True (Enemy is in the air) or False (Enemy is on platform or ground).
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

    /**
     * Sets the multiplier for Enemy's health.
     * @param healthMultiplier Health multiplier.
     */
    public void setHealthMultiplier(double healthMultiplier) {
        health = (int) Math.round(enemyType.getHealth() * healthMultiplier);
    }

    /**
     * Sets the multiplier for Enemy's damage.
     * @param damageMultiplier Damage multiplier.
     */
    public void setDamageMultiplier(double damageMultiplier) {
        damage = (int) Math.round(enemyType.getDamage() * damageMultiplier);
    }

    /**
     * Sets the multiplier for Enemy's move speed.
     * @param moveSpeedMultiplier Move speed multiplier.
     */
    public void setMoveSpeedMultiplier(double moveSpeedMultiplier) {
        moveSpeed = (int) Math.round(enemyType.getMoveSpeed() * moveSpeedMultiplier);
    }

    /**
     * Sets the multiplier for Enemy's jump height.
     * @param jumpHeightMultiplier Jump height multiplier.
     */
    public void setJumpHeightMultiplier(double jumpHeightMultiplier) {
        jumpHeight = (int) Math.round(enemyType.getJumpHeight() * jumpHeightMultiplier);
    }
}
