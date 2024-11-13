package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.gameObject.Mole;
import com.managers.TextManager;

public class GameManager {
    public static Sound hitSound;
    static Array<Mole>moles; // array of the mole
    static Texture moleTexture;
    static Texture stunTexture;

    static Texture backgroundTexture; // texture image for background
    static Sprite backgroundSprite; //sprite for background

    static Texture holeBackTexture; // texture image for background
    static Texture holeFrontTexture; // texture image for background

    static Array<Sprite> holeSprites; // array of hole sprite

    private static float MOLE_RESIZE_FACTER = 2000f;

    private static float MOLE_VERT_POSITION_FACTER = 3f;
    private static float MOLE1_HORIZ_POSITION_FACTER = 5.8f;
    private static float MOLE2_HORIZ_POSITION_FACTER = 2.4f;
    private static float MOLE3_HORIZ_POSITION_FACTER = 1.5f;

    private static float HOLE_RESIZE_FACTER = 1100f;

    public static int score;

    public static void initialize(float width, float height) {
        score = 0;
        moles = new Array<Mole>();
        moleTexture = new Texture(Gdx.files.internal("data/Mole.png"));
        stunTexture = new Texture(Gdx.files.internal("data/Stun.png"));

        for (int i = 0; i < 9; i++) {
            moles.add(new Mole());
        }

        backgroundTexture = new Texture(Gdx.files.internal("data/Background.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        backgroundSprite.setSize(width, height);
        backgroundSprite.setPosition(0, 0f);

        holeBackTexture = new Texture(Gdx.files.internal("data/HoleBack.png"));
        holeFrontTexture = new Texture(Gdx.files.internal("data/HoleFront.png"));
        holeSprites = new Array<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Sprite sprite1 = new Sprite(holeBackTexture);
                sprite1.setSize(sprite1.getWidth()*(width/HOLE_RESIZE_FACTER), sprite1.getHeight()*(height/MOLE_RESIZE_FACTER));
                sprite1.setPosition(width*(j+1)/4f - sprite1.getWidth()/2, height*(i+1)/4.4f - sprite1.getHeight());
                holeSprites.add(sprite1);
            }
        }

        for (int i = 0; i < 9; i++) {
            Mole mole = moles.get(i);
            Sprite sprite = holeSprites.get(i);

            mole.moleSprite = new Sprite(moleTexture);

            mole.stunSprite = new Sprite(stunTexture);

            float scaleFacter = width/2000f;
            mole.scaleFactor = scaleFacter;
            mole.width = mole.moleSprite.getWidth()*(scaleFacter);
            mole.height = mole.moleSprite.getHeight()*(scaleFacter);
            mole.moleSprite.setSize(mole.width, mole.height);

            mole.position.x = (((2*sprite.getX() + sprite.getWidth())/2) - (mole.moleSprite.getWidth()/2));
            mole.position.y = (sprite.getY()+sprite.getHeight()/5f);

            mole.moleSprite.setPosition(mole.position.x, mole.position.y);
            mole.stunSprite.setSize(mole.width/2f, mole.height/2f);
            mole.randomizeWaitTime();
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Sprite sprite2 = new Sprite(holeFrontTexture);
                sprite2.setSize(sprite2.getWidth()*(width/HOLE_RESIZE_FACTER), sprite2.getHeight()*(height/MOLE_RESIZE_FACTER));
                sprite2.setPosition(width*(j+1)/4f - sprite2.getWidth()/2, height*(i+1)/4.4f - sprite2.getHeight());
                holeSprites.add(sprite2);
            }
        }

        TextManager.initialize(width, height);

        hitSound = Gdx.audio.newSound(Gdx.files.internal("data/HitSound.mp3"));
    }

    public static void renderGame(SpriteBatch batch) {
        backgroundSprite.draw(batch);

        for (Sprite sprite : holeSprites)
            sprite.draw(batch);

        for (Mole mole : moles) {
            mole.update();
            mole.render(batch);
        }

        TextManager.displayMessage(batch);
    }

    public static void dispose() {
        backgroundTexture.dispose();
        holeBackTexture.dispose();
        moleTexture.dispose();
        stunTexture.dispose();
        hitSound.dispose();
    }
}
