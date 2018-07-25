package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;
import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class QuarterNoteDrawable extends MusicDrawable {

    public enum NoteState {NAKED, NATURAL, FLAT, SHARP}

    private View mView;
    private MusicDrawable mNoteDecorator;

    // Draw a quarter note centered on x,y
    // https://www.kisspng.com/png-quarter-note-musical-note-eighth-note-rest-music-n-1190492/
    // TODO: flip flag
    public QuarterNoteDrawable(View view, int x, int y) {
        super("Note",
                view.getResources().getDrawable(R.drawable.quarter_note),
                230,
                230,
                x,
                y,
                105,
                197);

        mView = view;
        mHitBuffer = 100;
    }

    public void setState(NoteState state) {
        int x = mXPos - 65; // Off to the left a bit
        int y = mYPos;

        switch (state) {
            case SHARP:
                mNoteDecorator = new SharpSignDrawable(mView, x, y);
                break;
            case FLAT:
                mNoteDecorator = new FlatSignDrawable(mView, x, y);
                break;
            default:
                mNoteDecorator = null;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mNoteDecorator != null)
            mNoteDecorator.draw(canvas);
    }
}
