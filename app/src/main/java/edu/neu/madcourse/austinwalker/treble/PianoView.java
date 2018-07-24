package edu.neu.madcourse.austinwalker.treble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PianoView extends View {

    public interface KeyPressedListener {
        void onKeyDown(MusicNote.Note notePressed);
    }

    public static final String TAG = "PianoView";
    private KeyPressedListener mListener;

    // View dimensions
    private int mViewPaddingX = 50;
    private int mViewPaddingY = 50;

    // TODO: make properties of the view
    private MusicNote.Note mStartNote = MusicNote.Note.C4;
    private MusicNote.Note endNote = MusicNote.Note.C5;

    private ArrayList<MusicNote.Note> keys;
    private ArrayList<Rect> keyRects;

    private int mLineThickness = 3;
    private int mKeyWidth = 80;
    private int mWhiteKeyHeight = 400;
    private int mBlackHeyHeight = 250;
    private int mKeyMargin = 3;

    private Paint mStaffColor = new Paint();

    public PianoView(Context context) {
        super(context);
    }

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void setupKeys() {
        keys = new ArrayList<>();
        keyRects = new ArrayList<>();

        int startNotes = mStartNote.ordinal();
        int endNotes = endNote.ordinal();
        MusicNote.Note[] allNotes = MusicNote.Note.values();

        int keyNum = 0;
        for (int i = startNotes; i <= endNotes; i++) {
            MusicNote.Note current = allNotes[i];

            keys.add(current);

            // Get the rect
            int x = mViewPaddingX + mKeyWidth * keyNum + 2 * mKeyMargin;
            int y = mViewPaddingY;
            if (current.isWhite()) {
                keyRects.add(getWhiteKeyRect(x, y));
                keyNum++;
            } else {
                x -= mKeyWidth / 2;
                keyRects.add(getBlackKeyRect(x, y));
            }
        }
    }

    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        // TODO recalculate view size
        setupKeys();

//        Log.d(TAG, "onSizeChanged: " + mViewWidth + "X" + mViewHeight);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mStaffColor.setColor(Color.BLACK);

        Paint keyColor = new Paint();

        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).isWhite()) {
                drawWhiteKey(canvas, keyRects.get(i));
            }
        }

        // Go back and draw black
        for (int i = 0; i < keys.size(); i++) {
            if (!keys.get(i).isWhite()) {
                keyColor.setColor(Color.BLACK);
                canvas.drawRect(keyRects.get(i), keyColor);
            }
        }
    }

    public void setKeyPressedListener(KeyPressedListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            int keyNumber = getKeyNumber(ev.getX(), ev.getY());

            if (mListener != null && keyNumber > -1) {
                MusicNote.Note pressedNote = keys.get(keyNumber);
                mListener.onKeyDown(pressedNote);
            }
        }

        return true;
    }

    // Return the key residing at x,y
    // or -1 if no key
    private int getKeyNumber(float x, float y) {

        // Make sure we check black keys first
        // SUPER HACK
        for (int i = 0; i < keys.size(); i++) {
            if (keyRects.get(i).contains((int) x, (int) y) && !keys.get(i).isWhite()) {
                return i;
            }
        }

        for (int i = 0; i < keys.size(); i++) {
            if (keyRects.get(i).contains((int) x, (int) y)) {
                return i;
            }
        }

        return -1;
    }

    private Rect getWhiteKeyRect(int x, int y) {
        int left = x + mKeyMargin;
        int right = x + mKeyWidth - mKeyMargin;
        int top = y;
        int bottom = y + mWhiteKeyHeight;

        return new Rect(left, top, right, bottom);
    }

    // Draw white key with top left corner at x,y
    private void drawWhiteKey(Canvas canvas, Rect r) {
        // Left
        canvas.drawRect(r.left, r.top, r.left + mLineThickness, r.bottom, mStaffColor);

        // Bottom
        canvas.drawRect(r.left, r.bottom, r.right, r.bottom + mLineThickness, mStaffColor);

        // Right
        canvas.drawRect(r.right, r.top, r.right + mLineThickness, r.bottom, mStaffColor);

        // Top
        canvas.drawRect(r.right, r.top, r.left, r.top + mLineThickness, mStaffColor);
    }

    // Draw black key with top left corner at x,y
    private void drawBlackKey(Canvas canvas, int x, int y) {
        canvas.drawRect(x + mKeyMargin, y, x + mKeyWidth - mKeyMargin, y + mBlackHeyHeight, mStaffColor);
    }

    // Draw black key with top left corner at x,y
    private Rect getBlackKeyRect(int x, int y) {
        return new Rect(x + mKeyMargin, y, x + mKeyWidth - mKeyMargin, y + mBlackHeyHeight);
    }


}
