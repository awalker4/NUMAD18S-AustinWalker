package edu.neu.madcourse.austinwalker.treble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class StaffView extends View {
    private static final String TAG = "StaffView";

    // View constants
    private static final int mViewPaddingX = 20;

    // Staff constants
    private static final int mStaffSpacing = 60;
    private static final int mLineThickness = 2;
    public static final int mVerticalBarWidth = 10;
    private static final int mLedgerWidth = 100;
    private final int mClefOffset = 50;
    private final int mNoteOffset = 300;

    // Alien constants
    private final int[] mUFOPositions = {600};

    private final Paint mStaffColor = new Paint();

    private int mViewWidth;
    private int mViewHeight;
    private int mStaffStartY;
    private int mStaffEndY;

    private int mLedgerLinesDown = 0;
    private int mLedgerLinesUp = 0;

    private boolean isTreble = true;
    private boolean isClosed = true;

    private MusicDrawable mClef;
    private MusicDrawable mNote;
    private MusicDrawable mAlien;
    private BulletDrawable mBullet;

    public StaffView(Context context) {
        super(context);
    }

    public StaffView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void setup() {
        int x = mViewPaddingX + mClefOffset;
        int y;

        if (isTreble) {
            y = getYFromStaffPos(2); // Center on G
            mClef = new TrebleClefDrawable(this, x, y);
        } else {
            y = getYFromStaffPos(6); // Center on F
            mClef = new BassClefDrawable(this, x, y);
        }
    }

    public void setTreble(boolean treble) {
        isTreble = treble;

        setup();
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        mViewWidth = w;
        mViewHeight = h;

        // Center the staff vertically
        // (get the center of the view, then go up by half the staff height)
        mStaffStartY = (mViewHeight / 2) - (4 * mStaffSpacing / 2);
        mStaffEndY = mStaffStartY + (4 * mStaffSpacing);

        setup();

        Log.d(TAG, "onSizeChanged: " + mViewWidth + "X" + mViewHeight);
    }

    // Return a y value for all possible staff positions
    // starting from the bottom line
    private int getYFromStaffPos(int position) {
        return mStaffEndY - (position * mStaffSpacing / 2);
    }

    // Draws a note at staff location from the bottom line up
    public void drawNote(int location) {
        int x = mViewPaddingX + mNoteOffset;
        int y = getYFromStaffPos(location);

        mNote = new QuarterNoteDrawable(this, x, y);

        mLedgerLinesDown = mLedgerLinesUp = 0;
        if (location < 0) {
            mLedgerLinesDown = (0 - location) / 2;
        } else if (location > 10) {
            mLedgerLinesUp = (location - 10) / 2;
        }

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        mStaffColor.setColor(Color.BLACK);

        drawStaff(canvas);
        addLedgerLines(canvas);

        if (mAlien != null)
            mAlien.draw(canvas);

        if (mNote != null)
            mNote.draw(canvas);

        if (mBullet != null)
            mBullet.draw(canvas);

//        drawVertical(canvas, mViewPaddingX + mClefOffset);
    }

    public void drawAlien(int position) {
        int x = 550;
        int y = getYFromStaffPos(position);

        if (mAlien == null)
            mAlien = new EnemyDrawable(this, x, y);

        if (mBullet == null)
            mBullet = new BulletDrawable(x, y);

        tick();
    }

    private void tick() {
        if (mBullet != null) {
            mBullet.tick();

            if (!mBullet.isReverse() && mNote.collidesWith(mBullet)) {
                mBullet.reverse();
            } else if (mBullet.isReverse() && mAlien.collidesWith(mBullet)) {
                mAlien = null;
                mBullet = null;
            }
        }
    }

    private void drawStaff(Canvas canvas) {
        // Staff lines
        for (int i = 0; i < 5; i++) {
            int vert = i * mStaffSpacing + mStaffStartY;

            if (vert < mViewHeight)
                canvas.drawRect(mViewPaddingX, vert, mViewWidth - mViewPaddingX, vert + mLineThickness, mStaffColor);

            Log.d(TAG, String.format("drawStaff: (vert=%d)", vert));
        }

        // Right-hand line
        drawVertical(canvas, mViewPaddingX, mLineThickness);

        // Left-hand line
        if (isClosed) {
            int staffEndX = mViewWidth - mViewPaddingX;
            drawVertical(canvas, staffEndX, -10);
            drawVertical(canvas, staffEndX - 20, mLineThickness);
        }

        // Clef
        if (mClef != null)
            mClef.draw(canvas);
    }

    // Add lines above or below staff if called for
    private void addLedgerLines(Canvas canvas) {
        for (int i = 0; i < mLedgerLinesUp; i++) {
            int y = mStaffStartY - i * mStaffSpacing;
            int x = mViewPaddingX + mNoteOffset - mLedgerWidth / 2;

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }

        for (int i = 0; i < mLedgerLinesDown; i++) {
            int y = mStaffStartY + (5 + i) * mStaffSpacing;
            int x = mViewPaddingX + mNoteOffset - mLedgerWidth / 2;

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }
    }

    // Draw a line across the staff at x
    private void drawVertical(Canvas canvas, int x, int width) {
        int topLine = getYFromStaffPos(8);
        int bottomLine = getYFromStaffPos(0);

        canvas.drawRect(x, topLine, x + width, bottomLine, mStaffColor);
    }
}

