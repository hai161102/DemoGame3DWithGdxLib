package com.haiprj.demogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.haiprj.demogame.base.BaseGame;
import com.haiprj.demogame.screens.MainScreen;
import com.haiprj.demogame.utils.Data;

public class Main extends BaseGame {


	@Override
	public void create () {
		super.create();
		setScreen(new MainScreen());
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(Data.SKY_COLOR.x, Data.SKY_COLOR.y, Data.SKY_COLOR.z, 1);
		super.render();
	}
}
