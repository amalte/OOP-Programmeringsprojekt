package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.TestingUtilities;
import edu.chalmers.model.SetupWorld;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.enemy.StatMultiplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestPlatformAI {

    private Entity enemy;
    private EnemyComponent enemyComponent;
    private EnemyAIComponent enemyAIComponent;
    private Entity tempPlayer;

    @BeforeAll
    public static void initApplication() throws InterruptedException {
        SetupWorld.initApp();
    }

    // Initializes the Player, Enemy and its EnemyComponent.
    private void init() {
        tempPlayer = spawn("player",10000,10000);

        enemy = EnemyFactory.getInstance().createEnemy("Zombie", 0, 0, tempPlayer, new StatMultiplier());
        enemyComponent = enemy.getComponent(EnemyComponent.class);
        enemyAIComponent = enemy.getComponent(EnemyAIComponent.class);

        // +1 so raycast works. Level enemy with platform.
        enemy.setY(-(enemy.getHeight() + 1));
    }

    @Test
    public void testUpdatePlatforms() {
        TestingUtilities.clearAllEntities();
        init();

        Entity platform;

        // No platforms exist yet.
        enemyAIComponent.getPlatformAI().updatePlatforms();
        assertEquals(0, enemyAIComponent.getPlatformAI().getPlatforms().size());

        // Spawn platform. 1 should exist in list.
        platform = spawn("testingPlatform", 3, 5);
        enemyAIComponent.getPlatformAI().updatePlatforms();
        assertEquals(1, enemyAIComponent.getPlatformAI().getPlatforms().size());
        assertEquals(platform, enemyAIComponent.getPlatformAI().getPlatforms().get(0));

        // Add another platform. 2 platforms should exist.
        spawn("testingPlatform", 5, 7);
        enemyAIComponent.getPlatformAI().updatePlatforms();
        assertEquals(2, enemyAIComponent.getPlatformAI().getPlatforms().size());

        // Clear all existing platforms. Platforms spawned att X=0 are not considered a platform (even if they technically are).
        // They are considered to be the ground, and are therefore not included in the platforms list.
        TestingUtilities.clearAllEntities();
        spawn("testingPlatform", 0, 5);
        enemyAIComponent.getPlatformAI().updatePlatforms();
        assertEquals(0, enemyAIComponent.getPlatformAI().getPlatforms().size());
    }

    @Test
    public void testGetClosestPlatform() {
        TestingUtilities.clearAllEntities();
        init();
        Entity platform1;

        // --- One platform --- //
        platform1 = spawn("testingPlatform", 5, 5);
        enemyAIComponent.getPlatformAI().updatePlatforms();
        assertEquals(platform1, enemyAIComponent.getPlatformAI().getClosestPlatform());
        //


        // --- Two platforms --- //
        TestingUtilities.clearAllEntities();
        init();

        // Spawn two platforms at roughly the same Y-pos. platform 1 is closer out of the two.
        platform1 = spawn("testingPlatform", 5, 3);
        spawn("testingPlatform", -10, 5);
        enemyAIComponent.getPlatformAI().updatePlatforms();
        assertEquals(platform1, enemyAIComponent.getPlatformAI().getClosestPlatform());
        //

        // TODO - check platform below Enemy? (for-loop)
    }

    @Test
    public void getPlatformBelowEnemy() {
        TestingUtilities.clearAllEntities();
        init();
        Entity platform;

        assertEquals(null, enemyAIComponent.getPlatformAI().getPlatformBelowEnemy());

        // ---- leftDownwardRaycast ---- //
        platform = spawn("testingPlatform", 50, 50);
        enemyAIComponent.getPlatformAI().updatePlatforms();
        // Set position so only leftDownwardRaycast is hitting platform.
        enemy.setX(platform.getRightX() - 10);
        enemy.setY(platform.getY() - (enemy.getHeight() + 5));
        assertEquals(platform, enemyAIComponent.getPlatformAI().getPlatformBelowEnemy());
        //

        TestingUtilities.clearAllEntities();
        init();

        // ---- rightDownwardRaycast ---- //
        platform = spawn("testingPlatform", 50, 50);
        enemyAIComponent.getPlatformAI().updatePlatforms();

        // Set position so only rightDownwardRaycast is hitting platform.
        enemy.setX((platform.getX() - enemy.getWidth()) + 10);
        enemy.setY(platform.getY() - (enemy.getHeight() + 5));
        assertEquals(platform, enemyAIComponent.getPlatformAI().getPlatformBelowEnemy());
        //
    }

    @Test
    public void testCheckPlatformBelowEnemy() {
        TestingUtilities.clearAllEntities();
        init();
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
    }

    @Test
    public void testPlayerRecentPlatformContactCheck() {
        TestingUtilities.clearAllEntities();
        init();

        // Player has not had contact with platform yet.
        Entity platform = spawn("testingPlatform", 50, 50);
        enemyAIComponent.getPlatformAI().updatePlatforms();
        assertEquals(null, enemyAIComponent.getPlatformAI().getPlayerRecentPlatformContact());

        // ---- leftPlayerPlatformRaycast ---- //
        // Set position so only leftPlayerPlatformRaycast is hitting platform.
        tempPlayer.setX(platform.getRightX() - 10);
        tempPlayer.setY(platform.getY() - (tempPlayer.getHeight() + 2));

        enemyAIComponent.getPlatformAI().playerRecentPlatformContactCheck();
        assertEquals(platform, enemyAIComponent.getPlatformAI().getPlayerRecentPlatformContact());
        //

        TestingUtilities.clearAllEntities();
        init();

        // ---- rightPlayerPlatformRaycast ---- //
        // Spawn new platform and update list.
        platform = spawn("testingPlatform", 50, 50);
        enemyAIComponent.getPlatformAI().updatePlatforms();

        // Set position so only rightPlayerPlatformRaycast is hitting platform.
        tempPlayer.setX((platform.getX() - enemy.getWidth()) + 10);
        tempPlayer.setY(platform.getY() - (enemy.getHeight() + 2));

        enemyAIComponent.getPlatformAI().playerRecentPlatformContactCheck();
        assertEquals(platform,  enemyAIComponent.getPlatformAI().getPlayerRecentPlatformContact());
        //
    }
}
