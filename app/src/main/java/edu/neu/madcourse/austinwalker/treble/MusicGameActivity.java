package edu.neu.madcourse.austinwalker.treble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameActivity extends AppCompatActivity {

    public static final String TAG = "MusicGameActivity";


    private MusicNote mn = new MusicNote();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_game);

        final StaffView staff = (StaffView) findViewById(R.id.game_staff);
        PianoView piano = (PianoView) findViewById(R.id.game_piano);

        piano.setKeyPressedListener(new PianoView.KeyPressedListener() {
            @Override
            public void onKeyDown(MusicNote.Note notePressed) {
                Log.d(TAG, "onKeyDown: " + notePressed.name());
                mn.playNote(notePressed.getFrequency());
            }
        });
    }
}
