package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.EntityType;
import edu.chalmers.model.PlayerComponent;
import edu.chalmers.model.enemy.enemytypes.Zombie;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sam Salek
 * <p>
 * Test class for EnemyComponent.
 */
public class TestEnemyComponent {

    private Entity enemy;
    private PlayerComponent player;
    private EnemyComponent enemyComponent;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    // Initializes the Player, Enemy and its EnemyComponent.
    private void init() throws InterruptedException {
        waitForRunLater(() -> {
            FXGLTest.clearAllEntities();

            player = spawn("player", 0, 0).getComponent(PlayerComponent.class);
            enemy = EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, 0, player.getEntity(), new StatMultiplier());
            enemyComponent = enemy.getComponent(EnemyComponent.class);
        });
    }

    @Test
    public void testMoveLeft() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            enemyComponent.moveLeft();
            assertEquals(-enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));
        });
    }

    @Test
    public void testMoveRight() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            enemyComponent.moveRight();
            assertEquals(enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));
        });

    }

    @Test
    public void testJump() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            enemyComponent.resetJumpAmounts();      // Reset jumpAmounts as enemies start with 0 jumpAmounts until they collide with the ground.
            enemyComponent.jump();
            assertEquals(-enemyComponent.getJumpHeight(), Math.round(enemyComponent.getPhysics().getVelocityY()));
        });
    }

    @Test
    public void testResetJumpAmounts() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            enemyComponent.jump();
            assertEquals(0, enemyComponent.getJumps());

            enemyComponent.resetJumpAmounts();
            assertEquals(1, enemyComponent.getJumps());
        });
    }

    @Test
    public void testStop() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Start movement so enemies can eventually stop.
            enemyComponent.moveRight();
            assertEquals(enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));

            enemyComponent.stop();
            assertEquals(0, enemyComponent.getPhysics().getVelocityX());
        });
    }

    @Test
    public void testInflictDamage() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            int startHealth = enemyComponent.getHealth();
            enemyComponent.inflictDamage(10);
            assertEquals(enemyComponent.getHealth(), (startHealth - 10));   // Start health - inflicted health.
        });
    }

    @Test
    public void testCheckHealth() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            enemyComponent.inflictDamage(enemyComponent.getHealth());        // Inflict the enemy's health as damage (health then = 0).
            assertEquals(0, getGameWorld().getEntitiesByType(EntityType.ENEMY).size());     // Enemy should have died and no Enemy entities in the world should exist.
        });
    }

    // ---------- GETTERS ---------- //

    @Test
    public void testGetX() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            assertEquals(0, enemyComponent.getX());     // Enemy is initialized with X-pos 0

            enemy.setX(10);
            assertEquals(10, enemyComponent.getX());
        });
    }

    @Test
    public void testGetRightX() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            assertEquals((0 + enemy.getWidth()), enemyComponent.getRightX());     // Enemy is initialized with X-pos 0

            enemy.setX(10);
            assertEquals((10 + enemy.getWidth()), enemyComponent.getRightX());
        });
    }

    @Test
    public void testGetY() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            assertEquals(0, enemyComponent.getY());     // Enemy is initialized with Y-pos 0

            enemy.setY(10);
            assertEquals(10, enemyComponent.getY());
        });
    }

    @Test
    public void testGetBottomY() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            assertEquals((0 + enemy.getHeight()), enemyComponent.getBottomY());     // Enemy is initialized with Y-pos 0.

            enemy.setY(10);
            assertEquals((10 + enemy.getHeight()), enemyComponent.getBottomY());
        });
    }

    @Test
    public void testGetPhysics() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is created with same color as its enemy type (zombie).
            assertEquals(PhysicsComponent.class, enemyComponent.getPhysics().getClass());
        });
    }

    @Test
    public void testGetEnemyType() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is created as a zombie.
            assertEquals(Zombie.class, enemyComponent.getEnemyType().getClass());
        });
    }

    @Test
    public void testGetDamage() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is created with same damage as its enemy type (zombie).
            assertEquals(new Zombie().getDamage(), enemyComponent.getDamage());
        });
    }

    @Test
    public void testGetBlockDamage() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is created with same block damage as its enemy type (zombie).
            assertEquals(new Zombie().getBlockDamage(), enemyComponent.getBlockDamage());
        });
    }

    @Test
    public void testGetHealth() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is created with same health as its enemy type (zombie).
            assertEquals(new Zombie().getHealth(), enemyComponent.getHealth());
        });
    }

    @Test
    public void testGetMoveSpeed() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is created with same move speed as its enemy type (zombie).
            assertEquals(new Zombie().getMoveSpeed(), enemyComponent.getMoveSpeed());
        });
    }

    @Test
    public void testGetJumpHeight() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is created with same jump height as its enemy type (zombie).
            assertEquals(new Zombie().getJumpHeight(), enemyComponent.getJumpHeight());
        });
    }

    @Test
    public void testGetJumps() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Enemy is spawned with 0 jumps available (until collision with the ground).
            assertEquals(0, enemyComponent.getJumps());

            enemyComponent.resetJumpAmounts();
            assertEquals(1, enemyComponent.getJumps());
        });
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

    @Test
    public void testSetMoveSpeedMultiplier() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            int startMoveSpeed = enemyComponent.getMoveSpeed();
            enemyComponent.setMoveSpeedMultiplier(2);
            assertEquals((startMoveSpeed * 2), enemyComponent.getMoveSpeed());
        });
    }

    @Test
    public void testSetJumpHeightMultiplier() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            int startJumpHeight = enemyComponent.getJumpHeight();
            enemyComponent.setJumpHeightMultiplier(2);
            assertEquals((startJumpHeight * 2), enemyComponent.getJumpHeight());
        });
    }
}

