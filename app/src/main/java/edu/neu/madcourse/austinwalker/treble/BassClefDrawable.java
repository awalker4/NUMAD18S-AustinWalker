package edu.neu.madcourse.austinwalker.treble;

import edu.neu.madcourse.austinwalker.R;

public class BassClefDrawable extends MusicDrawable {

    // Draw a treble clef with x,y in the center of the spiral
    // https://pixabay.com/en/bass-clef-music-clef-symbol-1425777
    public BassClefDrawable(StaffView staffView, int staffPosition, int staffRank) {
        super("Bass Clef",
                staffView,
                R.drawable.bass_clef,
                160,
                185,
                0,
                55,
                staffPosition,
                staffRank);
    }

}
