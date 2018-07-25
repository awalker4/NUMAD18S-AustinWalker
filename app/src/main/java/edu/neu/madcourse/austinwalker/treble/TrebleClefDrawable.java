package edu.neu.madcourse.austinwalker.treble;

import edu.neu.madcourse.austinwalker.R;

public class TrebleClefDrawable extends MusicDrawable {

    // Draw a treble clef with x,y in the center of the spiral
    // https://pixabay.com/en/treble-clef-png-key-music-clef-1279909/
    public TrebleClefDrawable(StaffView staffView, int x, int staffPos) {
        super("Treble Clef",
                staffView,
                R.drawable.treble_clef,
                250,
                400,
                50,
                247,
                x,
                staffPos);
    }

}
