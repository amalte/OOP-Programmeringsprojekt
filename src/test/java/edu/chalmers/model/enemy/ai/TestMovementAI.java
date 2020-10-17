package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.PlayerComponent;
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
public class TestMovementAI {

    private Entity enemy;
    private EnemyComponent enemyComponent;
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
            changeEnemy("Zombie");

            // +1 so raycast works. Level enemy with platform.
            enemy.setY(-(enemy.getHeight() + 1));
        });
    }

    private void changeEnemy(String enemyName) {
        enemy = EnemyFactory.getInstance().createEnemy(enemyName, 0, 0, tempPlayer, new StatMultiplier());
        enemyComponent = enemy.getComponent(EnemyComponent.class);
        enemyAIComponent = enemy.getComponent(EnemyAIComponent.class);
    }

    private void initJump() {
        enemyAIComponent.getMovementAI().setMoveDirection(MovementAI.Direction.RIGHT);
        enemyAIComponent.getRaycastAI().updateRaycastsDirection();
        enemyComponent.resetJumpAmounts();
        enemyAIComponent.getMovementAI().doJump();
    }

    @Test
    public void testSetMoveDirection() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Spawn player to the left of Enemy and set it as target. updateMoveDirection() is not called so moveDirection should be null.
            Entity player = spawn("player", -100, enemy.getY());
            enemyAIComponent.setTarget(player);
            assertEquals(null, enemyAIComponent.getMovementAI().getMoveDirection());

            // Update moveDirection. Should be LEFT.
            enemyAIComponent.getMovementAI().updateMoveDirection();
            assertEquals(MovementAI.Direction.LEFT, enemyAIComponent.getMovementAI().getMoveDirection());

            // Change player X-position to the right of Enemy.
            player.setX(100);
            enemyAIComponent.getMovementAI().updateMoveDirection();
            assertEquals(MovementAI.Direction.RIGHT, enemyAIComponent.getMovementAI().getMoveDirection());
        });
    }


    @Test
    public void testMoveTowardsTarget() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            // Spawn player to the left of Enemy and set it as target. Update moveDirection and start moving.
            Entity player = spawn("player", -100, enemy.getY());
            enemyAIComponent.setTarget(player);
            enemyAIComponent.getMovementAI().updateMoveDirection();
            enemyAIComponent.getMovementAI().moveTowardsTarget();
            assertEquals(-enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));

            // Change player X-position to the right of Enemy. Update moveDirection and start moving.
            player.setX(100);
            enemyAIComponent.getMovementAI().updateMoveDirection();
            enemyAIComponent.getMovementAI().moveTowardsTarget();
            assertEquals(enemyComponent.getMoveSpeed(), Math.round(enemyComponent.getPhysics().getVelocityX()));

            // Set playerReached to true so Enemy stops moving.
            enemyAIComponent.setPlayerReached(true);
            enemyAIComponent.getMovementAI().moveTowardsTarget();
            assertEquals(0, Math.round(enemyComponent.getPhysics().getVelocityX()));

            // TODO - test nearbyEnemyHasReachedPlayer?
        });
    }

    @Test
    public void testDoJump() throws InterruptedException {

        // ------ IF (jump up to platform from the ground) ------ //:
        init();
        waitForRunLater(() -> {
            double jmpHeight;

            tempPlayer.getComponent(PlayerComponent.class).setOnGround(false);                        // Set player onGround to false.
            tempPlayer.setY(-100);                                                                    // Set player Y-position to above enemy.
            spawn("testingPlatform", enemy.getWidth() + 10, enemy.getY() - 25);     // Spawn platform in front of higherHorizontalRaycast

            // init() starts enemy as a Zombie. Init jump variables and methods.
            initJump();
            // Set jump height to enemy type's jump height * the stat improvement.
            jmpHeight = Math.round(-(enemyComponent.getEnemyType().getJumpHeight() * enemyAIComponent.getStatImprovementAI().getZombieGroundToPlatformJmp()));
            assertEquals(jmpHeight, Math.round(enemyComponent.getPhysics().getVelocityY()));

            // Change enemy type to rex. Init jump variables and methods.
            changeEnemy("rex");
            initJump();
            // Set jump height to enemy type's jump height * the stat improvement.
            jmpHeight = Math.round(-(enemyComponent.getEnemyType().getJumpHeight() * enemyAIComponent.getStatImprovementAI().getRexGroundToPlatformJmp()));
            assertEquals(jmpHeight, Math.round(enemyComponent.getPhysics().getVelocityY()));

            // Change enemy type to blob. Init jump variables and methods.
            changeEnemy("blob");
            initJump();
            // Set jump height to enemy type's jump height * the stat improvement. -1 because stupid rounding error.
            jmpHeight = Math.round(-(enemyComponent.getEnemyType().getJumpHeight() * enemyAIComponent.getStatImprovementAI().getBlobGroundToPlatformJmp())) -1;
            assertEquals(jmpHeight, Math.round(enemyComponent.getPhysics().getVelocityY()));
        });
        //


        // ------ IF (going to fall) ------ //:
        init();
        waitForRunLater(() -> {
            double jmpHeight;
            double moveSpeed;

            enemyComponent.setAirborne(false);

            // init() starts enemy as a Zombie. Init jump variables and methods.
            initJump();
            // Set jump height and move speed to enemy type's values * the stat improvement.
            moveSpeed = Math.round(enemyComponent.getEnemyType().getMoveSpeed() * enemyAIComponent.getStatImprovementAI().getZombiePlatformToPlatformSpeed());
            jmpHeight = Math.round(-(enemyComponent.getEnemyType().getJumpHeight() * enemyAIComponent.getStatImprovementAI().getZombiePlatformToPlatformJmp()));
            assertEquals(moveSpeed, Math.round(enemyComponent.getMoveSpeed()));
            assertEquals(jmpHeight, Math.round(enemyComponent.getPhysics().getVelocityY()));

            // Change enemy type to rex. Init jump variables and methods.
            changeEnemy("rex");
            initJump();
            // Set jump height and move speed to enemy type's values * the stat improvement. -1 because stupid rounding error.
            moveSpeed = Math.round(enemyComponent.getEnemyType().getMoveSpeed() * enemyAIComponent.getStatImprovementAI().getRexPlatformToPlatformSpeed());
            jmpHeight = Math.round(-(enemyComponent.getEnemyType().getJumpHeight() * enemyAIComponent.getStatImprovementAI().getRexPlatformToPlatformJmp())) -1;
            assertEquals(moveSpeed, Math.round(enemyComponent.getMoveSpeed()));
            assertEquals(jmpHeight, Math.round(enemyComponent.getPhysics().getVelocityY()));

            // Change enemy type to blob. Init jump variables and methods.
            changeEnemy("blob");
            initJump();
            // Set jump height and move speed to enemy type's values * the stat improvement.
            moveSpeed = Math.round(enemyComponent.getEnemyType().getMoveSpeed() * enemyAIComponent.getStatImprovementAI().getBlobPlatformToPlatformSpeed());
            jmpHeight = Math.round(-(enemyComponent.getEnemyType().getJumpHeight() * enemyAIComponent.getStatImprovementAI().getBlobPlatformToPlatformJmp()));
            assertEquals(moveSpeed, Math.round(enemyComponent.getMoveSpeed()));
            assertEquals(jmpHeight, Math.round(enemyComponent.getPhysics().getVelocityY()));
        });
        //


        // ------ IF (hit a block or platform) ------ //:
        init();
        waitForRunLater(() -> {
            spawn("testingPlatform", enemy.getWidth() + 10, enemy.getY());      // Spawn platform to the right of Enemy.

            // init() starts enemy as a Zombie. Init jump variables and methods.
            initJump();
            assertEquals(-enemyComponent.getJumpHeight(), Math.round(enemyComponent.getPhysics().getVelocityY()));

            // Change enemy type to rex. Init jump variables and methods.
            changeEnemy("rex");
            initJump();
            assertEquals(-enemyComponent.getJumpHeight(), Math.round(enemyComponent.getPhysics().getVelocityY()));

            // Change enemy type to blob. Init jump variables and methods.
            changeEnemy("blob");
            initJump();
            assertEquals(-enemyComponent.getJumpHeight(), Math.round(enemyComponent.getPhysics().getVelocityY()));
        });
        //


        // ------ IF !jumpAllowed ------ //:
        init();
        waitForRunLater(() -> {
            enemyAIComponent.getMovementAI().setJumpAllowed(false);
            assertEquals(0, Math.round(enemyComponent.getPhysics().getVelocityY()));
        });
        //
    }

    @Test
    public void testFloatingPlatformMovement() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            enemyAIComponent.onUpdate(1);

            // Setup for If-statement
            tempPlayer.getComponent(PlayerComponent.class).setOnGround(false);
            enemyAIComponent.getMovementAI().setUnderPlatform(false);
            tempPlayer.setY(-1000);
            enemyAIComponent.getMovementAI().setMoveToNextPlatform(true);
            //

            enemyComponent.setAirborne(false);
            // No platform exists so should be null.
            enemyAIComponent.getMovementAI().floatingPlatformMovement();
            assertEquals(null, enemyAIComponent.getPlatformAI().getClosestPlatform());

            // Add platform and update platforms list.
            Entity platform = spawn("testingPlatform", 100, 100);
            enemyAIComponent.getPlatformAI().updatePlatforms();
            enemyAIComponent.getMovementAI().floatingPlatformMovement();
            assertEquals(platform, enemyAIComponent.getPlatformAI().getClosestPlatform());
        });


        // ---- else ---- //:
        init();
        waitForRunLater(() -> {

            tempPlayer.getComponent(PlayerComponent.class).setOnGround(true);
            enemyComponent.setAirborne(false);
            assertEquals(tempPlayer, enemyAIComponent.getTarget());
        });
    }
}
