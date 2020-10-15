package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 * AnimationComponent class. Creates animated textures and rotates the texture based on movement direction.
 */
public class AnimationComponent extends Component {

    private int timer = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public AnimationComponent(String idleImage, String walkImage) {
        animIdle = new AnimationChannel(FXGL.image(idleImage), 1, 60, 60, Duration.seconds(1), 0, 0);
        animWalk = new AnimationChannel(FXGL.image(walkImage), 4, 70, 60, Duration.seconds(1), 0, 3);

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

    /**
     * Loops the walk anim when timer is bigger than 0 and otherwise loops the idle anim.
     */
    @Override
    public void onUpdate(double tpf) {

        if (timer != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            timer = (int) (timer * 0.9);

            if (timer < 1) {
                timer = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    /**
     * Sets the time walk anim should continue after moveRight method stops being called and rotates the texture to the right.
     */
    public void moveRight() {
        timer = 100;

        getEntity().setScaleX(1);
    }

    /**
     * Sets the time walk anim should continue after moveLeft method stops being called and rotates the texture to the left.
     */
    public void moveLeft() {
        timer = 100;

        getEntity().setScaleX(-1);
    }
}