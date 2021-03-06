package edu.neu.madcourse.austinwalker.treble;

import edu.neu.madcourse.austinwalker.R;

public class FlatSignDrawable extends MusicDrawable {

    // https://www.emojidex.com/emoji/music_flat_sign
    public FlatSignDrawable(StaffView staffView, int staffPosition, int staffRank) {
        super("Flat Sign",
                staffView,
                R.drawable.flat_sign,
                70,
                90,
                31 + 65, // Offset a little
                56,
                staffPosition,
                staffRank);
    }
}
