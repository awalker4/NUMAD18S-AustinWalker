package edu.neu.madcourse.austinwalker.treble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameActivity extends AppCompatActivity {

    public static final String TAG = "MusicGameActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_game);

        StaffView staff = findViewById(R.id.game_staff);
        PianoView piano = findViewById(R.id.game_piano);
        MusicGameLevel firstLevel = new MusicGameLevel(staff, piano);

        new Thread(firstLevel).start();

    }
}
