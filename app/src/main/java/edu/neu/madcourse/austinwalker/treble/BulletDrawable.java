package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BulletDrawable extends MusicDrawable {

    private final Paint mColor = new Paint();

    private static int BULLET_SPEED_X = -1;
    private int speedX;
    private int speedY;

    public BulletDrawable(StaffView staffView, int staffPosition, int staffRank) {
        super("Bullet", staffView, 0, 50, 20, 25, 10, staffPosition, staffRank);
        mColor.setColor(Color.BLACK);
        speedX = BULLET_SPEED_X;
        speedY = 0;
    }

    public static void setBulletSpeedX(int speed) {
        BULLET_SPEED_X = speed * -1; // We're initially travelling backwards
    }

    public void tick() {
        mStaffPosition += speedX;

        // HACK: I doubled the tick rate but I only want bullets to jump by 4
        if (mStaffPosition % 2 == 0)
            mStaffRank += speedY;
    }

    public void deflect() {
        speedX *= -1;
    }

    public void deflectUp() {
        deflect();
        speedY = 1;
    }

    public void deflectDown() {
        deflect();
        speedY = -1;
    }

    @Override
    public void draw(Canvas canvas) {
        int x = mStaffView.getXForStaffLocation(mStaffPosition) - mXOff;
        int y = mStaffView.getYForRank(mStaffRank) - mYOff;

        canvas.drawRect(x, y, x + mWidth, y + mHeight, mColor);
    }

    public boolean isReverse() {
        return speedX > 0;
    }
}
