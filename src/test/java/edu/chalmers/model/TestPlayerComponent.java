package edu.chalmers.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static org.junit.jupiter.api.Assertions.fail;

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

    @Test
    public void testMoveLeft() {
        player = spawn("player",0,0).getComponent(PlayerComponent.class);
        player.moveLeft();
        assertEquals(- player.getMoveSpeed(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testMoveRight() {
        player = spawn("player",0,0).getComponent(PlayerComponent.class);
        player.moveRight();
        assertEquals(player.getMoveSpeed(), (int) player.getEntity().getComponent(PhysicsComponent.class).getVelocityX());
    }

    @Test
    public void testJump(){
        PlayerComponent playerComponent = spawn("player",0,0).getComponent(PlayerComponent.class);
        playerComponent.jump();
        assertEquals(0, playerComponent.getJumps());

        playerComponent.resetJumpAmounts();
        assertEquals(1, playerComponent.getJumps());
    }

    @Test
    public void jump(){
      fail("No test written!");
    }

    @Test
    public void stop(){
        fail("No test written!");
    }

    @Test
    public void resetJumpAmounts(){
        fail("No test written!");
    }

    @Test
    public void inflictDamage(){
        fail("No test written!");
    }

    @Test
    public void testShoot() {
        PlayerComponent playerComponent = spawn("player").getComponent(PlayerComponent.class);
        playerComponent.shoot();
        assertEquals(1, getGameWorld().getEntitiesByType(EntityType.PROJECTILE).size());
        //assertEquals(playerComponent.getEntity().getX(), getGameWorld().getEntitiesByType(EntityType.PROJECTILE).get(0).getX());      // check if projectile spawned at right position
    }
}
