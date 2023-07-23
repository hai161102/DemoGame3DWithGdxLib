package com.haiprj.demogame.utils.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Null;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
public class GltfLoader {
    private static GltfLoader instance;

    private final GLTFLoader gltfLoader;
    private GltfLoader() {
        gltfLoader = new GLTFLoader();
    }

    public static GltfLoader getInstance() {
        if (instance == null)
            instance = new GltfLoader();
        return instance;
    }

    public SceneAsset[] load(@Null boolean withData, String... filename) {
        SceneAsset[] sceneAssets = new SceneAsset[filename.length];
        for (int i = 0; i < filename.length; i++) {
            sceneAssets[i] = gltfLoader.load(Gdx.files.internal(filename[i]), withData);
        }
        return sceneAssets;
    }
}
