package edu.neu.madcourse.austinwalker.treble;

import edu.neu.madcourse.austinwalker.R;

public class EnemyDrawable extends MusicDrawable {

    // Draw a UFO centered on the staff location
    // https://www.deviantart.com/fireprouf/art/Ufo-Pixel-Art-646305499
    public EnemyDrawable(StaffView staff, int staffPosition, int staffRank) {
        super("Alien",
                staff,
                R.drawable.ufo,
                120,
                60,
                60,
                30,
                staffPosition,
                staffRank);
    }
}
