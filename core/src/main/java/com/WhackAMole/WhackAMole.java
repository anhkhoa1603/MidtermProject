package com.WhackAMole;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.managers.GameManager;
import com.managers.InputManager;

public class WhackAMole extends ApplicationAdapter {
    SpriteBatch batch; // spritebatch for drawing
    OrthographicCamera camera;
    @Override
    public void create () {
        // get window dimensions and get our viewport dimensions
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        // set our viewport to window dimensions
        camera = new OrthographicCamera();
        // center the camera at w/2, h/2
        camera.setToOrtho(false, width, height);

        batch = new SpriteBatch();
        //initialize the game
        GameManager.initialize(width, height);
    }

    @Override
    public void dispose () {
        super.dispose();
        //dispose the batch and the texture
        batch.dispose();
        GameManager.dispose();
    }

    @Override
    public void render () {
        // clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        InputManager.handleInput(camera);

        // set the spritebatch's drawing view to the camera's view
        batch.setProjectionMatrix(camera.combined);

        // render the game object
        batch.begin();
        GameManager.renderGame(batch);
        batch.end();
    }
}
