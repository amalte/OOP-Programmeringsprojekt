package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.enemy.StatMultiplier;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sam Salek
 * <p>
 * Test class for PlatformAI.
 */
public class TestPlatformAI {

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

            tempPlayer = spawn("player", 10000, 10000);

            enemy = EnemyFactory.getInstance().createEnemy("Zombie", 0, 0, tempPlayer, new StatMultiplier());
            enemyAIComponent = enemy.getComponent(EnemyAIComponent.class);

            // +1 so raycast works. Level enemy with platform.
            enemy.setY(-(enemy.getHeight() + 1));
        });
    }

    @Test
    public void testUpdatePlatforms() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // No platforms exist yet.
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(0, enemyAIComponent.getPlatformAI().getPlatforms().size());

            // Platforms spawned att X=0 are not considered a platform (even if they technically are).
            // They are considered to be the ground, and are therefore not included in the platforms list.
            spawn("testingPlatform", 0, 5);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(0, enemyAIComponent.getPlatformAI().getPlatforms().size());

            // Spawn platform. 1 should exist in list.
            Entity platform = spawn("testingPlatform", 3, 5);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(1, enemyAIComponent.getPlatformAI().getPlatforms().size());
            assertEquals(platform, enemyAIComponent.getPlatformAI().getPlatforms().get(0));

            // Add another platform. 2 platforms should exist.
            spawn("testingPlatform", 5, 7);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(2, enemyAIComponent.getPlatformAI().getPlatforms().size());
        });
    }

    @Test
    public void testGetClosestPlatform() throws InterruptedException {
        // --- One platform --- //
        init();
        waitForRunLater(() -> {
            Entity platform = spawn("testingPlatform", 5, 5);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(platform, enemyAIComponent.getPlatformAI().getClosestPlatform());
        });
        //


        // --- Two platforms --- //
        init();
        waitForRunLater(() -> {
            // Spawn two platforms at roughly the same Y-pos. platform 1 is closer out of the two.
            Entity platform = spawn("testingPlatform", 5, 3);
            spawn("testingPlatform", -10, 5);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(platform, enemyAIComponent.getPlatformAI().getClosestPlatform());
        });
        //

        // TODO - check platform below Enemy? (for-loop)
    }

    @Test
    public void getPlatformBelowEnemy() throws InterruptedException {
        // ---- leftDownwardRaycast ---- //
        init();
        waitForRunLater(() -> {
            // No platform at start.
            assertEquals(null, enemyAIComponent.getPlatformAI().getPlatformBelowEnemy());

            Entity platform = spawn("testingPlatform", 50, 50);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            // Set position so only leftDownwardRaycast is hitting platform.
            enemy.setX(platform.getRightX() - 10);
            enemy.setY(platform.getY() - (enemy.getHeight() + 5));
            assertEquals(platform, enemyAIComponent.getPlatformAI().getPlatformBelowEnemy());
        });
        //

        // ---- rightDownwardRaycast ---- //
        init();
        waitForRunLater(() -> {
            Entity platform = spawn("testingPlatform", 50, 50);
            enemyAIComponent.getPlatformAI().updatePlatforms();

            // Set position so only rightDownwardRaycast is hitting platform.
            enemy.setX((platform.getX() - enemy.getWidth()) + 10);
            enemy.setY(platform.getY() - (enemy.getHeight() + 5));
            assertEquals(platform, enemyAIComponent.getPlatformAI().getPlatformBelowEnemy());
        });
        //
    }

    @Test
    public void testCheckPlatformBelowEnemy() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity platform;

            // No platforms, should be false.
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(false, enemyAIComponent.getPlatformAI().checkPlatformBelowEnemy(null));

            // Spawn platform. Set Enemy position above it.
            platform = spawn("testingPlatform", 50, 50);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            enemy.setX(platform.getX());
            enemy.setY(platform.getY() - (enemy.getHeight() + 5));
            assertEquals(true, enemyAIComponent.getPlatformAI().checkPlatformBelowEnemy(platform));
        });
    }

    @Test
    public void testPlayerRecentPlatformContactCheck() throws InterruptedException {
        // Player has not had contact with platform yet.
        // ---- leftPlayerPlatformRaycast ---- //
        init();
        waitForRunLater(() -> {
            Entity platform = spawn("testingPlatform", 50, 50);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            assertEquals(null, enemyAIComponent.getPlatformAI().getPlayerRecentPlatformContact());

            // Set position so only leftPlayerPlatformRaycast is hitting platform.
            tempPlayer.setX(platform.getRightX() - 10);
            tempPlayer.setY(platform.getY() - (tempPlayer.getHeight() + 2));

            enemyAIComponent.getPlatformAI().playerRecentPlatformContactCheck();
            assertEquals(platform, enemyAIComponent.getPlatformAI().getPlayerRecentPlatformContact());
        });
        //


        // ---- rightPlayerPlatformRaycast ---- //
        init();
        waitForRunLater(() -> {
            // Spawn new platform and update list.
            Entity platform = spawn("testingPlatform", 50, 50);
            enemyAIComponent.getPlatformAI().updatePlatforms();

            // Set position so only rightPlayerPlatformRaycast is hitting platform.
            tempPlayer.setX((platform.getX() - 10));
            tempPlayer.setY(platform.getY() - (tempPlayer.getHeight() + 2));

            enemyAIComponent.getPlatformAI().playerRecentPlatformContactCheck();
            Entity platform1 = enemyAIComponent.getPlatformAI().getPlayerRecentPlatformContact();
            assertEquals(platform, platform1);
        });
        //
    }
}
