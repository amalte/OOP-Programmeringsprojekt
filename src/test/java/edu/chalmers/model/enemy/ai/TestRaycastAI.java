package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.model.SetupWorld;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.model.enemy.EnemyFactory;
import edu.chalmers.model.enemy.StatMultiplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

@ExtendWith(RunWithFX.class)
public class TestRaycastAI {

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
}
