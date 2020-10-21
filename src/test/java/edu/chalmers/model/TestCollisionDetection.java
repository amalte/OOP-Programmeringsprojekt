package edu.chalmers.model;

import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.enemy.StatMultiplier;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oscar Arvidson
 *
 * Test class for CollisionDetection.
 */
public class TestCollisionDetection {

    private EnemyFactory enemyFactory = EnemyFactory.getInstance();
    private PlayerComponent p;
    private EnemyComponent enemy;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    @Test
    public void testCollisionHandler() throws InterruptedException {
        /*waitForRunLater(() -> {
            FXGLTest.clearAllEntities();
            CollisionDetection collision = new CollisionDetection(new PlayerComponent(new PhysicsComponent()));
            p = spawn("player", 0, 0).getComponent(PlayerComponent.class);
            p.setTesting(true);
        });
        p.setTesting(true);
        p.jump();
        assertEquals(0, p.getJumps());
        /*Entity platform = spawn("platform",0,0);
        assertTrue(p.getEntity().isColliding(platform));
        assertTrue(p.isOnGround());
        assertEquals(1, p.getJumps());


        assertEquals(p.getMaxHealth(), p.getHealth());
        waitForRunLater(() -> {
            enemy = enemyFactory.createEnemy("Zombie", 0, 0, p.getEntity(), new StatMultiplier()).getComponent(EnemyComponent.class);
        });
        assertTrue(p.getEntity().isColliding(enemy.getEntity()));
        assertEquals(1, p.getJumps());
        assertEquals(p.getMaxHealth()-enemy.getDamage(), p.getHealth());
         */
    }
}