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

    // Quarter note constants
    private final int mNoteXPos = 250;

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

    private MusicDrawable mClef;
    private MusicDrawable mNote;
    private MusicDrawable mAlien;
    private Bullet mBullet;

    public StaffView(Context context) {
        super(context);
    }

    public StaffView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void setup() {
        if (isTreble) {
            int x = mViewPaddingX + 130; // FIXME magic
            int y = getYFromStaffPos(2); // Center on G
            mClef = new TrebleClef(this, x, y);
        } else {
            // TODO: fix sizing
//            Drawable bassClef = getResources().getDrawable(R.drawable.bass_clef);
//            bassClef.setBounds(0, 0, 250, 400);
//            bassClef.draw(canvas);
        }
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
        int x = mViewPaddingX + mNoteXPos;
        int y = getYFromStaffPos(location);

        mNote = new QuarterNote(this, x, y);

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
    }

    public void drawAlien(int position) {
        int x = 550;
        int y = getYFromStaffPos(position);

        if (mAlien == null)
            mAlien = new Enemy(this, x, y);

        if (mBullet == null)
            mBullet = new Bullet(x, y);

        tick();
    }

    private void tick() {
        if (mBullet != null) {
            mBullet.tick();

            if (mNote.collidesWith(mBullet)) {
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
        canvas.drawRect(mViewPaddingX, mStaffStartY, mViewPaddingX + mVerticalBarWidth, mStaffStartY + 4 * mStaffSpacing, mStaffColor);

        // Clef
        // https://pixabay.com/en/bass-clef-music-clef-symbol-1425777
        if (isTreble)
            mClef.draw(canvas);
    }

    // Add lines above or below staff if called for
    private void addLedgerLines(Canvas canvas) {
        for (int i = 0; i < mLedgerLinesUp; i++) {
            int y = mStaffStartY - i * mStaffSpacing;
            int x = mViewPaddingX + mNoteXPos - mLedgerWidth / 2;

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }

        for (int i = 0; i < mLedgerLinesDown; i++) {
            int y = mStaffStartY + (5 + i) * mStaffSpacing;
            int x = mViewPaddingX + mNoteXPos - mLedgerWidth / 2;

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }
    }

    // Debugging function - draw a line across the staff at x
    private void drawVertical(Canvas canvas, int x) {
        canvas.drawRect(x - 1, mStaffStartY, x + 1, mStaffStartY + 4 * mStaffSpacing, mStaffColor);
    }
}

