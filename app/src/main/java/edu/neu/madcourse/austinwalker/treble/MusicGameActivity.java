package edu.neu.madcourse.austinwalker.treble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameActivity extends AppCompatActivity {

    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";

    private MusicNote mn = new MusicNote();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_game);

        final StaffLayout staff = (StaffLayout) findViewById(R.id.include_staff);

        Button c4Button = findViewById(R.id.piano_c4);
        c4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.C4);
                staff.drawNote(-2);
            }
        });

        Button c4sButton = findViewById(R.id.piano_c4s);
        c4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.C4S);
                staff.drawNote(-2);
            }
        });

        Button d4Button = findViewById(R.id.piano_d4);
        d4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.D4);
                staff.drawNote(-1);
            }
        });


        Button d4sButton = findViewById(R.id.piano_d4s);
        d4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.D4S);
                staff.drawNote(-1);
            }
        });

        Button e4Button = findViewById(R.id.piano_e4);
        e4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.E4);
                staff.drawNote(0);
            }
        });

        Button f4Button = findViewById(R.id.piano_f4);
        f4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.F4);
                staff.drawNote(1);

            }
        });

        Button f4sButton = findViewById(R.id.piano_f4s);
        f4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.F4S);
                staff.drawNote(1);
            }
        });


        Button g4Button = findViewById(R.id.piano_g4);
        g4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.G4);
                staff.drawNote(2);
            }
        });


        Button g4sButton = findViewById(R.id.piano_g4s);
        g4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.G4S);
                staff.drawNote(2);
            }
        });


        Button a4Button = findViewById(R.id.piano_a4);
        a4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.A4);
                staff.drawNote(3);
            }
        });


        Button a4sButton = findViewById(R.id.piano_a4s);
        a4sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.A4S);
                staff.drawNote(3);
            }
        });


        Button b4Button = findViewById(R.id.piano_b4);
        b4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn.playNote(MusicNote.B4);
                staff.drawNote(4);
            }
        });
    }
}
