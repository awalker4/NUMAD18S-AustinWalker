package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class MusicDrawable {

    private String TAG;

    private Drawable mGraphic;

    protected StaffView mStaffView;

    protected int mWidth;
    protected int mHeight;
    protected int mStaffPosition;
    protected int mStaffRank;
    protected int mXOff;
    protected int mYOff;
    protected int mHitBuffer;

    public MusicDrawable(String tag, StaffView staff, int graphicID, int w, int h, int xOff, int yOff, int staffPosition, int staffRank) {
        TAG = tag;
        mStaffView = staff;

        if (graphicID != 0)
            mGraphic = mStaffView.getResources().getDrawable(graphicID);

        mWidth = w;
        mHeight = h;
        mXOff = xOff;
        mYOff = yOff;
        mStaffPosition = staffPosition;
        mStaffRank = staffRank;
        mHitBuffer = mWidth / 2; // The hitbox extends this far on each side
    }

    public Rect getHitBox() {
        int x = mStaffView.getXForStaffLocation(mStaffPosition);
        int y = mStaffView.getYForStaffLocation(mStaffRank);
        return new Rect(x - mHitBuffer, y, x + mHitBuffer, y + 1);
    }

    public boolean collidesWith(MusicDrawable other) {
        boolean hit = this.getHitBox().contains(other.getHitBox());

        if (hit)
            Log.d(TAG, String.format("Collision with %s at pos %d", other.TAG, mStaffRank));

        return hit;
    }

    public void draw(Canvas canvas) {
        int x = mStaffView.getXForStaffLocation(mStaffPosition) - mXOff;
        int y = mStaffView.getYForStaffLocation(mStaffRank) - mYOff;

        Log.d(TAG, String.format("draw: (%d,%d,%d,%d)", x, y, x + mWidth, y + mHeight));

        mGraphic.setBounds(x, y, x + mWidth, y + mHeight);
        mGraphic.draw(canvas);
    }
}
