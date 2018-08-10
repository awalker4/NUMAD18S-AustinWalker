package edu.neu.madcourse.austinwalker.treble;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameActivity extends AppCompatActivity {

    public static final String TAG = "MusicGameActivity";

    private Staff mStaff;
    private PianoView mPiano;

    private GameTimer mGameTimer;
    private MusicGameLevel levelData;

    public static int levelNum;

    private AlertDialog mDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_music_game);

        // Setup views
        StaffView staffView = findViewById(R.id.game_staff);
        mStaff = new Staff(staffView);
        mPiano = findViewById(R.id.game_piano);

        final MusicNote mn = new MusicNote();
        mPiano.setKeyPressedListener(new PianoView.KeyPressedListener() {
            @Override
            public void onKeyDown(MusicNote.Note notePressed) {
                Log.d(TAG, "onKeyDown: " + notePressed.name());
                mn.playNote(notePressed.getFrequency());
                mStaff.placeNote(notePressed);
            }
        });
    }

    protected void onResume() {
        super.onResume();

        levelData = MusicGameLevelFactory.GetLevel(levelNum);
        setupLevel(levelNum);
        start();
    }

    public void onPause() {
        super.onPause();
        mStaff.setFinished();
    }

    public void start() {
        String introText = levelData.getIntroText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Incoming Transmission..."); // Todo: attribute of level
        builder.setMessage(introText);
        builder.setIcon(R.drawable.quarter_note); // TODO: better icon
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.cancel();
                        startTimer();
                    }
                });

        mDialog = builder.show();
    }


    private void startTimer() {
        mGameTimer = new GameTimer();
        mGameTimer.execute();
    }

    public void setupLevel(int levelNum) {
        mStaff.reset();

        switch (levelNum) {
            case 0:
                setTreble();
                mStaff.queueAlien(MusicNote.Note.F4, 0, 2);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                setBass();
                mStaff.queueAlien(MusicNote.Note.F4, 0, 1);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    private void setTreble() {
        mStaff.setTreble(true);
        mPiano.setRange(MusicNote.Note.C4, MusicNote.Note.C5);
    }

    private void setBass() {
        mStaff.setTreble(false);
        mPiano.setRange(MusicNote.Note.C3, MusicNote.Note.C4); // FIXME: An octave down is just too low to hear
    }

    private class GameTimer extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... time) {

            // TODO: account for backing out midgame
            while (!mStaff.isFinished()) {
                publishProgress();

                try {
                    Thread.sleep(1000); // TODO: implement BPM
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void onProgressUpdate(Void... progress) {
            mStaff.tick();
        }
    }
}
