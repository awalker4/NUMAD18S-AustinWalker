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

    public final static int[] LEVEL_INTRO_IDS = {
            R.string.treble_level_intro_1,
            R.string.treble_level_intro_2,
            R.string.treble_level_intro_3,
            R.string.treble_level_intro_4,
            R.string.treble_level_intro_5,
            R.string.treble_level_intro_6,
            R.string.treble_level_intro_7,
            R.string.treble_level_intro_8,
            R.string.treble_level_intro_9};

    public final static int[] LEVEL_SUCCESS_IDS = {
            R.string.treble_level_success_1,
            R.string.treble_level_success_2,
            R.string.treble_level_success_3,
            R.string.treble_level_success_4,
            R.string.treble_level_success_5,
            R.string.treble_level_success_6,
            R.string.treble_level_success_7,
            R.string.treble_level_success_8,
            R.string.treble_level_success_9};

    public final static int[] LEVEL_FAIL_IDS = {
            R.string.treble_level_fail_1,
            R.string.treble_level_fail_2,
            R.string.treble_level_fail_3,
            R.string.treble_level_fail_4,
            R.string.treble_level_fail_5,
            R.string.treble_level_fail_6,
            R.string.treble_level_fail_7,
            R.string.treble_level_fail_8,
            R.string.treble_level_fail_9};

    // TODO success, fail text

    public static MusicGameLevel GetLevel(int levelNum) {
        String name = mContext.getResources().getString(LEVEL_NAME_IDS[levelNum]);
        String desc = mContext.getResources().getString(LEVEL_DESCRIPTION_IDS[levelNum]);
        String intro = mContext.getResources().getString(LEVEL_INTRO_IDS[levelNum]);
        String success = mContext.getResources().getString(LEVEL_SUCCESS_IDS[levelNum]);
        String fail = mContext.getResources().getString(LEVEL_FAIL_IDS[levelNum]);

        return new MusicGameLevel(name, desc, intro, success, fail);
    }
}
