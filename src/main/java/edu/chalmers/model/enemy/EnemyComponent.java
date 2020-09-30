package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import edu.chalmers.model.enemy.enemytypes.IEnemyType;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * EnemyComponent class. When added to entity it becomes an Enemy.
 */
public class EnemyComponent extends Component {

    IEnemyType enemyType;
    private PhysicsComponent physics;
    private final int amountOfJumps = 1;
    private int jumps = 0;

    // STATS
    private Color color;
    private int health;
    private int damage;
    private int moveSpeed;
    private int jumpHeight;

    public EnemyComponent(IEnemyType enemyType) {
        this.enemyType = enemyType;

        physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        this.color = enemyType.getColor();
        this.health = enemyType.getHealth();
        this.damage = enemyType.getDamage();
        this.moveSpeed = enemyType.getMoveSpeed();
        this.jumpHeight = enemyType.getJumpHeight();
    }

    @Override
    public void onUpdate(double tpf) {
        checkHealth();
    }

    /**
     * Method moves enemy Entity left (negative x).
     */
    public void moveLeft(){
        physics.setVelocityX(-moveSpeed);
    }

    /**
     * Method moves enemy Entity right (positive x).
     */
    public void moveRight(){
        physics.setVelocityX(moveSpeed);
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
        jumps = amountOfJumps;
    }

    /**
     * Lower Enemy's health with damage.
     * @param damage The amount of incoming damage.
     */
    public void inflictDamage(int damage){
        health -= damage;
    }

    /**
     * Kills Enemy if its health becomes 0 or lower.
     */
    private void checkHealth() {
        if(health <= 0) {
            die();
        }
    }

    /**
     * Gets the enemy entity's X-position.
     * @return Enemy X-position.
    */
    public double getX() {
        return entity.getX();
    }

    /**
     * Gets the enemy entity's mid X-position.
     * @return Enemy middle X-position.
     */
    public double getMiddleX() {
        return entity.getX() + (entity.getWidth() / 2);
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
     * Gets the enemy entity's mid Y-position.
     * @return Enemy middle Y-position.
     */
    public double getMiddleY() {
        return entity.getY() + (entity.getHeight() / 2);
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
     * Getter for the variable color.
     * @return The Color of entity.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for variable damage.
     * @return The amount of damage the enemy can inflict on an other Entity.
     */
    public int getDamage(){
        return damage;
    }

    /**
     * Getter for variable health.
     * @return The health of the Enemy.
     */
    public int getHealth(){
        return health;
    }
}
