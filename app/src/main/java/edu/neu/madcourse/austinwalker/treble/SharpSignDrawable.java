package edu.neu.madcourse.austinwalker.treble;

import android.view.View;
import edu.neu.madcourse.austinwalker.R;

public class SharpSignDrawable extends MusicDrawable {

    // https://pixabay.com/en/sharp-note-music-note-action-27902/
    public SharpSignDrawable(View view, int x, int y) {
        super("Sharp Sign",
                view.getResources().getDrawable(R.drawable.sharp_sign),
                50,
                90,
                x,
                y,
                23,
                47);
    }
}
