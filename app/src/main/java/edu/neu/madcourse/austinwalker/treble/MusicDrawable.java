package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class MusicDrawable {

    private static final String TAG = "MusicDrawable";

    private Drawable mGraphic;

    protected int width;
    protected int height;
    protected int mXPos;
    protected int mYPos;
    protected int mXOff;
    protected int mYOff;

    public MusicDrawable(Drawable graphic, int w, int h, int x, int y, int xOff, int yOff) {
        width = w;
        height = h;
        mXPos = x;
        mYPos = y;
        mXOff = xOff;
        mYOff = yOff;
        mGraphic = graphic;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return mXPos;
    }

    public int getY() {
        return mYPos;
    }

    public boolean collidesWith(int x, int y) {
        return true;
    }

    public void draw(Canvas canvas) {
        int x = mXPos - mXOff;
        int y = mYPos - mYOff;
        Log.d(TAG, String.format("(%d,%d,%d,%d)", x, y, x + width, y + height));
        mGraphic.setBounds(x, y, x + width, y + height);
        mGraphic.draw(canvas);
    }
}
