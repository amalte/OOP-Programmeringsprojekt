package edu.chalmers.model.building.blocks;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.building.MapManager;
import edu.chalmers.utilities.Constants;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.model.building.IBlock;
import edu.chalmers.model.EntityType;
import edu.chalmers.utilities.EntityPos;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class Block implements IBlock {
    private Entity currentBlock;
    private PhysicsComponent physics = new PhysicsComponent();
    private int tileSize = Constants.TILE_SIZE;

    private int health = 100;
    private TimerAction damageDelayTimer;
    private int damageDelayMilliseconds = 500;
    private MapManager mapManager;

    public Block(Point2D mousePos, MapManager mapManager) {
        this.mapManager = mapManager;
        Point2D blockPosition = CoordsCalculations.posToTilePos(mousePos);

        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        currentBlock = FXGL.entityBuilder()
                .type(EntityType.BLOCK)
                .at(((int)blockPosition.getX()),((int)blockPosition.getY()))
                .viewWithBBox(new Rectangle(tileSize, tileSize, Color.DARKGREY))
                .with(physics)
                .with(new CollidableComponent(true))
                .with("this", this)         // Adds a property with value of this class and key String "this". Can be used to reach Block class when simply working with Entity's.
                .buildAndAttach();

        initDamageDelayTimer();
    }

    @Override
    public boolean canBeDestroyed() {
        return true;
    }

    @Override
    public void remove() {
        if(canBeDestroyed()) {
            FXGL.getGameWorld().removeEntity(currentBlock);
            mapManager.removeBlockFromMap(CoordsCalculations.posToTile(EntityPos.getPosition(currentBlock)));
        }
    }

    private void die() {
        remove();
        mapManager.removeLevitatingNeighbours(CoordsCalculations.posToTile(EntityPos.getPosition(currentBlock)));
    }

    /**
     * Lower Block health with damage if it have not taken damage within 1 second.
     * @param damage amount of health points to be inflicted to player.
     */
    @Override
    public void inflictDamage(int damage){
        if(damageDelayTimer.isExpired()){
            damageDelayTimer = runOnce(() -> health -= damage, Duration.millis(damageDelayMilliseconds));
        }

        // Remove block if its health becomes 0 or lower
        if(health <= 0) {
            die();
        }
    }

    /**
     * Initiate damage delay timer.
     */
    private void initDamageDelayTimer(){
        damageDelayTimer = runOnce(() -> {}, Duration.seconds(0));
    }
}
