package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BulletDrawable extends MusicDrawable {

    private final Paint mColor = new Paint();

    private int speedX = -20;
    private int speedY = 0;

    public BulletDrawable(StaffView staffView, int x, int staffPos) {
        super("Bullet", staffView, 0, 50, 20, 25, 10,  x, staffPos);
        mColor.setColor(Color.BLACK);
    }

    public void tick() {
        mXPos += speedX;
        mStaffPos += speedY; // TODO: angled bullets
    }

    public void reverse() {
        speedX *= -1;
    }

    @Override
    public void draw(Canvas canvas) {
        int x = mXPos - mXOff;
        int y = mStaffView.getYForStaffLocation(mStaffPos) - mYOff;

        canvas.drawRect(x, y, x + mWidth, y + mHeight, mColor);
    }

    public boolean isReverse() {
        return speedX > 0;
    }
}
