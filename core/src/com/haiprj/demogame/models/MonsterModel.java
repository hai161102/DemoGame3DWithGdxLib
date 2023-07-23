package com.haiprj.demogame.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.haiprj.demogame.base.BaseModel;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.haiprj.demogame.utils.Data.GAME_SIZE;

public class MonsterModel extends BaseModel {

    private AnimationKey animationKey = AnimationKey.IDLE;
    private float speed = 5 * GAME_SIZE;
    private boolean isFront = false, isBack = false;
    public MonsterModel(String filename) {
        super(filename);
        for (Animation animation : this.model.animations) {
            System.out.println(animation.id);
        }
        setAnimationKey(animationKey);
    }

    public void setAnimationKey(final AnimationKey animationKey) {
        this.animationKey = animationKey;
        if (this.animationKey == AnimationKey.IDLE) {
            this.loopCount = 1000;
        }
        else this.loopCount = 1;
        setAnimation(Objects.requireNonNull(animationKey.getValue(this.model.animations)), new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {
                if (MonsterModel.this.animationKey != AnimationKey.IDLE) {
                    MonsterModel.this.setAnimationKey(AnimationKey.IDLE);

                }
                isBack = isFront = false;
                canAction = true;
            }

            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {
                if (MonsterModel.this.animationKey == AnimationKey.IDLE) {
                    if (animation.loopCount <= 1) {
                        MonsterModel.this.loopCount = 1000;
                        MonsterModel.this.setAnimationKey(AnimationKey.IDLE);
                    }
                }

            }
        });
    }

    private int attackCount = 0;
    private boolean canAction = true;
    public void attack() {
        if (canAction){
            if (attackCount > 1) {
                attackCount = 0;
            }
            switch (attackCount) {
                case 0:
                    setAnimationKey(AnimationKey.ATTACK);
                    break;
                case 1:
                    setAnimationKey(AnimationKey.ATTACK_1);
                    break;
            }
            attackCount++;
            canAction = false;
        }

    }

    @Override
    public void update(float dt, ModelBatch modelBatch, Environment environment) {
        super.update(dt, modelBatch, environment);
        if (isFront) {
            setPosition(getPosition().x, getPosition().y, getPosition().z + speed * dt);
        }
        else if (isBack) setPosition(getPosition().x, getPosition().y, getPosition().z - speed * dt);
    }

    public void useSkill() {
        if (canAction) {
            setAnimationKey(AnimationKey.SKILL);
            canAction = false;
        }
    }

    public void moveForward() {
        if (canAction) {
            isFront = true;
            setAnimationKey(AnimationKey.WALK);
            canAction = false;
        }

    }

    public void moveBack() {
        if (canAction) {
            isBack = true;
            setAnimationKey(AnimationKey.WALK);
            canAction = false;
        }
    }

    public enum AnimationKey{
        IDLE,
        ATTACK,
        ATTACK_1,
        SKILL,
        WALK,
        DEAD;

        @Nullable
        public Animation getValue(Iterable<Animation> animations) {
            switch (this) {
                case ATTACK:
                    for (Animation animation : animations) {
                        if (animation.id.equals("attack_0")) return animation;
                    }
                case ATTACK_1:
                    for (Animation animation : animations) {
                        if (animation.id.equals("attack_1")) return animation;
                    }
                case SKILL:
                    for (Animation animation : animations) {
                        if (animation.id.equals("skill")) return animation;
                    }
                case WALK:
                    for (Animation animation : animations) {
                        if (animation.id.equals("walk")) return animation;
                    }
                case DEAD:
                    for (Animation animation : animations) {
                        if (animation.id.equals("dead")) return animation;
                    }
                case IDLE:
                default:

                    for (Animation animation : animations) {
                        if (animation.id.equals("idle")) return animation;
                    }
            }
            return null;
        }
    }
}
