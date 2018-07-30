package edu.neu.madcourse.austinwalker.treble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Vector;

public class StaffView extends View {
    private static final String TAG = "StaffView";

    // View constants
    private static final int mViewPaddingX = 20;

    // Staff constants
    private static final int mStaffSpacing = 60;
    private static final int mLineThickness = 2;
    private static final int mLedgerWidth = 100;
    private static final int mClefOffset = 50;
    private static final int mNoteOffset = 300;

    private final Paint mStaffColor = new Paint();

    private int mViewWidth;
    private int mViewHeight;
    private int mStaffStartY;
    private int mStaffEndY;
    private int mNotePosition = 0;
    private int mAlienPosition = 3;

    private int mLedgerLinesDown = 0;
    private int mLedgerLinesUp = 0;

    private boolean isTreble = true;
    private boolean isClosed = false;

    private MusicDrawable mClef;
    private QuarterNoteDrawable mNote;
    private ArrayList<EnemyDrawable> mAliens = new ArrayList<>();
    private ArrayList<BulletDrawable> mBullets = new ArrayList<>();

    public StaffView(Context context) {
        super(context);
    }

    public StaffView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mStaffColor.setColor(Color.BLACK); // Could be somewhere else

        mViewWidth = w;
        mViewHeight = h;

        // Center the staff vertically
        // (get the center of the view, then go up by half the staff height)
        mStaffStartY = (mViewHeight / 2) - (4 * mStaffSpacing / 2);
        mStaffEndY = mStaffStartY + (4 * mStaffSpacing);

        setupClef();

        Log.d(TAG, "onSizeChanged: " + mViewWidth + "X" + mViewHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawStaff(canvas);
        addLedgerLines(canvas);

        if (mNote != null) {
            mNote.draw(canvas);
        }

        for (MusicDrawable alien : mAliens) {
            alien.draw(canvas);
        }

        for (MusicDrawable bullet : mBullets) {
            bullet.draw(canvas);
        }
    }

    public void drawNote(int location) {
        drawNote(location, QuarterNoteDrawable.NoteState.NAKED);
    }

    public void drawFlatNote(int location) {
        drawNote(location, QuarterNoteDrawable.NoteState.FLAT);
    }

    public void drawSharpNote(int location) {
        drawNote(location, QuarterNoteDrawable.NoteState.SHARP);
    }

    public void drawNaturalNote(int location) {
        drawNote(location, QuarterNoteDrawable.NoteState.NATURAL);
    }

    public void addAlien(int rank) {
        // TODO: add a potential shooting delay
        mAliens.add(new EnemyDrawable(this, mAlienPosition, rank));
        mBullets.add(new BulletDrawable(this, mAlienPosition, rank));

        invalidate();
    }

    public int numAliens() {
        return mAliens.size();
    }

    // FIXME: better list processing
    public void tick() {
        Vector<BulletDrawable> toRemoveBullets = new Vector<>();

        for (BulletDrawable bullet : mBullets) {
            bullet.tick();

            if (mNote != null && !bullet.isReverse() && mNote.collidesWith(bullet)) {
                bullet.reverse();
            }

            Vector<MusicDrawable> toRemove = new Vector<>();

            for (MusicDrawable alien : mAliens) {
                if (bullet.isReverse() && alien.collidesWith(bullet)) {
                    toRemove.add(alien);
                    toRemoveBullets.add(bullet);
                }
            }

            for (MusicDrawable alien : toRemove) {
                mAliens.remove(alien);
            }
        }

        for (BulletDrawable bulletRemove : toRemoveBullets) {
            mBullets.remove(bulletRemove);
        }

        invalidate();
    }

    public int getXForStaffLocation(int position) {
        // HACK: position x is the clef, draw at hardcoded offset
        if (position == -1)
            return mViewPaddingX + mClefOffset;

        // Give us enough room to draw 4 notes across the staff (minus some space for the clef)
        int staffWidth = mViewWidth - (2 * mViewPaddingX) - mNoteOffset;
        int noteSpacing = staffWidth / 4;

        return mNoteOffset + position * noteSpacing;
    }

    // Return a y value for all possible staff positions
    // starting from the bottom line
    public int getYForRank(int position) {
        return mStaffEndY - (position * mStaffSpacing / 2);
    }

    public boolean isTreble() {
        return isTreble;
    }

    public void setTreble(boolean treble) {
        isTreble = treble;
        setupClef();
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    private void setupClef() {
        if (isTreble) {
            mClef = new TrebleClefDrawable(this, -1, 2); // Center on G
        } else {
            mClef = new BassClefDrawable(this, -1, 6); // Center on F
        }
    }

    // Draws a note at staff rank from the bottom line up
    private void drawNote(int rank, QuarterNoteDrawable.NoteState state) {
        mNote = new QuarterNoteDrawable(this, mNotePosition, rank);
        mNote.setState(state);

        mLedgerLinesDown = mLedgerLinesUp = 0;
        if (rank < 0) {
            mLedgerLinesDown = (0 - rank) / 2;
        } else if (rank > 10) {
            mLedgerLinesUp = (rank - 10) / 2;
        }

        invalidate();
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

        // TEST LINES
        for (int i = 0; i < 4; i++) {
            drawVertical(canvas, getXForStaffLocation(i), 3);
        }
    }

    // Add lines above or below staff if called for
    private void addLedgerLines(Canvas canvas) {
        int x = getXForStaffLocation(mNotePosition) - mLedgerWidth /2;

        for (int i = 1; i <= mLedgerLinesUp; i++) {
            int y = getYForRank(8 + 2 * i);

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }

        for (int i = 1; i <= mLedgerLinesDown; i++) {
            int y = getYForRank(0 - 2 * i);

            canvas.drawRect(x, y, x + mLedgerWidth, y + mLineThickness, mStaffColor);
        }
    }

    // Draw a line across the staff at x
    private void drawVertical(Canvas canvas, int x, int width) {
        int topLine = getYForRank(8);
        int bottomLine = getYForRank(0);

        canvas.drawRect(x, topLine, x + width, bottomLine, mStaffColor);
    }
}

