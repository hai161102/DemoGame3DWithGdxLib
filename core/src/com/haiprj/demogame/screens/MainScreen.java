package com.haiprj.demogame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.haiprj.demogame.base.BaseScreen;
import com.haiprj.demogame.models.ActorModel;
import com.haiprj.demogame.models.MonsterModel;
import com.haiprj.demogame.models.NinjaModel;
import com.haiprj.demogame.scene.UserControlScene;
import com.haiprj.demogame.utils.Data;
import com.haiprj.demogame.utils.light.LightData;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import static com.haiprj.demogame.utils.Data.GAME_SIZE;

public class MainScreen extends BaseScreen {

    private final Environment environment;
    private final PerspectiveCamera camera;
    private final CameraInputController cameraController;
    private final ModelBatch modelBatch;
    private final ActorModel ninjaModel;
    private final MonsterModel monsterModel;
    private final ModelInstance planeInstance;
    private final LightData lightData;
    private UserControlScene userControlScene;

    public MainScreen() {

        environment = new Environment();
        lightData = new LightData(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                GAME_SIZE * Gdx.graphics.getWidth() / 10f,
                GAME_SIZE * Gdx.graphics.getHeight() / 10f,
                0.01f,
                10000);
        modelBatch = new ModelBatch();

        // Create a perspective camera with some sensible defaults
        camera = new PerspectiveCamera(67f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f * GAME_SIZE, 10f * GAME_SIZE, 50f * GAME_SIZE);
        camera.near = 0.01f;
        camera.far = 1000f * GAME_SIZE;
        camera.update();

        lightData.setData(environment, camera);
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));

        // Import and instantiate our model (called "myModel.g3dj")
        ModelBuilder modelBuilder = new ModelBuilder();
        ninjaModel = new ActorModel("model/actor/actor_all.g3dj");
        monsterModel = new MonsterModel("model/monster/monster_all_animation.g3dj");
        System.out.println(ninjaModel);
        ninjaModel.setPosition(4 * GAME_SIZE, 0f, 0f);
        Data.scaleSize(ninjaModel);
        Data.scaleSize(monsterModel, 3);
        Material material = new Material(ColorAttribute.createDiffuse(new Color(0, 0.8f, 0, 1)));
        Model plane = modelBuilder.createBox(100 * GAME_SIZE, 10 * GAME_SIZE, 100 * GAME_SIZE, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        planeInstance = new ModelInstance(plane);
        planeInstance.transform.translate(0, -5 * GAME_SIZE, 0);
        cameraController = new CameraInputController(camera);
        InputMethodListener inputMethodListener = new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {

            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        };

        cameraController.scrollFactor *= GAME_SIZE;

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                System.out.println(keycode);
                switch (keycode) {
                    case 66:
                        monsterModel.attack();
                        break;
                    case 8:
                        monsterModel.useSkill();
                        break;
                    case 19:
                        ninjaModel.moveFront();
                        break;
                    case 20:
                        monsterModel.moveBack();
                        break;

                }
                cameraController.keyDown(keycode);
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                cameraController.keyUp(keycode);
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                cameraController.keyTyped(character);
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cameraController.touchDown(screenX, screenY, pointer, button);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                cameraController.touchUp(screenX, screenY, pointer, button);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                cameraController.touchDragged(screenX, screenY, pointer);
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                cameraController.mouseMoved(screenX, screenY);
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
//                cameraController.scrolled(amountX, amountY);
                return false;
            }
        });
        this.userControlScene = new UserControlScene();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // Clear the stuff that is left over from the previous render cycle

//        camera.position.set(ninjaModel.getPositionForCam());
        cameraController.update();
        camera.update();

        camera.position.set(monsterModel.getPosition().x, monsterModel.getPosition().y + GAME_SIZE * 5, monsterModel.getPosition().z - GAME_SIZE * 10);
        lightData.update(delta, planeInstance, ninjaModel, monsterModel);


        // Let our ModelBatch take care of efficient rendering of our ModelInstance
        modelBatch.begin(camera);
        modelBatch.render(planeInstance, environment);
        ninjaModel.update(delta, modelBatch, environment);
        monsterModel.update(delta, modelBatch, environment);
        modelBatch.end();

//        userControlScene.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        modelBatch.dispose();
        ninjaModel.dispose();
        monsterModel.dispose();
    }
}
