package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class TrebleClef extends MusicDrawable {

    // Draw a treble clef with x,y in the center of the spiral
    // https://pixabay.com/en/treble-clef-png-key-music-clef-1279909/
    public TrebleClef(View view, int x, int y) {
        super(view.getResources().getDrawable(R.drawable.treble_clef),
                250,
                400,
                x,
                y,
                145,
                247);
    }

}
