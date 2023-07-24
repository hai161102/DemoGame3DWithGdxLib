package com.haiprj.demogame.models;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.demogame.base.BaseModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.haiprj.demogame.utils.Data.GAME_SIZE;

public class ActorModel extends BaseModel {

    private AnimationKey animationKey = AnimationKey.IDLE;
    private boolean canAction = true;
    public ActorModel(String filename) {
        super(filename);
        setAnimationKey(animationKey);
    }
    public void setAnimationKey(final AnimationKey animationKey) {
        this.animationKey = animationKey;
        if (this.animationKey == AnimationKey.IDLE) {
            this.loopCount = 1000;
        }
        else this.loopCount = 1;

        setAnimation(Objects.requireNonNull(animationKey.getAnimation(this.model)), new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {
//                if (animationKey == AnimationKey.WALKING) {
//                    setPosition(getPosition().x, getPosition().y, getPosition().z + GAME_SIZE * 10);
//                }
                if (animationKey == AnimationKey.WALKING) {
                    setAnimationKey(AnimationKey.STOP_WALKING);
                    return;
                }

                if (animationKey != AnimationKey.IDLE) {
                    setAnimationKey(AnimationKey.IDLE);
                }
                canAction = true;
            }
            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {
                if (animationKey == AnimationKey.IDLE) {
                    if (animation.loopCount <= 1) {
                        loopCount = 1000;
                        setAnimationKey(AnimationKey.IDLE);
                    }
                }


            }
        });
    }

    @Override
    public void update(float dt, ModelBatch modelBatch, Environment environment) {
        super.update(dt, modelBatch, environment);
        System.out.println("Location: " + this.model.getAnimation(animationKey.getId()).nodeAnimations.get(0).node.translation.toString());
    }

    public void moveFront() {
        if (canAction) {
            setAnimationKey(AnimationKey.WALKING);
            canAction = false;
        }
    }

    public enum AnimationKey {
        IDLE,
        DYING,
        KICK,
        ROUND_KICK,
        PUNCH,
        RUN,
        WALKING,
        START_WALKING,
        STOP_WALKING;

        @NotNull
        @Contract(pure = true)
        public String getId() {
            switch (this) {
                case DYING:
                    return "dying";
                case KICK:
                    return "kicking";
                case ROUND_KICK:
                    return "kicking_round";
                case PUNCH:
                    return "punching_combo";
                case RUN:
                    return "running";
                case WALKING:
                    return "walking";
                case START_WALKING:
                    return "start_walking";
                case STOP_WALKING:
                    return "stop_walking";
                case IDLE:
                default:
                    return "idle";
            }
        }
        @Nullable
        public Animation getAnimation(@NotNull Model model) {
            for (Animation animation : model.animations) {
                if (getId().equals(animation.id)) return animation;
            }
            return null;
        }
    }
}
