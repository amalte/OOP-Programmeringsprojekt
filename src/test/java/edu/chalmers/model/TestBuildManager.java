package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.model.building.BuildManager;
import edu.chalmers.model.wave.WaveManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestBuildManager {

    private BuildManager buildManager;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        SetupWorld.initApp();
    }
}
