package edu.neu.madcourse.austinwalker.treble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameActivity extends AppCompatActivity {

    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
//    private WordGameFragment mGameFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Piano piano = new Piano(this);
        setContentView(R.layout.piano);

        Button c4Button = findViewById(R.id.piano_c4);
        c4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.C4);
            }
        });

        Button c4sButton = findViewById(R.id.piano_c4s);
        c4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.C4S);
            }
        });

        Button d4Button = findViewById(R.id.piano_d4);
        d4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.D4);
            }
        });


        Button d4sButton = findViewById(R.id.piano_d4s);
        d4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.D4S);
            }
        });

        Button e4Button = findViewById(R.id.piano_e4);
        e4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.E4);
            }
        });

        Button f4Button = findViewById(R.id.piano_f4);
        f4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.F4);
            }
        });

        Button f4sButton = findViewById(R.id.piano_f4s);
        f4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.F4S);
            }
        });


        Button g4Button = findViewById(R.id.piano_g4);
        g4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.G4);
            }
        });


        Button g4sButton = findViewById(R.id.piano_g4s);
        g4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.G4S);
            }
        });


        Button a4Button = findViewById(R.id.piano_a4);
        a4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.A4);
            }
        });


        Button a4sButton = findViewById(R.id.piano_a4s);
        a4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.A4S);
            }
        });


        Button b4Button = findViewById(R.id.piano_b4);
        b4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicNote.playTone(MusicNote.B4);
            }
        });


//
//        mGameFragment = (WordGameFragment) getFragmentManager().findFragmentById(R.id.fragment_word_game);
//        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
//        if (restore) {
//            String gameData = getPreferences(MODE_PRIVATE).getString(PREF_RESTORE, null);
//            if (gameData != null) {
//                mGameFragment.resumeGame(gameData);
//            }
//        }
    }
}
