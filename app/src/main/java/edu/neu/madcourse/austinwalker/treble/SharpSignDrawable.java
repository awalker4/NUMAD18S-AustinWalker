package edu.neu.madcourse.austinwalker.treble;

import edu.neu.madcourse.austinwalker.R;

public class SharpSignDrawable extends MusicDrawable {

    // https://pixabay.com/en/sharp-note-music-note-action-27902/
    public SharpSignDrawable(StaffView staffView, int staffPosition, int staffRank) {
        super("Sharp Sign",
                staffView,
                R.drawable.sharp_sign,
                50,
                90,
                23 + 65, // Offset a little
                47,
                staffPosition,
                staffRank);
    }
}
