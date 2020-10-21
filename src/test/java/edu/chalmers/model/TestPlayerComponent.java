package edu.chalmers.model;

import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.FXGLTest;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Oscar Arvidson
 *
 * Test class for PlayerComponent.
 */
public class TestPlayerComponent {

    private PlayerComponent player;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    private void resetPlayer() throws InterruptedException {
        waitForRunLater(() -> {
            FXGLTest.clearAllEntities();
            player = spawn("player", 50, 0).getComponent(PlayerComponent.class);
            player.setTesting(true);
        });
    }

    @Test
    public void testMoveLeft() throws InterruptedException {
        resetPlayer(); //Reset all player stats
        player.moveLeft();
        assertEquals(- player.getMoveSpeed(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testMoveRight() throws InterruptedException {
        resetPlayer();
        player.moveRight();
        assertEquals(player.getMoveSpeed(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testJump() throws InterruptedException {
        resetPlayer();
        player.jump();
        assertEquals(-player.getJumpHeight(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityY());
    }


    @Test
    public void testStop() throws InterruptedException {
        resetPlayer();
        player.moveLeft();
        assertEquals(-player.getMoveSpeed(), (int)player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());

        player.stop();
        assertEquals(0, (int)player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testResetJumpAmounts() throws InterruptedException {
        resetPlayer();
        player.jump();
        assertEquals(0, player.getJumps());

        player.resetJumpAmounts();
        assertEquals(1, player.getJumps());
    }

    @Test
    public void testInflictDamage() throws InterruptedException {
        resetPlayer();
        player.inflictDamage(10);
        assertEquals(player.getMaxHealth() - 10, player.getHealth());
    }

    @Test
    public void testShoot() throws InterruptedException {
        resetPlayer();
        waitForRunLater(() -> {
            player.shoot();
        });
        assertEquals(1, getGameWorld().getEntitiesByType(EntityType.PROJECTILE).size());
    }

    @Test
    public void testSetActiveWeapon() throws InterruptedException {
        resetPlayer();
        player.setActiveWeapon(0);
        assertTrue(player.getWeapons().get(0) == player.getActiveWeapon());
        player.setActiveWeapon(1);
        assertTrue(player.getWeapons().get(1) == player.getActiveWeapon());
        player.setActiveWeapon(2);
        assertTrue(player.getWeapons().get(2) == player.getActiveWeapon());
    }

    @Test
    public void testSetIsOnGround() throws InterruptedException {
        resetPlayer();
        player.setAirborne(true);
        assertTrue(player.isAirborne());
        player.setAirborne(false);
        assertTrue(!player.isAirborne());
    }

    @Test
    public void testSetOnGround() throws InterruptedException {
        resetPlayer();
        player.setOnGround(true);
        assertTrue(player.isOnGround());
        player.setOnGround(false);
        assertTrue(!player.isOnGround());
    }

    @Test
    public void testReload() throws InterruptedException {
        resetPlayer();
        player.getActiveWeapon().setTesting(true);
        waitForRunLater(() -> {
            player.shoot();
        });
        int temp = player.getActiveWeapon().getMagazineCounter();
        player.reload();
        assertEquals(player.getActiveWeapon().getMagazineSize(),player.getActiveWeapon().getMagazineCounter());
    }

    @Test
    public void testObserverMethods() throws InterruptedException {
        resetPlayer();
        MockObserver o = new MockObserver();
        assertTrue(player.observers.size() == 0);
        player.addObserver(o);
        assertTrue(player.observers.size() == 1);
        player.notifyObserver();
        assertTrue(o.isTest());
        player.removeObserver(o);
        assertTrue(player.observers.size() == 0);
    }

    @Test
    public void testLanded() throws InterruptedException {
        resetPlayer();
        int temp = player.getJumps();
        player.jump();
        assertEquals(temp-1, player.getJumps());
        assertTrue(player.getEntity().getComponent(AnimationComponent.class).isAirborne());
        player.landed();
        assertTrue(!player.getEntity().getComponent(AnimationComponent.class).isAirborne());
        assertEquals(temp, player.getJumps());
    }
}
