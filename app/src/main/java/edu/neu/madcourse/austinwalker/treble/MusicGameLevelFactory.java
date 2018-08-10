package edu.neu.madcourse.austinwalker.treble;

import android.content.Context;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameLevelFactory {

    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    public final static int[] LEVEL_NAME_IDS = {
            R.string.treble_level_name_1,
            R.string.treble_level_name_2,
            R.string.treble_level_name_3,
            R.string.treble_level_name_4,
            R.string.treble_level_name_5,
            R.string.treble_level_name_6,
            R.string.treble_level_name_7,
            R.string.treble_level_name_8,
            R.string.treble_level_name_9};

    public final static int[] LEVEL_DESCRIPTION_IDS = {
            R.string.treble_level_description_1,
            R.string.treble_level_description_2,
            R.string.treble_level_description_3,
            R.string.treble_level_description_4,
            R.string.treble_level_description_5,
            R.string.treble_level_description_6,
            R.string.treble_level_description_7,
            R.string.treble_level_description_8,
            R.string.treble_level_description_9};

    // TODO success, fail text

    public static MusicGameLevel GetLevel(int levelNum) {
        String name = mContext.getResources().getString(LEVEL_NAME_IDS[levelNum]);
        String desc = mContext.getResources().getString(LEVEL_DESCRIPTION_IDS[levelNum]);

        return new MusicGameLevel(name, desc, "","","");
    }

    public static LevelTile GetLevelTile(int levelNum) {
        String name = mContext.getResources().getString(LEVEL_NAME_IDS[levelNum]);
        String desc = mContext.getResources().getString(LEVEL_DESCRIPTION_IDS[levelNum]);

        return new LevelTile(name, desc);
    }

}
