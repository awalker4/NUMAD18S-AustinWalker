package edu.neu.madcourse.austinwalker.treble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameActivity extends AppCompatActivity {

    public static final String TAG = "MusicGameActivity";

    // FIXME: Shouldn't hardcode these
    public final static String[] LEVEL_NAMES = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};
    public final static String[] LEVEL_DESCRIPTIONS = {"Intro", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};

    public static int levelNum = 1;
    public MusicGameLevel mCurrentLevel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_game);

        StaffView staff = findViewById(R.id.game_staff);
        PianoView piano = findViewById(R.id.game_piano);

        mCurrentLevel = new MusicGameLevel(this, staff, piano);
        mCurrentLevel.setupLevel(levelNum);
    }

    protected void onResume() {
        super.onResume();
        mCurrentLevel.start();
    }
}
