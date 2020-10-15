package edu.chalmers.controller;

import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.view.game.BuildView;

import static com.almasb.fxgl.dsl.FXGL.getInput;

public class BuildUIController {
    GenericPlatformer game;
    BuildView buildView;

    public BuildUIController(GenericPlatformer game, BuildView buildView) {
        this.game = game;
        this.buildView = buildView;
    }

    /**
     * Method updates the build ui overlay, (will be green box if inside build range for example)
     */
    public void updateBuildTileUI() {
        buildView.updateTileOverlay(game.getBuildManager().getEmptyReachableTiles(CoordsCalculations.posToTile(EntityPos.getPosition(game.getPlayer()))));

        if(game.getBuildManager().isInBuildRange(CoordsCalculations.posToTile(getInput().getMousePositionWorld()), CoordsCalculations.posToTile(EntityPos.getPosition(game.getPlayer())))) {
            buildView.showBuildUI(getInput().getMousePositionWorld(), game.getBuildManager().possibleToPlaceBlockOnPos(getInput().getMousePositionWorld(), EntityPos.getPosition(game.getPlayer())));
        }
        else {
            buildView.hideBuildUI();
        }
    }
}
