package edu.chalmers.model.building.blocks;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.time.TimerAction;
import edu.chalmers.model.building.*;
import edu.chalmers.utilities.CoordsCalculations;
import edu.chalmers.model.EntityType;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class Block implements IBlock, Observable {

    private Entity currentBlock;
    private PhysicsComponent physics = new PhysicsComponent();

    private int health = 100;
    private TimerAction damageDelayTimer;
    private int damageDelayMilliseconds = 500;

    public Block(Point2D mousePos) {
        Point2D blockPosition = CoordsCalculations.posToTilePos(mousePos);

        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        currentBlock = FXGL.entityBuilder()
                .type(EntityType.BLOCK)
                .at(((int)blockPosition.getX()),((int)blockPosition.getY()))
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("BuildingBlock.png"))
                .with(physics)
                .with(new CollidableComponent(true))
                .with("this", this)         // Adds a property with value of this class and the key String "this". Can be used to reach Block class when simply working with Entity's.
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
        }
    }

    /**
     * Lower Block health with damage.
     * @param damage Amount of health points to be inflicted to the Block.
     */
    @Override
    public void inflictDamage(int damage){
        if(damageDelayTimer.isExpired()){
            damageDelayTimer = runOnce(() -> health -= damage, Duration.millis(damageDelayMilliseconds));
        }
        checkHealth();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update(CoordsCalculations.posToTile(currentBlock.getPosition()));
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Method checks the block's health and takes appropriate course of action based on health left.
     */
    private void checkHealth() {
        // Remove block if its health becomes 0 or lower
        if(health <= 0) {
            destroyAndNotify();
        }
        else if(health <= 30) {
            changeTexture("BuildingBlockDamageStage2.png");
        }
        else if(health <= 70) {
            changeTexture("BuildingBlockDamageStage1.png");
        }
    }

    /**
     * Method removes block and notifies its observers.
     */
    private void destroyAndNotify() {
        FXGL.getGameWorld().removeEntity(currentBlock);
        notifyObservers();
    }

    /**
     * Method changes the block's texture to a texture file with the given name.
     * @param textureName Name of texture file.
     */
    private void changeTexture(String textureName) {
        currentBlock.getViewComponent().clearChildren();
        currentBlock.getViewComponent().addChild(FXGL.getAssetLoader().loadTexture(textureName));
    }

    /**
     * Method initiates damage delay timer.
     */
    private void initDamageDelayTimer(){
        damageDelayTimer = runOnce(() -> {}, Duration.seconds(0));
    }
}
