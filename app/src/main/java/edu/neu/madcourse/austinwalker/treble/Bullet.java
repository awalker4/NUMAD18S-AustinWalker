package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet extends MusicDrawable {

    private final Paint mColor = new Paint();

    private int speedX = -20;
    private int speedY = 0;

    public Bullet(int x, int y) {
        super("Bullet", null, 50, 20, x, y, 25, 10);
        mColor.setColor(Color.BLACK);
    }

    public void tick() {
        mXPos += speedX;
        mYPos += speedY;
    }

    public void reverse() {
        speedX *= -1;
    }

    @Override
    public void draw(Canvas canvas) {
        int x = mXPos - mXOff;
        int y = mYPos - mYOff;
        canvas.drawRect(x, y, x + width, y + height, mColor);
    }

    public boolean isReverse() {
        return speedX > 0;
    }
}
