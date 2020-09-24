package edu.chalmers.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.main.Main;
import edu.chalmers.model.wave.WaveManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestWaveManager {
    @BeforeAll
    public static void initApp() throws InterruptedException {
        SetupWorld.initApp();
    }

    @Test
    public void testGenerateWave(){
        Entity player = spawn("player");
        WaveManager waveManager = new WaveManager(player);
        waveManager.generateNewWave();
        assertEquals(2, waveManager.getCurrentWave());
    }
}
