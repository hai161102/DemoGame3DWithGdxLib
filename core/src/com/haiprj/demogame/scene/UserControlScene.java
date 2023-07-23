package com.haiprj.demogame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haiprj.demogame.base.Scene;

public class UserControlScene extends Scene {
    public SpriteBatch spriteBatch;

    private Texture texture;
    public UserControlScene() {
        spriteBatch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
    }

    public void update(float dt) {
        this.spriteBatch.begin();
        //do anythings
        this.spriteBatch.draw(texture, 0, 0);
        this.spriteBatch.end();
    }

    public void dispose() {
        this.spriteBatch.dispose();
    }
}
