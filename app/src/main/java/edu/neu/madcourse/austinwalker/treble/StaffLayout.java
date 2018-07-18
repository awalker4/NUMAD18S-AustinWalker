package edu.neu.madcourse.austinwalker.treble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class StaffLayout extends View {
    private boolean isTreble = true;
    private Drawable mNote;

    // Staff constants
    private final int mStaffOffsetX = 10;
    private final int mStaffOffsetY = 60;
    private final int mStaffSpacing = 60;
    private final int mLineThickness = 2;
    private final int mLedgerWidth = 100;

    // Quarter note constants
    private final int mNoteRectWidth = 230;
    private final int mNoteYOffset = 106;
    private final int[] mNotePositions = {200};

    private final Paint mStaffColor = new Paint();

    private int mWidth;
    private int mHeight;
    private int mLedgerLinesDown = 0;
    private int mLedgerLinesUp = 0;

    public StaffLayout(Context context) {
        super(context);
    }

    public StaffLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        mWidth = w;
        mHeight = h;
    }

    // Draws a note at staff location from the bottom line up
    public void drawNote(int location) {
        int x = mNotePositions[0];
        int y = mNoteYOffset - location * mStaffSpacing / 2;

        mNote = getResources().getDrawable(R.drawable.quarter_note);
        mNote.setBounds(x, y, x + mNoteRectWidth, y + mNoteRectWidth);

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

        if (mNote != null)
            mNote.draw(canvas);
    }

    private void drawStaff(Canvas canvas) {
         // Staff lines
        for (int i = 0; i < 5; i++) {
            int vert = i * mStaffSpacing + mStaffOffsetY;
            canvas.drawRect(mStaffOffsetX, vert, mWidth - mStaffOffsetX, vert + mLineThickness, mStaffColor);
        }

        // Right-hand line
        canvas.drawRect(mStaffOffsetX, mStaffOffsetY, 20, mStaffOffsetY + 4 * mStaffSpacing, mStaffColor);

        // Clef
        if (isTreble) {
            Drawable trebleClef = getResources().getDrawable(R.drawable.treble_clef);
            trebleClef.setBounds(0, 0, 250, 400);
            trebleClef.draw(canvas);
        } else {
            // TODO: fix sizing
            Drawable bassClef = getResources().getDrawable(R.drawable.bass_clef);
            bassClef.setBounds(0, 0, 250, 400);
            bassClef.draw(canvas);
        }
    }

    private void addLedgerLines(Canvas canvas) {
        for (int i = 0; i < mLedgerLinesUp; i++) {
            int y = mStaffOffsetY - i * mStaffSpacing;
            int x = mNotePositions[0] + mLedgerWidth / 2;

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }

        for (int i = 0; i < mLedgerLinesDown; i++) {
            int y = mStaffOffsetY + (5 + i) * mStaffSpacing;
            int x = mNotePositions[0] + mLedgerWidth / 2;

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }
    }
}

