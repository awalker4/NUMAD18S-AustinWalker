package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class BassClefDrawable extends MusicDrawable {

    // Draw a treble clef with x,y in the center of the spiral
    // https://pixabay.com/en/bass-clef-music-clef-symbol-1425777
    public BassClefDrawable(View view, int x, int y) {
        super("Bass Clef",
                view.getResources().getDrawable(R.drawable.bass_clef),
                160,
                185,
                x,
                y,
                0,
                55);
    }

}
