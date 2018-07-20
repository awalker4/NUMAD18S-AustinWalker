package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class MusicDrawable {

    private static final String TAG = "MusicDrawable";

    private Drawable mGraphic;

    private int mXPos;
    private int mYPos;
    private int width;
    private int height;

    public MusicDrawable(Drawable graphic, int w, int h, int x, int y) {
        width = w;
        height = h;
        mXPos = x;
        mYPos = y;
        mGraphic = graphic;
    }

    public int getWidth() {
        return width;
    }

    public void draw(Canvas canvas) {
        Log.d(TAG, String.format("(%d,%d,%d,%d)", mXPos, mYPos, mXPos + width, mYPos + height));
        mGraphic.setBounds(mXPos, mYPos, mXPos + width, mYPos + height);
        mGraphic.draw(canvas);
    }
}
