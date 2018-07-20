package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class MusicDrawable {

    private String TAG;

    private Drawable mGraphic;

    protected int width;
    protected int height;
    protected int mXPos;
    protected int mYPos;
    protected int mXOff;
    protected int mYOff;
    protected int mHitBuffer;

    public MusicDrawable(String tag, Drawable graphic, int w, int h, int x, int y, int xOff, int yOff) {
        TAG = tag;
        width = w;
        height = h;
        mXPos = x;
        mYPos = y;
        mXOff = xOff;
        mYOff = yOff;
        mHitBuffer = width/2; // The hitbox extends this far on each side
        mGraphic = graphic;
    }

    public int getWidth() {
        return width;
    }

    public Rect getHitBox() {
        return new Rect(mXPos- mHitBuffer, mYPos, mXPos + mHitBuffer, mYPos + 1);
    }

    public boolean collidesWith(MusicDrawable other) {
        boolean hit = this.getHitBox().contains(other.getHitBox());

        if (hit)
            Log.d(TAG, String.format("Collision (%d,%d) with %s (%d,%d)", mXPos, mYPos, other.TAG, other.mXPos, other.mYPos));

        return hit;
    }

    public void draw(Canvas canvas) {
        int x = mXPos - mXOff;
        int y = mYPos - mYOff;
        Log.d(TAG, String.format("(%d,%d,%d,%d)", x, y, x + width, y + height));
        mGraphic.setBounds(x, y, x + width, y + height);
        mGraphic.draw(canvas);
    }
}
