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

public class NinjaModel extends BaseModel {

    private AnimationKey animationKey = AnimationKey.WALK;
    public NinjaModel(String filename) {
        super(filename);
        System.out.println("Ninja Start---------------------------------------");
        for (Animation animation : this.model.animations) {
            System.out.println(animation.id);
        }
        System.out.println("Ninja End------------------------------------------");
        setAnimationKey(animationKey);
    }


    @Override
    public void update(float dt, ModelBatch modelBatch, Environment environment) {
        super.update(dt, modelBatch, environment);

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
                if (NinjaModel.this.animationKey != AnimationKey.IDLE) {
                    NinjaModel.this.setAnimationKey(AnimationKey.IDLE);

                }
                if (animationKey == AnimationKey.TURN_LEFT) {
                    rotate(new Vector3(0,1,0), 45);
                }
            }
            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {
                if (NinjaModel.this.animationKey == AnimationKey.IDLE) {
                    if (animation.loopCount <= 1) {
                        NinjaModel.this.loopCount = 1000;
                        NinjaModel.this.setAnimationKey(AnimationKey.IDLE);
                    }
                }


            }
        });
    }


    private void setAnimationKey(AnimationKey animationKey, AnimationController.AnimationListener listener) {
        this.animationKey = animationKey;
        if (this.animationKey == AnimationKey.IDLE) {
            this.loopCount = 1000;
        }else this.loopCount = 1;
        setAnimation(Objects.requireNonNull(animationKey.getAnimation(this.model)), listener);
    }
    @Override
    public void rotate(Vector3 rotateAxis, float degree) {
        super.rotate(rotateAxis, degree);

    }

    public enum AnimationKey {
        IDLE,
        DEAD,
        KICK,
        ROUND_KICK,
        PUNCH_0,
        PUNCH_1,
        PUNCH_2,
        RUN,
        WALK,
        TURN_LEFT,
        TURN_RIGHT;

        @NotNull
        @Contract(pure = true)
        public String getId() {
            switch (this) {
                case DEAD:
                    return "dead";
                case KICK:
                    return "kick";
                case ROUND_KICK:
                    return "round_kick";
                case PUNCH_0:
                    return "punch_0";
                case PUNCH_1:
                    return "punch_1";
                case PUNCH_2:
                    return "punch_2";
                case RUN:
                    return "run";
                case WALK:
                    return "walk";
                case TURN_LEFT:
                    return "turn_left";
                case TURN_RIGHT:
                    return "turn_right";
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
