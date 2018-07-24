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

        final Staff staff = new Staff((StaffView) findViewById(R.id.game_staff));
        PianoView piano = findViewById(R.id.game_piano);

        piano.setRange(MusicNote.Note.C4, MusicNote.Note.C5);
        staff.setTreble(true);

        piano.setKeyPressedListener(new PianoView.KeyPressedListener() {
            @Override
            public void onKeyDown(MusicNote.Note notePressed) {
                Log.d(TAG, "onKeyDown: " + notePressed.name());
                mn.playNote(notePressed.getFrequency());
                staff.placeNote(notePressed);
            }
        });
    }
}
