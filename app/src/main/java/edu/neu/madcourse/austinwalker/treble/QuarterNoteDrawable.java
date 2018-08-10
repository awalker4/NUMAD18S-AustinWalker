package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Canvas;

import edu.neu.madcourse.austinwalker.R;

public class QuarterNoteDrawable extends MusicDrawable {

    public enum NoteState {NAKED, NATURAL, FLAT, SHARP}

    private MusicDrawable mNoteDecorator;
    private NoteState mState;

    // Draw a quarter note centered on x,y
    // https://www.kisspng.com/png-quarter-note-musical-note-eighth-note-rest-music-n-1190492/
    // TODO: flip for high notes
    public QuarterNoteDrawable(StaffView staffView, int staffPosition, int staffRank) {
        super("Note",
                staffView,
                R.drawable.quarter_note,
                230,
                230,
                105,
                197,
                staffPosition,
                staffRank);

        mHitBuffer = 100;
    }

    public void setState(NoteState state) {
        mState = state;
        switch (state) {
            case SHARP:
                mNoteDecorator = new SharpSignDrawable(mStaffView, mStaffPosition, mStaffRank);
                break;
            case FLAT:
                mNoteDecorator = new FlatSignDrawable(mStaffView, mStaffPosition, mStaffRank);
                break;
            default:
                mNoteDecorator = null;
        }
    }

    public NoteState getState() {
        return mState;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mNoteDecorator != null)
            mNoteDecorator.draw(canvas);
    }
}
