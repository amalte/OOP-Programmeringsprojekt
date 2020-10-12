package edu.chalmers.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.*;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.enemy.StatMultiplier;
import edu.chalmers.model.enemy.enemytypes.Zombie;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import com.almasb.fxgl.test.RunWithFX;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RunWithFX.class)
public class TestCollisionDetection {

    private EnemyFactory enemyFactory = EnemyFactory.getInstance();

    @BeforeAll
    public static void initApplication() throws InterruptedException {
        SetupWorld.initApp();
    }

    @Test
    public void testInitCollisionHandler() {
        CollisionDetection collision = new CollisionDetection(new PlayerComponent(new PhysicsComponent()));

        PlayerComponent p = spawn("player", 0 ,50).getComponent(PlayerComponent.class);
        p.setTesting(true);
        p.jump();
        assertEquals(0, p.getJumps());
        Entity platform = FXGL.entityBuilder().type(EntityType.PLATFORM).at(0,0).bbox(new HitBox(BoundingShape.box(50, 50))).with(new CollidableComponent(true)).with(new PhysicsComponent()).build();
        assertTrue(p.getEntity().isColliding(platform));

        fail("No finished test written!");
        /* UNIMPLEMENTED TESTS
        assertTrue(p.isOnGround());
        assertEquals(1, p.getJumps());
        EnemyComponent e = enemyFactory.createEnemy("zombie", 60,60, p.getEntity(), new StatMultiplier()).getComponent(EnemyComponent.class);
        assertTrue(p.getHealth() == p.getHealth()-e.getDamage())
         */
    }
}