package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.wave.WaveManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestWaveManager {

    private WaveManager waveManager;
    private Entity player;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        SetupWorld.initApp();
    }

    private void resetTest() {
        FXGLTest.clearAllEntities();
        player = spawn("player");
        waveManager = new WaveManager(player);
    }

    @Test
    public void testGenerateWave(){
        resetTest();
        waveManager.generateNewWave();
        assertEquals(2, waveManager.getCurrentWave());
    }

    @Test
    public void testGetSpawnTimeSec() {
        resetTest();
        assertEquals(0, waveManager.getSpawnTimeSec());
        waveManager.generateNewWave();
        assertEquals(4, waveManager.getSpawnTimeSec());
    }

    @Test
    public void testGetCurrentWave(){
        resetTest();
        assertEquals(1, waveManager.getCurrentWave());
        waveManager.generateNewWave();
        assertEquals(2, waveManager.getCurrentWave());
    }
}
