package com.haiprj.demogame.utils.light;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.haiprj.demogame.utils.Log;

public class LightData extends DirectionalShadowLight {

    private Environment environment;
    private ModelBatch shadowBatch;
    private Camera camera;
    public LightData(int shadowMapWidth, int shadowMapHeight, float shadowViewportWidth, float shadowViewportHeight, float shadowNear, float shadowFar) {
        super(shadowMapWidth, shadowMapHeight, shadowViewportWidth, shadowViewportHeight, shadowNear, shadowFar);
        this.set(0.8f, 0.8f, 0.8f, -10f, -10f, -10f);

    }

    public void setData(Environment environment, Camera camera) {
        this.environment = environment;
        this.camera = camera;
        load();
    }

    private void load() {
        this.environment.add(this);
        this.environment.shadowMap = this;
        shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    public void update(float dt, ModelInstance... data) {
        if (this.environment != null && this.camera != null) {
            this.begin(Vector3.Zero, camera.direction);
            shadowBatch.begin(this.getCamera());
            for (ModelInstance d : data) {

                shadowBatch.render(d);
            }
            shadowBatch.end();
            this.end();
        }
    }
}
