package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.model.SetupWorld;
import edu.chalmers.model.enemy.enemytypes.Zombie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestEnemyComponent {

    private Entity enemy;
    private PlayerComponent player;
    private EnemyComponent enemyComponent;

    @BeforeAll
    public static void initApplication() throws InterruptedException {
        SetupWorld.initApp();
    }

    // Initializes the Player, Enemy and its EnemyComponent.
    private void init() {
        player = spawn("player",0,0).getComponent(PlayerComponent.class);
        enemy = EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, 0, player.getEntity(), new StatMultiplier());
        enemyComponent = enemy.getComponent(EnemyComponent.class);
    }

    // Removes all entities in the world
    private void clearAllEntities() {
        // A separate list for entities was used to avoid ConcurrentModification exception.
        List entities = new ArrayList<Entity>();        // List with all entities.

        // Add each and every entity from the game world to the list.
        for(Entity e : getGameWorld().getEntities()) {
            entities.add(e);
        }
        getGameWorld().removeEntities(entities);     // Remove all existing entities from the world.
    }

    @Test
    public void testMoveLeft() {
        init();

        enemyComponent.moveLeft();
        assertEquals(-enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));
    }

    @Test
    public void testMoveRight() {
        init();

        enemyComponent.moveRight();
        assertEquals(enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));
    }

    @Test
    public void testJump(){
        init();

        enemyComponent.resetJumpAmounts();      // Reset jumpAmounts as enemies start with 0 jumpAmounts until they collide with the ground.
        enemyComponent.jump();
        assertEquals(-enemyComponent.getJumpHeight(), Math.round(enemyComponent.getPhysics().getVelocityY()));
    }

    @Test
    public void testResetJumpAmounts(){
        init();

        enemyComponent.jump();
        assertEquals(0, enemyComponent.getJumps());

        enemyComponent.resetJumpAmounts();
        assertEquals(1, enemyComponent.getJumps());
    }

    @Test
    public void testStop(){
        init();

        // Start movement so enemies can eventually stop.
        enemyComponent.moveRight();
        assertEquals(enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));

        enemyComponent.stop();
        assertEquals(0, enemyComponent.getPhysics().getVelocityX());
    }

    @Test
    public void testInflictDamage(){
        init();

        int startHealth = enemyComponent.getHealth();
        enemyComponent.inflictDamage(10);
        assertEquals(enemyComponent.getHealth(), (startHealth - 10));   // Start health - inflicted health.
    }

    @Test
    public void testCheckHealth(){
        clearAllEntities();
        init();

        enemyComponent.inflictDamage(enemyComponent.getHealth());        // Inflict the players health as damage (health then = 0).
        assertEquals(0, getGameWorld().getEntitiesByType(EntityType.ENEMY).size());     // Enemy should have died and no Enemy entities in the world should exist.
    }

    // ---------- GETTERS ---------- //

    @Test
    public void testGetX() {
        init();
        assertEquals(0, enemyComponent.getX());     // Enemy is initialized with X-pos 0

        enemy.setX(10);
        assertEquals(10, enemyComponent.getX());
    }

    @Test
    public void testGetRightX() {
        init();
        assertEquals((0 + enemy.getWidth()), enemyComponent.getRightX());     // Enemy is initialized with X-pos 0

        enemy.setX(10);
        assertEquals((10 + enemy.getWidth()), enemyComponent.getRightX());
    }

    @Test
    public void testGetY() {
        init();
        assertEquals(0, enemyComponent.getY());     // Enemy is initialized with Y-pos 0

        enemy.setY(10);
        assertEquals(10, enemyComponent.getY());
    }

    @Test
    public void testGetBottomY() {
        init();
        assertEquals((0 + enemy.getHeight()), enemyComponent.getBottomY());     // Enemy is initialized with Y-pos 0.

        enemy.setY(10);
        assertEquals((10 + enemy.getHeight()), enemyComponent.getBottomY());
    }

    @Test
    public void testGetPhysics() {
        init();

        // Enemy is created with same color as its enemy type (zombie).
        assertEquals(PhysicsComponent.class, enemyComponent.getPhysics().getClass());
    }

    @Test
    public void testGetEnemyType() {
        init();

        // Enemy is created as a zombie.
        assertEquals(Zombie.class, enemyComponent.getEnemyType().getClass());
    }

    @Test
    public void testGetColor() {
        init();

        // Enemy is created with same color as its enemy type (zombie).
        assertEquals(new Zombie().getColor(), enemyComponent.getColor());
    }

    @Test
    public void testGetDamage(){
        init();

        // Enemy is created with same damage as its enemy type (zombie).
        assertEquals(new Zombie().getDamage(), enemyComponent.getDamage());
    }

    @Test
    public void testGetBlockDamage() {
        init();

        // Enemy is created with same block damage as its enemy type (zombie).
        assertEquals(new Zombie().getBlockDamage(), enemyComponent.getBlockDamage());
    }

    @Test
    public void testGetHealth(){
        init();

        // Enemy is created with same health as its enemy type (zombie).
        assertEquals(new Zombie().getHealth(), enemyComponent.getHealth());
    }

    @Test
    public void testGetMoveSpeed() {
        init();

        // Enemy is created with same move speed as its enemy type (zombie).
        assertEquals(new Zombie().getMoveSpeed(), enemyComponent.getMoveSpeed());
    }

    @Test
    public void testGetJumpHeight() {
        init();

        // Enemy is created with same jump height as its enemy type (zombie).
        assertEquals(new Zombie().getJumpHeight(), enemyComponent.getJumpHeight());
    }

    @Test
    public void testGetJumps() {
        init();

        // Enemy is spawned with 0 jumps available (until collision with the ground).
        assertEquals(0, enemyComponent.getJumps());

        enemyComponent.resetJumpAmounts();
        assertEquals(1, enemyComponent.getJumps());
    }

    // How to test these?
    /*
    @Test
    public void testIsOnGround() {
        clearAllEntities();
        init();

        SpawnData spawnData = new SpawnData(0 ,0);
        spawnData.put("width", 60);
        spawnData.put("height", 60);
        Entity platform = spawn("platform", spawnData);

        assertEquals(false, enemyComponent.isOnGround());

        enemy.setX(-enemy.getWidth() + 5);
        enemy.setY(-enemy.getHeight() + 5);

        assertEquals(true, enemyComponent.isOnGround());
    }

    @Test
    public void testIsAirborne() {
        clearAllEntities();
        init();

        Entity platform = spawn("platform", 0, 0);
        enemy.setX(-enemy.getWidth() + 1);
        enemy.setY(-enemy.getHeight() + 1);

        assertEquals(false, enemyComponent.isAirborne());

        enemyComponent.resetJumpAmounts();
        enemyComponent.jump();

        assertEquals(true, enemyComponent.isAirborne());
    }
    */


    // -------- SETTERS -------- //

    // Should these be tested?
    /*
    @Test
    public void testSetOnGround() {
        init();


        //assertEquals();
    }

    @Test
    public void testSetAirborne() {
        init();

        assertEquals();
    }
    */

    // Methods not used.
    /*
    @Test
    public void testSetHealthMultiplier() {
        init();

        assertEquals();
    }

    @Test
    public void testSetDamageMultiplier() {
        init();

        assertEquals();
    }
    */
    
    @Test
    public void testSetMoveSpeedMultiplier() {
        init();

        int startMoveSpeed = enemyComponent.getMoveSpeed();
        enemyComponent.setMoveSpeedMultiplier(2);
        assertEquals((startMoveSpeed * 2), enemyComponent.getMoveSpeed());
    }

    @Test
    public void testSetJumpHeightMultiplier() {
        init();

        int startJumpHeight = enemyComponent.getJumpHeight();
        enemyComponent.setJumpHeightMultiplier(2);
        assertEquals((startJumpHeight * 2), enemyComponent.getJumpHeight());
    }
}

