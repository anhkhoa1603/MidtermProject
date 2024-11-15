package com.WhackAMole;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.managers.GameManager;
import com.managers.InputManager;

public class WhackAMole extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    @Override
    public void create () {
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();

        camera.setToOrtho(false, width, height);

        batch = new SpriteBatch();

        GameManager.initialize(width, height);
    }

    @Override
    public void dispose () {
        super.dispose();
        batch.dispose();
        GameManager.dispose();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        InputManager.handleInput(camera);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        GameManager.renderGame(batch);
        batch.end();
    }
}
