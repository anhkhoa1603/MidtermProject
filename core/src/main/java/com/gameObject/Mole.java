package com.gameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.managers.GameManager;

public class Mole {
    public Sprite moleSprite;
    public Sprite stunSprite;
    public Vector2 position = new Vector2();
    public float height, width;

    public float scaleFactor = 4000f;

    public enum State {GOINUP, GOINDOWN, UNDERGROUND, STUNNED};
    public State state = State.GOINUP;
    public float currentHeight = 0f;
    public float speed = 2f;

    public float timeUnderGround = 0f;
    public float maxTimeUnderGround = 0.8f;

    public float stunTime = 0.1f;
    public float stunCounter = 0f;

    public void update() {
        switch (state) {
            case STUNNED:
                if (stunCounter >= stunTime) {
                    state = State.UNDERGROUND;
                    stunCounter = 0f;
                    currentHeight = 0f;
                    randomizeWaitTime();
                } else {
                    stunCounter += Gdx.graphics.getDeltaTime();
                }
                break;
            case UNDERGROUND:
                if (timeUnderGround >= maxTimeUnderGround) {
                    state = State.GOINUP;
                    timeUnderGround = 0f;
                }
                else {
                    timeUnderGround += Gdx.graphics.getDeltaTime();
                }
                break;
            case GOINUP:
                currentHeight +=speed;
                if (currentHeight >= height) {
                    currentHeight = height;
                    state = State.GOINDOWN;
                }
                break;
            case GOINDOWN:
                currentHeight -= speed;
                if (currentHeight <= 0) {
                    currentHeight = 0f;
                    state = State.UNDERGROUND;
                }
                break;
        }

        // draw only some portion of the mole image, depending on height
        moleSprite.setRegion(0, 0, (int) (width / scaleFactor), (int) (currentHeight / scaleFactor));
        moleSprite.setSize(moleSprite.getWidth(), currentHeight);
    }

    public void render(SpriteBatch batch) {
        moleSprite.draw(batch);
        if (state == State.STUNNED) {
            stunSprite.draw(batch);
        }
    }

    public void randomizeWaitTime() {
        maxTimeUnderGround = (float) Math.random()*2f;
    }

    public boolean handleTouch (float touchX, float touchY) {
        if ((touchX>=position.x) && touchX<=(position.x+width) && (touchY>=position.y) && touchY<=(position.y+currentHeight)) {
            stunSprite.setPosition(position.x + width - (stunSprite.getWidth()/2), position.y + currentHeight - (stunSprite.getHeight()/2));
            state = State.STUNNED;
            GameManager.hitSound.play();
            return true;
        }
        return false;
    }
}
