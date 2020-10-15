package edu.chalmers.view.game;

import com.almasb.fxgl.dsl.FXGL;
import edu.chalmers.model.GenericPlatformer;
import edu.chalmers.model.IObserver;
import edu.chalmers.utilities.Constants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

/**
 * View that creates all the nodes needed for showing game information to the user.
 */
public class GameUI implements IObserver {

    private GenericPlatformer game;

    public GameUI(GenericPlatformer game) {
        this.game = game;
    }

    private final double healthBarMaxWidth = 200;
    private final double healthBarMaxHeight = 30;
    private Rectangle healthBar;
    private Text amountOfAmmoText;
    private Text activeWeaponText;

    private Text drawWaveText(int CurrentWave) {
        Text currentWaveText = new Text();
        currentWaveText.setY(50);
        currentWaveText.setX(Constants.GAME_WIDTH/2 - 150);
        currentWaveText.setText("Current Wave:" + CurrentWave);
        currentWaveText.setTextAlignment(TextAlignment.LEFT);
        currentWaveText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 36));
        currentWaveText.setFill(Color.TOMATO);
        currentWaveText.setStroke(Color.BLACK);
        return currentWaveText;
    }

    private Rectangle drawHealthBar(){
        healthBar = new Rectangle(healthBarMaxWidth,healthBarMaxHeight, Color.LIMEGREEN);
        healthBar.setY(10);
        healthBar.setX(10);
        return healthBar;
    }

    private Rectangle drawBackgroundHealthBar(){
        Rectangle background = new Rectangle(healthBarMaxWidth, healthBarMaxHeight);
        background.setY(10);
        background.setX(10);
        background.setFill(Color.TRANSPARENT);
        background.setStroke(Color.BLACK);
        return background;
    }

    private Text drawAmountOfAmmoText(){
        amountOfAmmoText = new Text(10,100, "");
        amountOfAmmoText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        amountOfAmmoText.setFill(Color.LIMEGREEN);
        amountOfAmmoText.setStroke(Color.BLACK);
        updateAmmunition();
        return amountOfAmmoText;
    }

    private Text drawActiveWeapon(){
        activeWeaponText = new Text(10, 70, "");
        activeWeaponText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        activeWeaponText.setFill(Color.LIMEGREEN);
        activeWeaponText.setStroke(Color.BLACK);
        updateActiveWeapon();
        return activeWeaponText;
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

    public void setReloading(){
        amountOfAmmoText.setText("Reloading");
    }

    /**
     * Changes width on healthBar when health is lowered.
     */
    public void updateHealth(){
        float percentage = (float)game.getPlayerComponent().getHealth()/(float)game.getPlayerComponent().getMaxHealth(); //Possible placed in controller
        healthBar.setWidth(healthBarMaxWidth * percentage);
    }

    /**
     * Adds all UI nodes to the game scene.
     */
    public void setNodes() {
        FXGL.getGameScene().addUINodes(drawWaveText(1), drawHealthBar(), drawBackgroundHealthBar(), drawAmountOfAmmoText(), drawActiveWeapon());
    }

    /**
     * Updates all the views nodes.
     */
    @Override
    public void update() {
        updateHealth();
        updateActiveWeapon();
        updateAmmunition();
    }
}
