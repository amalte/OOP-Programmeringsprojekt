package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Erik Wetter
 * <p>
 * Test class for AnimationComponent.
 */
public class TestAnimationComponent {

    private Entity entity;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        initialize();
    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    @Test
    public void testSetAnimationChannel() throws InterruptedException {
        waitForRunLater(() -> {
            entity = spawn("player", 0, 0);
        });
        AnimationComponent animation = entity.getComponent(AnimationComponent.class);
        assertEquals(animation.getAnimIdle(), animation.getCurrentAnimationChannel());

        animation.jump();
        animation.onUpdate(0);
        assertEquals(animation.getAnimJump(), animation.getCurrentAnimationChannel());

        animation.landed();
        animation.moveLeft();
        animation.onUpdate(0);
        assertEquals(animation.getAnimWalk(), animation.getCurrentAnimationChannel());

    }

    @Test
    public void testMoveleft() throws InterruptedException {
        waitForRunLater(() -> {
            entity = spawn("player", 0, 0);
        });
        entity.getComponent(AnimationComponent.class).moveLeft();
        assertEquals(100, entity.getComponent(AnimationComponent.class).getTimer());
        assertEquals(entity.getScaleX(), -1);
    }

    @Test
    public void testMoveRight() throws InterruptedException {
        waitForRunLater(() -> {
            entity = spawn("player", 0, 0);
        });
        entity.getComponent(AnimationComponent.class).moveRight();
        assertEquals(100, entity.getComponent(AnimationComponent.class).getTimer());
        assertEquals(entity.getScaleX(), 1);
    }
}
