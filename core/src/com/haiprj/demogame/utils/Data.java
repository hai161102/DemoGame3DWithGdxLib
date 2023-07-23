package com.haiprj.demogame.utils;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class Data {
    public final static float GAME_SIZE = 0.1f;
    public final static Vector3 SKY_COLOR = new Vector3((float) 135 / 255, (float) 206 / 255, (float) 235 / 255);
    public static void scaleSize(ModelInstance modelInstance) {
        BoundingBox boundingBox = new BoundingBox();
        modelInstance.calculateBoundingBox(boundingBox);
        Vector3 size = new Vector3();
        boundingBox.getDimensions(size);
        float minSize = size.x;
        if (minSize > size.y) minSize = size.y;
        if (minSize > size.z) minSize = size.z;
        float scale = GAME_SIZE / minSize;
        modelInstance.transform.scale(scale, scale, scale);
    }

    public static void scaleSize(ModelInstance modelInstance, float sizeTime) {
        BoundingBox boundingBox = new BoundingBox();
        modelInstance.calculateBoundingBox(boundingBox);
        Vector3 size = new Vector3();
        boundingBox.getDimensions(size);
        float minSize = size.x;
        if (minSize > size.y) minSize = size.y;
        if (minSize > size.z) minSize = size.z;
        float scale = GAME_SIZE / minSize;
        scale *= sizeTime;
        modelInstance.transform.scale(scale, scale, scale);
    }
}
