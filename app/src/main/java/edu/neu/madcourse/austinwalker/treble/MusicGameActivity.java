package edu.neu.madcourse.austinwalker.treble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameActivity extends AppCompatActivity {

    public static final String TAG = "MusicGameActivity";

    private StaffView mStaffView;
    private PianoView mPianoView;

    public static boolean testTreble;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_game);

        mStaffView = findViewById(R.id.game_staff);
        mPianoView = findViewById(R.id.game_piano);
    }

    protected void onResume() {
        super.onResume();

        MusicGameLevel level = new MusicGameLevel(mStaffView, mPianoView);

        if (testTreble)
            level.testTreble();
        else
            level.testBass();
    }
}
