package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestEnemyAIComponent {

    private Entity enemy;
    private EnemyAIComponent enemyAIComponent;
    private Entity tempPlayer;

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

            tempPlayer = spawn("player",10000,10000);
            enemy = EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, 0, tempPlayer, new StatMultiplier());
            enemyAIComponent = enemy.getComponent(EnemyAIComponent.class);

            // Spawn the ground
            SpawnData spawnData = new SpawnData(0 ,0);
            spawnData.put("width", 60 * 20);
            spawnData.put("height", 60);
            spawn("platform", spawnData);

            // +1 so raycast works. Level enemy with platform.
            enemy.setY(-(enemy.getHeight() + 1));
        });

    }

    @Test
    public void testSetPlayerReached() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Player not reached at first
            assertEquals(false, enemyAIComponent.isPlayerReached());

            // Set player position to the right of enemy (+1 so raycast isn't inside player).
            //player.getEntity().setX(enemy.getRightX() + 1);
            //player.getEntity().setY(enemy.getY());

            // Spawn new player as lines above does not work. Why?
            spawn("player", enemy.getRightX() + 1, enemy.getY());
            enemyAIComponent.onUpdate(1);

            assertEquals(true, enemyAIComponent.isPlayerReached());
        });
    }

    @Test
    public void testEnemyAboveOrBelowFix() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Spawn a new enemy above existing enemy. The +1 is for raycast to work
            EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, enemy.getY() - (enemy.getWidth() + 1), tempPlayer, new StatMultiplier());

            // Save starting jump height.
            int startJumpHeight = enemy.getComponent(EnemyComponent.class).getJumpHeight();

            // Set airborne to true to avoid resetting StatMultiplier. Disable jumpAllowed.
            enemy.getComponent(EnemyComponent.class).setAirborne(true);
            enemyAIComponent.getMovementAI().setJumpAllowed(false);

            enemyAIComponent.onUpdate(1);

            assertEquals(startJumpHeight * 2, enemy.getComponent(EnemyComponent.class).getJumpHeight());
        });
    }

    @Test
    public void testGetNearbyEnemyAI() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Spawn a new enemy to the right of existing enemy. The +1 is for raycast to work
            EnemyFactory.getInstance().createEnemy("ZOMBIE", enemy.getRightX() + 1, enemy.getY(), tempPlayer, new StatMultiplier());

            enemyAIComponent.onUpdate(1);

            assertEquals(EnemyAIComponent.class, enemyAIComponent.getNearbyEnemyAI().getClass());
        });
    }

    @Test
    public void testIsEntityToLeft() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position and spawn new player to the right.
            enemy.setX(0);
            enemy.setY(0);
            Entity player = spawn("player", 10, 0);

            assertEquals(false, enemyAIComponent.isEntityToLeft(player));

            // Set player middle X-position to be slightly to the left of Enemy.
            player.setX(-(player.getWidth() / 2 + 1));

            assertEquals(true, enemyAIComponent.isEntityToLeft(player));
        });
    }

    @Test
    public void testIsEntityToRight() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position and spawn new player to the left.
            enemy.setX(0);
            enemy.setY(0);
            Entity player = spawn("player", -10, 0);

            assertEquals(false, enemyAIComponent.isEntityToRight(player));

            // Set player middle X-position to be exactly to the right of Enemy.
            player.setX(enemy.getRightX() + player.getWidth() / 2);

            assertEquals(true, enemyAIComponent.isEntityToRight(player));
        });
    }

    @Test
    public void testIsEntityMiddleYAbove() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position and spawn new player slightly above Enemy (but its middle not above).
            enemy.setX(0);
            enemy.setY(0);
            Entity player = spawn("player", 0, -10);

            assertEquals(false, enemyAIComponent.isEntityMiddleYAbove(player));

            // Set player middle Y-position slightly above Enemy.
            player.setY(-((player.getHeight() / 2) + 1));

            assertEquals(true, enemyAIComponent.isEntityMiddleYAbove(player));
        });
    }

    @Test
    public void testIsEntityBottomYAbove() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position and spawn new player slightly above Enemy (but its bottom Y-pos not above).
            enemy.setX(0);
            enemy.setY(0);
            Entity player = spawn("player", 0, -20);

            assertEquals(false, enemyAIComponent.isEntityBottomYAbove(player));

            // Set player bottom Y-position slightly above Enemy.
            player.setY(-(player.getHeight() + 1));

            assertEquals(true, enemyAIComponent.isEntityBottomYAbove(player));
        });
    }

    @Test
    public void testIsEntityMiddleYBelow() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position and spawn new player slightly below Enemy (but its middle not above).
            enemy.setX(0);
            enemy.setY(0);
            Entity player = spawn("player", 0, 10);

            assertEquals(false, enemyAIComponent.isEntityMiddleYBelow(player));

            // Set player middle Y-position slightly below Enemy.
            player.setY((player.getHeight() / 2 ) + 1);

            assertEquals(true, enemyAIComponent.isEntityMiddleYBelow(player));
        });
    }

    @Test
    public void testIsEntitySameY() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position and spawn new player slightly below Enemy.
            enemy.setX(0);
            enemy.setY(0);
            Entity player = spawn("player", 0, 10);

            assertEquals(false, enemyAIComponent.isEntitySameY(player));

            // Set player to same Y-pos as Enemy.
            player.setY(enemy.getY() + enemy.getHeight());

            assertEquals(true, enemyAIComponent.isEntityMiddleYBelow(player));
        });
    }

    @Test
    public void testEnemyDirectlyAbove() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position.
            enemy.setX(0);
            enemy.setY(0);

            // Spawn a new Enemy below existing Enemy.
            EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, 10, tempPlayer, new StatMultiplier());
            enemyAIComponent.onUpdate(1);
            assertEquals(false, enemyAIComponent.enemyDirectlyAbove());

            // Spawn a new Enemy at a Y-pos slightly above existing Enemy. +1 so raycast may work.
            EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, (enemy.getY() - (enemy.getHeight() + 1)), tempPlayer, new StatMultiplier());
            enemyAIComponent.onUpdate(1);

            assertEquals(true, enemyAIComponent.enemyDirectlyAbove());
        });
    }

    @Test
    public void testEnemyDirectlyBelow() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Set enemy position.
            enemy.setX(0);
            enemy.setY(0);

            // Spawn a new Enemy above existing Enemy.
            EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, -10, tempPlayer, new StatMultiplier());
            enemyAIComponent.onUpdate(1);
            assertEquals(false, enemyAIComponent.enemyDirectlyBelow());

            // Spawn a new Enemy at a Y-pos slightly below existing Enemy. +1 so raycast may work.
            EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, (enemy.getBottomY() + 1), tempPlayer, new StatMultiplier());
            enemyAIComponent.onUpdate(1);

            assertEquals(true, enemyAIComponent.enemyDirectlyBelow());
        });
    }
}
