package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class EnemyDrawable extends MusicDrawable {

    // Draw a UFO centered on x,y
    // https://www.deviantart.com/fireprouf/art/Ufo-Pixel-Art-646305499
    public EnemyDrawable(View view, int x, int y) {
        super("Alien",
                view.getResources().getDrawable(R.drawable.ufo),
                120,
                60,
                x,
                y,
                60,
                30);
    }
}
