package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class FlatSignDrawable extends MusicDrawable {

    // https://www.emojidex.com/emoji/music_flat_sign
    public FlatSignDrawable(View view, int x, int y) {
        super("Flat Sign",
                view.getResources().getDrawable(R.drawable.flat_sign),
                70,
                90,
                x,
                y,
                31,
                56);
    }
}
