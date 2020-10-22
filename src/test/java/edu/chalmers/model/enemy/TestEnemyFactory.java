package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.Entity;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sam Salek
 *
 * Test class for EnemyFactory.
 */
public class TestEnemyFactory {

    Entity tempPlayer;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    private void init() throws InterruptedException {
        waitForRunLater(() -> {
            tempPlayer = spawn("player", 0, 0);
        });
    }

    @Test
    public void testCreateZombie() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity zombie1 = EnemyFactory.getInstance().createEnemy("zombie", 0, 0, tempPlayer, new StatMultiplier());
            Entity zombie2 = EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, 0, tempPlayer, new StatMultiplier());
            Entity zombie3 = EnemyFactory.getInstance().createEnemy("ZoMbIe", 0, 0, tempPlayer, new StatMultiplier());

            assertEquals("Zombie", zombie1.getComponent(EnemyComponent.class).getEnemyType().getName());
            assertEquals("Zombie", zombie2.getComponent(EnemyComponent.class).getEnemyType().getName());
            assertEquals("Zombie", zombie3.getComponent(EnemyComponent.class).getEnemyType().getName());
        });
    }

    @Test
    public void testCreateRex() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity rex1 = EnemyFactory.getInstance().createEnemy("rex", 0, 0, tempPlayer, new StatMultiplier());
            Entity rex2 = EnemyFactory.getInstance().createEnemy("REX", 0, 0, tempPlayer, new StatMultiplier());
            Entity rex3 = EnemyFactory.getInstance().createEnemy("ReX", 0, 0, tempPlayer, new StatMultiplier());

            assertEquals("Rex", rex1.getComponent(EnemyComponent.class).getEnemyType().getName());
            assertEquals("Rex", rex2.getComponent(EnemyComponent.class).getEnemyType().getName());
            assertEquals("Rex", rex3.getComponent(EnemyComponent.class).getEnemyType().getName());
        });
    }

    @Test
    public void testCreateBlob() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity blob1 = EnemyFactory.getInstance().createEnemy("blob", 0, 0, tempPlayer, new StatMultiplier());
            Entity blob2 = EnemyFactory.getInstance().createEnemy("BLOB", 0, 0, tempPlayer, new StatMultiplier());
            Entity blob3 = EnemyFactory.getInstance().createEnemy("BlOb", 0, 0, tempPlayer, new StatMultiplier());

            assertEquals("Blob", blob1.getComponent(EnemyComponent.class).getEnemyType().getName());
            assertEquals("Blob", blob2.getComponent(EnemyComponent.class).getEnemyType().getName());
            assertEquals("Blob", blob3.getComponent(EnemyComponent.class).getEnemyType().getName());
        });
    }

    @Test
    public void testCreateNull() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity _null = EnemyFactory.getInstance().createEnemy("", 0, 0, tempPlayer, new StatMultiplier());
            Entity _null2 = EnemyFactory.getInstance().createEnemy("   ", 0, 0, tempPlayer, new StatMultiplier());
            Entity _null3 = EnemyFactory.getInstance().createEnemy("Hej", 0, 0, tempPlayer, new StatMultiplier());
            Entity _null4 = EnemyFactory.getInstance().createEnemy(null, 0, 0, tempPlayer, new StatMultiplier());

            assertEquals(null, _null);
            assertEquals(null, _null2);
            assertEquals(null, _null3);
            assertEquals(null, _null4);
        });
    }
}

