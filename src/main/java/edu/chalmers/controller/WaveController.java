package edu.chalmers.controller;

import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.GenericPlatformer;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class WaveController {
    GenericPlatformer genericPlatformer;
    int waveTimerSec = 15;  // how long a wave can last before next wave starts
    TimerAction waveTimerAction;
    //WaveView waveView;

    public WaveController(GenericPlatformer genericPlatformer) {
        this.genericPlatformer = genericPlatformer;
    }

    public void onNoEnemiesLeft() {      // should be called in Model when there are no enemies in wave left through observer pattern

        if (waveTimerAction != null) waveTimerAction.expire();   // Stop and remove currentWaveTimer since a new wave has started
        waveTimerAction = createWaveTimer();  // Create new timer, when timer reaches 0 generate a new wave

        genericPlatformer.getWaveManager().generateNewWave();

        //waveView.updateText(genericPlatformer.getWaveManager().getCurrentWave()); // Update current wave text
    }

    private TimerAction createWaveTimer() {
        return runOnce(new Runnable() {
            @Override
            public void run() {
                genericPlatformer.getWaveManager().generateNewWave();
            }
        }, Duration.seconds(waveTimerSec));
    }
}
