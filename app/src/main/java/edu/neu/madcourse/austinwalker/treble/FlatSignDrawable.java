package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class FlatSignDrawable extends MusicDrawable {

    // https://www.emojidex.com/emoji/music_flat_sign
    public FlatSignDrawable(StaffView staffView, int x, int staffPos) {
        super("Flat Sign",
                staffView,
                R.drawable.flat_sign,
                70,
                90,
                31,
                56,
                x,
                staffPos);
    }
}
