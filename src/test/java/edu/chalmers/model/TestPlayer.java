package edu.chalmers.model;

import com.almasb.fxgl.app.GameApplication;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.main.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import com.almasb.fxgl.test.RunWithFX;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RunWithFX.class)
public class TestPlayer{

    @BeforeAll
    public static void initApplication() throws InterruptedException {
        SetupWorld.initApp();
    }

    @Test
    public void testJump(){
        PlayerComponent playerComponent = spawn("player").getComponent(PlayerComponent.class);
        playerComponent.jump();
        assertEquals(0, playerComponent.getJumps());

        playerComponent.resetJumpAmounts();
        assertEquals(1, playerComponent.getJumps());
    }

    @Test
    public void testShoot() {
        PlayerComponent playerComponent = spawn("player").getComponent(PlayerComponent.class);
        playerComponent.shoot();
        assertEquals(1, getGameWorld().getEntitiesByType(EntityType.PROJECTILE).size());
        //assertEquals(playerComponent.getEntity().getX(), getGameWorld().getEntitiesByType(EntityType.PROJECTILE).get(0).getX());      // check if projectile spawned at right position

    }
}
