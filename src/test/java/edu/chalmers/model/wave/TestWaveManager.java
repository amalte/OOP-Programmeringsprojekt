package edu.chalmers.model.wave;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.FXGLTest;
import edu.chalmers.model.MockObserver;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Malte Åkvist, Oscar Arvidson
 * <p>
 * Test class for WaveManager.
 */
public class TestWaveManager {

    private WaveManager waveManager;
    private Entity player;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    private void resetTest() throws InterruptedException {
        waitForRunLater(() -> {
            FXGLTest.clearAllEntities();
            player = spawn("player");
        });
        waveManager = new WaveManager(player);
    }

    @Test
    public void testGenerateWave() throws InterruptedException {
        resetTest();

        assertEquals(0, waveManager.getCurrentWave());
        waveManager.generateNewWave();
        assertEquals(1, waveManager.getCurrentWave());
    }

    @Test
    public void testGetSpawnTimeSec() throws InterruptedException {
        resetTest();
        //assertEquals(0, waveManager.getSpawnTimeSec());
        waveManager.generateNewWave();
        //assertEquals(2, waveManager.getSpawnTimeSec());
    }

    @Test
    public void testGetCurrentWave() throws InterruptedException {
        resetTest();
        assertEquals(0, waveManager.getCurrentWave());
        waveManager.generateNewWave();
        assertEquals(1, waveManager.getCurrentWave());
    }

    @Test
    public void testObserverMethods() throws InterruptedException {
        resetTest();
        MockObserver o = new MockObserver();
        assertTrue(waveManager.observers.size() == 0);
        waveManager.addObserver(o);
        assertTrue(waveManager.observers.size() == 1);
        waveManager.notifyObserver();
        assertTrue(o.isTest());
        waveManager.removeObserver(o);
        assertTrue(waveManager.observers.size() == 0);
    }
}
