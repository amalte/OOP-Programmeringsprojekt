package edu.chalmers.controller;

import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.wave.WaveManager;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class WaveController {
    WaveManager waveManager;
    TimerAction waveTimerAction;
    int baseWaveTimeSec = 5;  // Shortest time a wave lasts before the next one starts
    //WaveView waveView;

    public WaveController(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    public void onNoEnemiesLeft() {      // When all enemies are dead
        waveManager.generateNewWave();

        //waveView.updateText(genericPlatformer.getWaveManager().getCurrentWave()); // Update current wave text
    }

    public void onNewWaveSpawned() {    // When a new wave spawns
        if (waveTimerAction != null) waveTimerAction.expire();   // Stop and remove currentWaveTimer since a new wave just started
        waveTimerAction = createWaveTimer();  // Create new timer, when timer reaches 0 it will generate wave
    }

    private TimerAction createWaveTimer() {
        return runOnce(() -> waveManager.generateNewWave(), Duration.seconds(baseWaveTimeSec + waveManager.getSpawnTimeSec()));
    }
}
