package edu.chalmers.view.game;

import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.IObserver;
import edu.chalmers.utilities.Constants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

/**
 * @author Oscar Arvidson
 *
 * View that creates all the nodes needed for showing game information to the user.
 */
public class GameUI implements IObserver {

    private GenericPlatformer game;

    public GameUI(GenericPlatformer game) {
        this.game = game;
    }

    private final double healthBarMaxWidth = 400;
    private final double healthBarMaxHeight = 30;
    private Rectangle healthBar;
    private Rectangle healthBackground;
    private Text amountOfAmmoText;
    private Text activeWeaponText;
    private Text currentWaveText;
    private Text reloadingText;

    /**
     * Create the nodes for this view.
     */
    public void createNodes(){

        //Current wave text
        currentWaveText = new Text();
        currentWaveText.setY(50);
        currentWaveText.setX(Constants.GAME_WIDTH/2 - 150);
        currentWaveText.setTextAlignment(TextAlignment.LEFT);
        currentWaveText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 36));
        currentWaveText.setFill(Color.TOMATO);
        currentWaveText.setStroke(Color.BLACK);

        FXGL.getGameScene().addUINode(currentWaveText);

        //Health bar
        healthBar = new Rectangle(healthBarMaxWidth,healthBarMaxHeight, Color.LIMEGREEN);
        healthBar.setY(10);
        healthBar.setX(10);

        FXGL.getGameScene().addUINode(healthBar);

        //Health bar background
        healthBackground = new Rectangle(healthBarMaxWidth, healthBarMaxHeight);
        healthBackground.setY(10);
        healthBackground.setX(10);
        healthBackground.setFill(Color.TRANSPARENT);
        healthBackground.setStroke(Color.BLACK);

        FXGL.getGameScene().addUINode(healthBackground);

        //Amount of ammo text
        amountOfAmmoText = new Text(10,100, "");
        amountOfAmmoText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        amountOfAmmoText.setFill(Color.LIMEGREEN);
        amountOfAmmoText.setStroke(Color.BLACK);

        FXGL.getGameScene().addUINode(amountOfAmmoText);

        //Active weapon text
        activeWeaponText = new Text(10, 70, "");
        activeWeaponText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        activeWeaponText.setFill(Color.LIMEGREEN);
        activeWeaponText.setStroke(Color.BLACK);

        FXGL.getGameScene().addUINode(activeWeaponText);

        //Reloading text
        reloadingText = new Text(10, 130, "");
        reloadingText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        reloadingText.setFill(Color.LIMEGREEN);
        reloadingText.setStroke(Color.BLACK);

        FXGL.getGameScene().addUINode(reloadingText);
        update();
    }

    /**
     * Updates activeWeaponText to be equal to the name of the players selected weapon.
     */
    public void updateActiveWeapon(){
        activeWeaponText.setText("Weapon: " + game.getPlayerComponent().getActiveWeapon().getWeaponType().getName());
    }

    /**
     * Updates amountOfAmmoText to be equal to the magazine of the players selected weapon.
     */
    public void updateAmmunition(){
        amountOfAmmoText.setText("Ammunition: " + game.getPlayerComponent().getActiveWeapon().getMagazineCounter());
    }

    /**
     * Updates reloadingText depending on if the active weapon is being reloaded.
     */
    public void updateReloading(){
        if(game.getPlayerComponent().getActiveWeapon().isReloading()) {
            reloadingText.setText("Reloading");
            reloadingText.setFill(Color.INDIANRED);
        } else {
            reloadingText.setText("Reloaded");
            reloadingText.setFill(Color.LIMEGREEN);
        }
    }


    /**
     * Changes width on healthBar when health is lowered.
     */
    private void updateHealth(){
        float percentage = (float)game.getPlayerComponent().getHealth()/(float)game.getPlayerComponent().getMaxHealth(); //Possible placed in controller
        healthBar.setWidth(healthBarMaxWidth * percentage);
    }

    /**
     * Updates currentWaveText to the number of the current wave.
     */
    public void updateWaveCounter(){
        currentWaveText.setText("Current Wave: "+ game.getWaveManager().getCurrentWave());
    }

    /**
     * Updates all the views nodes.
     */
    @Override
    public void update() {
        updateHealth();
        updateActiveWeapon();
        updateAmmunition();
        updateWaveCounter();
        updateReloading();
    }
}
