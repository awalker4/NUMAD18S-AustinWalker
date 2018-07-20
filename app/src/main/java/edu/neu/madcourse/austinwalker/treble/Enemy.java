package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class Enemy extends MusicDrawable {

    // Draw a UFO centered on x,y
    // https://www.deviantart.com/fireprouf/art/Ufo-Pixel-Art-646305499
    public Enemy(View view, int x, int y) {
        super(view.getResources().getDrawable(R.drawable.ufo),
                120,
                60,
                x-60,
                y-30);
    }
}
