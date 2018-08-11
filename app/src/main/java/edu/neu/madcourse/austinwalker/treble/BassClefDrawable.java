package edu.neu.madcourse.austinwalker.treble;

import android.graphics.Rect;

import edu.neu.madcourse.austinwalker.R;

public class BassClefDrawable extends MusicDrawable {

    // Draw a treble clef with x,y in the center of the spiral
    // https://pixabay.com/en/bass-clef-music-clef-symbol-1425777
    public BassClefDrawable(StaffView staffView, int staffPosition, int staffRank) {
        super("Bass Clef",
                staffView,
                R.drawable.bass_clef,
                160,
                185,
                0,
                55,
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
