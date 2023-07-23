package com.haiprj.demogame.base;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.haiprj.demogame.utils.loader.G3DJLoader;

public abstract class BaseModel extends ModelInstance{
    protected Vector3 position;
    protected Vector3 size;
    protected Vector3 direction;
    protected AnimationController animationController;
    public int loopCount = 1;

    public BaseModel(String filename) {
        super(G3DJLoader.getInstance().load(filename));
        this.animationController = new AnimationController(this);
        this.setPosition(this.transform.getTranslation(new Vector3()));
    }

    public void setAnimation(Animation source, AnimationController.AnimationListener animationListener) {
        this.animationController.setAnimation(source.id, loopCount, animationListener);
    }
    public void update(float dt, ModelBatch modelBatch, Environment environment) {
        modelBatch.render(this, environment);
        if (this.animationController != null) {
            this.animationController.update(dt);
        }
    }

    public void dispose() {
        this.model.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getPositionForCam() {
        Vector3 p = position;
        BoundingBox b = new BoundingBox();
        this.calculateBoundingBox(b);
        p.y -= b.getHeight();
        return p;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
        this.transform.setTranslation(this.position);
    }

    public void setPosition(float x, float y, float z) {
        setPosition(new Vector3(x, y, z));
    }

    public Vector3 getSize() {
        return size;
    }

    public void setSize(Vector3 size) {
        this.size = size;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public void rotate(Vector3 rotateAxis, float degree) {
        this.transform.rotate(rotateAxis, degree);
    }
}
