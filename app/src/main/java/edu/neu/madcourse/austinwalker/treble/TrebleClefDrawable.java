package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Rect;

import edu.neu.madcourse.austinwalker.R;

public class TrebleClefDrawable extends MusicDrawable {

    // Draw a treble clef with x,y in the center of the spiral
    // https://pixabay.com/en/treble-clef-png-key-music-clef-1279909/
    public TrebleClefDrawable(StaffView staffView, int staffPosition, int staffRank) {
        super("Treble Clef",
                staffView,
                R.drawable.treble_clef,
                250,
                400,
                50,
                247,
                staffPosition,
                staffRank);
    }

    @Override
    public Rect getHitBox() {
        int x = mStaffView.getXForStaffLocation(mStaffPosition);
        int topY = mStaffView.getYForRank(10);
        int bottomY = mStaffView.getYForRank(-2);

        return new Rect(x - mHitBuffer, topY, x + mHitBuffer, bottomY);
    }
}
