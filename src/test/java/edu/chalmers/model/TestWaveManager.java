package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.wave.WaveManager;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.waitForRunLater;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static edu.chalmers.FXGLTest.*;

@ExtendWith(RunWithFX.class)
public class TestWaveManager {

    private WaveManager waveManager;
    private Entity player;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    private void resetTest() {
        FXGLTest.clearAllEntities();
        player = spawn("player");
        waveManager = new WaveManager(player);
    }

    @Test
    public void testGenerateWave() throws InterruptedException {
        waitForRunLater(() -> {
            resetTest();
            waveManager.generateNewWave();
            assertEquals(2, waveManager.getCurrentWave());
        });
    }

    @Test
    public void testGetSpawnTimeSec() throws InterruptedException {
        waitForRunLater(() -> {
            resetTest();
            assertEquals(0, waveManager.getSpawnTimeSec());
            waveManager.generateNewWave();
            assertEquals(4, waveManager.getSpawnTimeSec());
        });

    }

    @Test
    public void testGetCurrentWave() throws InterruptedException {
        waitForRunLater(() -> {
            resetTest();
            assertEquals(1, waveManager.getCurrentWave());
            waveManager.generateNewWave();
            assertEquals(2, waveManager.getCurrentWave());
        });
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }
}
