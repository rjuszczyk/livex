package com.mygdx.livex;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Radek on 22.02.2016.
 */
public class PositionedTexture extends Texture {
    public int mX;
    public int mY;
    public static int screenHeight;
    public static int screenWidth;
    public PositionedTexture(String file, int x, int y) {
        super(file);
        mX= x;
        mY= screenHeight - y - getHeight();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this, mX, mY);
    }
}
