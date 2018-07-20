package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class QuarterNote extends MusicDrawable {

    // Draw a quarter note centered on x,y
    // https://www.kisspng.com/png-quarter-note-musical-note-eighth-note-rest-music-n-1190492/
    // TODO: flip flag
    public QuarterNote(View view, int x, int y) {
        super("Note",
                view.getResources().getDrawable(R.drawable.quarter_note),
                230,
                230,
                x,
                y,
                105,
                197);

        mHitBuffer = 100;
    }
}
