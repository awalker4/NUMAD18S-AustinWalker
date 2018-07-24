package edu.neu.madcourse.austinwalker.treble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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
    private int mViewWidth;
    private int mViewHeight;
    private int mPianoStartX;
    private int mViewPaddingY = 50;

    private MusicNote.Note mStartNote;
    private MusicNote.Note mEndNote;

    private ArrayList<MusicNote.Note> keys;
    private ArrayList<Rect> keyRects;

    private int mLineThickness = 3;
    private int mKeyWidth = 80;
    private int mWhiteKeyHeight = 400;
    private int mBlackHeyHeight = 250;
    private int mKeyMargin = 3;

    private Paint mKeyColor = new Paint();

    public PianoView(Context context) {
        super(context);
    }

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRange(MusicNote.Note start, MusicNote.Note end) {
        mStartNote = start;
        mEndNote = end;
    }

    private void setupKeys() {
        mKeyColor.setColor(Color.BLACK); // MAYBE SOMEWHERE ELSE

        keys = new ArrayList<>();
        keyRects = new ArrayList<>();

        int startNotes = mStartNote.ordinal();
        int endNotes = mEndNote.ordinal();
        MusicNote.Note[] allNotes = MusicNote.Note.values();

        int keyNum = 0;
        for (int i = startNotes; i <= endNotes; i++) {
            MusicNote.Note current = allNotes[i];

            keys.add(current);

            // Get the rect
            int x = mPianoStartX + mKeyWidth * keyNum + 2 * mKeyMargin;
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

    private int getPianoWidth() {
        if (mStartNote == null || mEndNote == null)
            return 0;

        return (mEndNote.getKeyNumber() - mStartNote.getKeyNumber() + 1) * mKeyWidth;
    }

    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        mViewWidth = w;
        mViewHeight = h;

        // Center the keys horizontally
        // get center of view, then go back by half of piano width
        mPianoStartX = (mViewWidth / 2) - (getPianoWidth() / 2);

        setupKeys();

        Log.d(TAG, "onSizeChanged: " + mViewWidth + "X" + mViewHeight);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).isWhite()) {
                drawWhiteKey(canvas, keyRects.get(i));
            }
        }

        // Go back and draw black
        for (int i = 0; i < keys.size(); i++) {
            if (!keys.get(i).isWhite()) {
                canvas.drawRect(keyRects.get(i), mKeyColor);
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
        int whiteKey = -1;

        for (int i = 0; i < keys.size(); i++) {
            if (keyRects.get(i).contains((int) x, (int) y)) {

                // Make sure there isn't also a black key here
                if (keys.get(i).isWhite())
                    whiteKey = i;
                else
                    return i;
            }
        }

        return whiteKey;
    }

    private Rect getWhiteKeyRect(int x, int y) {
        int left = x + mKeyMargin;
        int right = x + mKeyWidth - mKeyMargin;
        int top = y;
        int bottom = y + mWhiteKeyHeight;

        return new Rect(left, top, right, bottom);
    }

    // Draw black key with top left corner at x,y
    private Rect getBlackKeyRect(int x, int y) {
        return new Rect(x + mKeyMargin, y, x + mKeyWidth - mKeyMargin, y + mBlackHeyHeight);
    }

    // Draw white key outlined on Rect r
    private void drawWhiteKey(Canvas canvas, Rect r) {
        // Left
        canvas.drawRect(r.left, r.top, r.left + mLineThickness, r.bottom, mKeyColor);

        // Bottom
        canvas.drawRect(r.left, r.bottom, r.right, r.bottom + mLineThickness, mKeyColor);

        // Right
        canvas.drawRect(r.right, r.top, r.right + mLineThickness, r.bottom, mKeyColor);

        // Top
        canvas.drawRect(r.right, r.top, r.left, r.top + mLineThickness, mKeyColor);
    }
}
