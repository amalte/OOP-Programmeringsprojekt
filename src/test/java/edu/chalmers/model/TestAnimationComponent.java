package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnimationComponent {

    private Entity entity;

    @BeforeClass
    public static void setUp() throws InterruptedException {
        initialize();
    }

    @Test
    public void testSetAnimationChannel() throws InterruptedException {
        waitForRunLater(() -> {
            entity = spawn("player",0,0);
            AnimationComponent animation = entity.getComponent(AnimationComponent.class);
            assertEquals(animation.getCurrentAnimationChannel(), animation.getAnimIdle());

            animation.jump();
            animation.onUpdate(0);
            assertEquals(animation.getCurrentAnimationChannel(), animation.getAnimJump());

            animation.landed();
            animation.moveLeft();
            animation.onUpdate(0);
            assertEquals(animation.getCurrentAnimationChannel(), animation.getAnimWalk());
        });
    }

    @Test
    public void testMoveleft() throws InterruptedException {
        waitForRunLater(() -> {
            entity = spawn("player",0,0);
            entity.getComponent(AnimationComponent.class).moveLeft();
            assertEquals(entity.getComponent(AnimationComponent.class).getTimer(), 100);
            assertEquals(entity.getScaleX(), -1);
        });
    }

    @Test
    public void testMoveRight() throws InterruptedException {
        waitForRunLater(() -> {
            entity = spawn("player",0,0);
            entity.getComponent(AnimationComponent.class).moveRight();
            assertEquals(entity.getComponent(AnimationComponent.class).getTimer(), 100);
            assertEquals(entity.getScaleX(), 1);
        });
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }
}
