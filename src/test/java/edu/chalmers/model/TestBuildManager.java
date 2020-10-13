package edu.chalmers.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.model.building.BuildManager;
import edu.chalmers.model.building.MapManager;
import edu.chalmers.model.wave.WaveManager;
import edu.chalmers.services.TileMap;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestBuildManager {

    private BuildManager buildManager;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        SetupWorld.initApp();
    }

    private void resetTest() {
        SetupWorld.clearEntities();
        MapManager mapManager = new MapManager(new TileMap().getBlockMapFromLevel("level1.tmx"));
        buildManager = new BuildManager(new PlayerComponent(new PhysicsComponent()).getBuildRangeTiles(), mapManager);
    }

    @Test
    public void testPlaceBlock() {
        resetTest();
        Point2D blockPos = new Point2D(100, 100);
        buildManager.placeBlock(blockPos);
        assertEquals(1, getGameWorld().getEntitiesByType(EntityType.BLOCK).size());
    }
}
