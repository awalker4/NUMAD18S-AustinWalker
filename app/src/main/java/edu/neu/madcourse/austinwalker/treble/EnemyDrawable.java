package edu.neu.madcourse.austinwalker.treble;

import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class EnemyDrawable extends MusicDrawable {

    // Draw a UFO centered on x,y
    // https://www.deviantart.com/fireprouf/art/Ufo-Pixel-Art-646305499
    public EnemyDrawable(StaffView staff, int x, int staffPos) {
        super("Alien",
                staff,
                R.drawable.ufo,
                120,
                60,
                60,
                30,
                x,
                staffPos);
    }
}
