package com.mygdx.livex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Radek on 22.02.2016.
 */
public class Answer {
    private final boolean mIsCorrect;
    int mX;
    int mY;
    int mX_initial;
    int mY_initial;
    Texture mSprite;
    Texture mSpriteSuccess;
    private float mAnimationSpeed = 1;
    public int targetIndex = -1;

    public Answer(int startX, int startY, Texture sprite, boolean isCorrect) {
        mSprite = sprite;
        mSpriteSuccess = new Texture("gray_square.png");
        mX = startX;
        mY = PositionedTexture.screenHeight - startY - sprite.getHeight();
        mX_initial = mX;
        mY_initial = mY;
        mIsCorrect = isCorrect;
    }

    public Answer(int startX, int startY, Texture sprite, boolean isCorrect, int targetIndex) {
        this.targetIndex = targetIndex;
        mSprite = sprite;
        mSpriteSuccess = new Texture("gray_square.png");
        mX = startX;
        mY = PositionedTexture.screenHeight - startY - sprite.getHeight();
        mX_initial = mX;
        mY_initial = mY;
        mIsCorrect = isCorrect;
    }

    public void draw(SpriteBatch batch) {
        if(!showSpriteCOrrect) {
            batch.draw(mSpriteSuccess, mX, mY);
        }
        batch.draw(mSprite, mX, mY);
    }

    public boolean isCorrect() {
        return mIsCorrect;
    }

    int mDragX_start;
    int mDragY_start;
    int mDragX_offset;
    int mDragY_offset;
    boolean mIsDragging = false;

    public boolean contains(int x, int y) {
        return x>=mX && x<mX+mSprite.getWidth() && y>=mY && y<mY+mSprite.getHeight();
    }

    public void animateOut() {
        if(!isCorrect()) {
            startAnimation(getPosition(), new Vector2((mX - 2*(600 - mX)), mY - 2*(350-mY)), com.mygdx.livex.MyGdxGame.ANIMATION_SPEED);
        }
    }

    boolean isAnimating = false;

    public void startAnimationIn() {
        startAnimation(getPosition().add(0, PositionedTexture.screenHeight), getPosition(), com.mygdx.livex.MyGdxGame.ANIMATION_SPEED);
    }

    public void startAnimationOut() {
        startAnimation(getPosition(), new Vector2((mX*0.7f+mX_initial*0.3f), mY - PositionedTexture.screenHeight), com.mygdx.livex.MyGdxGame.ANIMATION_SPEED);
    }

    public void animate(float deltaTime) {
        if(isAnimating) {
            animationProgress += deltaTime*mAnimationSpeed; //MathUtils.lerp(animationProgress, 1, deltaTime);
            if(animationProgress <0.99f) {
                mX = (int) (startAnimation.x + (endAnimation.x - startAnimation.x) * animationProgress);
                mY = (int) (startAnimation.y + (endAnimation.y - startAnimation.y) * animationProgress);
            } else {
                animationProgress = 1;
                isAnimating = false;
                mX = (int) endAnimation.x;
                mY = (int) endAnimation.y;
            }
        }
    }

    public void startAnimation(Vector2 from, Vector2 to, float speed ) {
        startAnimation.set(from);
        mAnimationSpeed = speed;
        endAnimation.set(to);
        animationProgress = 0;
        isAnimating = true;
    }

    private float animationProgress = 0;
    Vector2 startAnimation = new Vector2();
    Vector2 endAnimation = new Vector2();

    public void startDrag(int dragX, int dragY) {
        mDragX_offset = mX - dragX;
        mDragY_offset = mY - dragY;
        mDragX_start = mX_initial;
        mDragY_start = mY_initial;

        isAnimating = false;
        mIsDragging = true;
    }

    public void onDrag(int currentX, int currentY) {
        if(mIsDragging) {
            mX = currentX + mDragX_offset;
            mY = currentY + mDragY_offset;
        }
    }

    public void onEndDrag(int currentX, int currentY, EndDragCallback endDragCallback) {
        if(mIsDragging) {
            mIsDragging = false;
            endDragCallback.onEndDrag(currentX, currentY);
        }
    }

    public void setPosition(Vector2 position) {
        mX = (int) position.x;
        mY = (int) position.y;
    }

    public Vector2 getStartPosition() {
        return new Vector2(mDragX_start, mDragY_start);
    }

    public Vector2 getPosition() {
        return new Vector2(mX, mY);
    }

    boolean showSpriteCOrrect = false;

    public void showGood() {
        showSpriteCOrrect = true;
    }

    public void reset() {
        mX = mX_initial;
        mY = mY_initial;
        showSpriteCOrrect = false;
    }

    public interface EndDragCallback {
        void onEndDrag(int endX, int endY);
    }
}
