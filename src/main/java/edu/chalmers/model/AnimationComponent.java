package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 * @author Erik Wetter
 *
 * AnimationComponent class. Creates animated textures for an entity and controls which animated texture is shown based on the entities movement.
 */
public class AnimationComponent extends Component {

    private int timer;
    private boolean isAirborne;

    private final AnimatedTexture texture;
    private final AnimationChannel animIdle, animWalk, animJump;

    public AnimationComponent(String idleImage, String walkImage, String jumpImage) {
        animIdle = new AnimationChannel(FXGL.image(idleImage), 1, 60, 60, Duration.seconds(1), 0, 0);
        animWalk = new AnimationChannel(FXGL.image(walkImage), 4, 70, 60, Duration.seconds(1), 0, 3);
        animJump = new AnimationChannel(FXGL.image(jumpImage), 4, 70, 60, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    /**
     * Sets the center point of the texture entity and adds the texture onto the entity.
     */
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(29, 29));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        setAnimationChannel();
    }

    /**
     * Sets which animation should play based on timer and isAirborne variables.
     */
    private void setAnimationChannel() {
        if(!isAirborne) {
            if (timer != 0) {

                if (texture.getAnimationChannel() == animIdle || texture.getAnimationChannel() == animJump) {
                    texture.loopAnimationChannel(animWalk);
                }

                timer = (int) (timer * 0.9);

                if (timer < 1) {
                    timer = 0;
                    texture.loopAnimationChannel(animIdle);
                }
            }else {
                texture.loopAnimationChannel(animIdle);
            }
        }else {
            if (texture.getAnimationChannel() == animIdle || texture.getAnimationChannel() == animWalk) {
                texture.playAnimationChannel(animJump);
            }
        }
    }


    /**
     * Sets the time walk anim should continue after moveRight method stops being called and rotates the texture to the right.
     */
    public void moveRight() {
        timer = 100;
        entity.setScaleX(1);
    }

    /**
     * Sets the time walk anim should continue after moveLeft method stops being called and rotates the texture to the left.
     */
    public void moveLeft() {
        timer = 100;
        entity.setScaleX(-1);
    }

    /**
     * Sets isAirborne to true.
     */
    public void jump() {
        isAirborne = true;
    }

    /**
     * Sets isAirborne to false.
     */
    public void landed() {
        isAirborne = false;
    }

    public int getTimer() {
        return timer;
    }

    public boolean isAirborne() {
        return isAirborne;
    }

    public AnimationChannel getAnimIdle() {
        return animIdle;
    }

    public AnimationChannel getAnimWalk() {
        return animWalk;
    }

    public AnimationChannel getAnimJump() {
        return animJump;
    }

    public AnimationChannel getCurrentAnimationChannel() {
        return texture.getAnimationChannel();
    }
}