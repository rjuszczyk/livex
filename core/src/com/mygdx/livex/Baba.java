package com.mygdx.livex;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.livex.Baba.State.CZEKA;

/**
 * Created by Radek on 22.02.2016.
 */
public class Baba {
    PositionedTexture mTexCzeka;
    PositionedTexture mTexZla;
    PositionedTexture mTexZadowolona;

    public State state = CZEKA;

    public enum State {
        CZEKA,
        ZLA,
        ZADOWOLONA
    }


    public Baba(PositionedTexture czeka, PositionedTexture dobra, PositionedTexture zla) {
        mTexCzeka = czeka;
        mTexZla = zla;
        mTexZadowolona = dobra;
    }

    public void draw(SpriteBatch batch) {
        switch (state) {
            case CZEKA:
                mTexCzeka.draw(batch);
                return;
            case ZLA:
                mTexZla.draw(batch);
                return;
            case ZADOWOLONA:
                mTexZadowolona.draw(batch);
                return;
        }
    }

}
