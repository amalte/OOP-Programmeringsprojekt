package edu.chalmers.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static org.junit.jupiter.api.Assertions.*;

import com.almasb.fxgl.physics.PhysicsComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import com.almasb.fxgl.test.RunWithFX;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RunWithFX.class)
public class TestPlayerComponent {

    private PlayerComponent player;

    @BeforeAll
    public static void initApplication() throws InterruptedException {
        SetupWorld.initApp();
    }

    private void resetPlayer(){
        //ToDo reset stats instead of object
        player = spawn("player",50,0).getComponent(PlayerComponent.class);
    }

    @Test
    public void testMoveLeft() throws InterruptedException {
        resetPlayer(); //Reset all player stats
        int temp = (int) player.getEntity().getX(); //Players x position in the beginning
        player.moveLeft();

        assertEquals(- player.getMoveSpeed(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testMoveRight() {
        resetPlayer();
        player.moveRight();
        assertEquals(player.getMoveSpeed(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testJump(){
        resetPlayer();
        player.jump();
        assertEquals(-player.getJumpHeight(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityY());
    }


    @Test
    public void testStop(){
        resetPlayer();
        player.moveLeft();
        assertEquals(-player.getMoveSpeed(), (int)player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());

        player.stop();
        assertEquals(0, (int)player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testResetJumpAmounts(){
        resetPlayer();
        player.jump();
        assertEquals(0, player.getJumps());

        player.resetJumpAmounts();
        assertEquals(1, player.getJumps());
    }

    @Test
    public void testInflictDamage() {
        resetPlayer();
        player.setTesting(true);
        int temp = player.getHealth();
        player.inflictDamage(10);
        assertEquals(temp - 10, player.getHealth());
    }

    @Test
    public void testShoot() {
        resetPlayer();
        player.shoot();
        assertEquals(1, getGameWorld().getEntitiesByType(EntityType.PROJECTILE).size());
    }

    @Test
    public void testSetActiveWeapon(){
        resetPlayer();
        player.setActiveWeapon(0);
        assertTrue(player.getWeapons().get(0) == player.getActiveWeapon());
        player.setActiveWeapon(1);
        assertTrue(player.getWeapons().get(1) == player.getActiveWeapon());
        player.setActiveWeapon(2);
        assertTrue(player.getWeapons().get(2) == player.getActiveWeapon());
    }

    @Test
    public void testSetIsOnGround(){
        resetPlayer();
        player.setAirborne(true);
        assertTrue(player.isAirborne());
        player.setAirborne(false);
        assertTrue(!player.isAirborne());
    }

    @Test
    public void testSetOnGround(){
        resetPlayer();
        player.setOnGround(true);
        assertTrue(player.isOnGround());
        player.setOnGround(false);
        assertTrue(!player.isOnGround());
    }
}
