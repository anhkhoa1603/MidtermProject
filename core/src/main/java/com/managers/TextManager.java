package com.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.managers.GameManager;

public class TextManager {
    static BitmapFont font;
    static float width, height;

    public static void initialize(float width, float height) {
        font = new BitmapFont();
        TextManager.width = width;
        TextManager.height = height;

        font.setColor(Color.WHITE);

        font.getData().setScale(width/500f);
    }

    public static void displayMessage(SpriteBatch batch) {
        float fontWidth = 100;

        font.draw(batch, "Score: "+ GameManager.score, width*0.8f, height*0.95f);
    }
}
