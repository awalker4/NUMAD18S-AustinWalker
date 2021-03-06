package edu.neu.madcourse.austinwalker.treble;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private MusicGameLevel mLevelData;

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

        mLevelData = MusicGameLevelFactory.GetLevel(levelNum);
        setupLevel(levelNum);
        start();
    }

    public void onPause() {
        super.onPause();
        mStaff.setFinished();
    }

    public void start() {
        String introText = mLevelData.getIntroText();

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

    public void showFinishDialog(boolean success) {
        String message;

        if (success)
            message = mLevelData.getSuccessText();
        else
            message = mLevelData.getFailureText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Incoming Transmission..."); // Todo: attribute of level
        builder.setMessage(message);
        builder.setIcon(R.drawable.quarter_note); // TODO: better icon
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.cancel();
                        switchToLevelSelect();
                    }
                });

        mDialog = builder.show();
    }

    public void showStuckDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Incoming Transmission..."); // Todo: attribute of level
        builder.setMessage("Ah, looks like they aren't going anywhere...");
        builder.setIcon(R.drawable.quarter_note); // TODO: better icon
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.cancel();
                        switchToLevelSelect();
                    }
                });

        mDialog = builder.show();
    }

    private void startTimer() {
        // FIXME Get alien position on staff, divide by 4, use that to sleep such that each beat is a second
        mGameTimer = new GameTimer();
        mGameTimer.execute();
    }

    private void switchToLevelSelect() {
        Intent intent = new Intent(this, MusicGameLevelSelectActivity.class);

        // Potentially increment unlocked levels
        if (mStaff.isWin()) {
            MusicGameLevelSelectActivity.setHighestUnlocked(levelNum);
        }

        startActivity(intent);
    }

    public void setupLevel(int levelNum) {
        mStaff.reset();

        switch (levelNum) {
            case 0: // Prelude
                setTreble();
                mStaff.queueAlien(MusicNote.Note.F4, 0, 4, -1);
                break;
            case 1: // Three's a crowd
                setTreble();
                mStaff.queueAlien(MusicNote.Note.C4, 0, 4, -1);
                mStaff.queueAlien(MusicNote.Note.E4, 0, 6, -1);
                mStaff.queueAlien(MusicNote.Note.G4, 0, 8, -1);
                break;
            case 2: // Minor Issue
                setTreble();
                mStaff.queueAlien(MusicNote.Note.D4, 0, 2, -1);
                mStaff.queueAlien(MusicNote.Note.F4, 0, 4, -1);
                mStaff.queueAlien(MusicNote.Note.A4, 0, 8, -1);
                mStaff.queueAlien(MusicNote.Note.B4, 0, 10, -1);
                mStaff.queueAlien(MusicNote.Note.G4, 0, 12, -1);
                mStaff.queueAlien(MusicNote.Note.E4, 0, 14, -1);
                break;
            case 3: // Sharpshooter
                setTreble();
                mStaff.queueAlien(MusicNote.Note.A4, 0, 2, 3);
                mStaff.queueAlien(MusicNote.Note.D5, 0, -1, -1);
                break;
            case 4: // How Flattering
                setTreble();
                mStaff.setUseFlats(true);
                mStaff.queueAlien(MusicNote.Note.A4, 0, 2, 4);
                mStaff.queueAlien(MusicNote.Note.E4, 0, 6, -1);
                mStaff.queueAlien(MusicNote.Note.B3, 0, -1, -1);
                break;
        case 5: // New Tactics
                setBass();
                mStaff.queueAlien(MusicNote.Note.C3, 0, 2, -1);
                mStaff.queueAlien(MusicNote.Note.E3, 0, 4, -1);
                mStaff.queueAlien(MusicNote.Note.G3, 0, 6, -1);
                mStaff.queueAlien(MusicNote.Note.C4, 0, 8, -1);
                break;
            case 6: // Aliens can't eat grass
                setBass();
                mPiano.setRange(MusicNote.Note.A2, MusicNote.Note.B3);
                mStaff.queueAlien(MusicNote.Note.A2, 0, 2, -1);
                mStaff.queueAlien(MusicNote.Note.C3, 0, 4, -1);
                mStaff.queueAlien(MusicNote.Note.E3, 0, 6, -1);
                mStaff.queueAlien(MusicNote.Note.G3, 0, 8, -1);
                break;
            case 7: // Putting it Together
                setBass();
                mStaff.setUseFlats(true);
                mStaff.queueAlien(MusicNote.Note.A2, 0, -1, -1);
                mStaff.queueAlien(MusicNote.Note.B2, 0, -1, -1);
                mStaff.queueAlien(MusicNote.Note.D3, 0, 2, 4);
                mStaff.queueAlien(MusicNote.Note.E3, 0, 4, 4);
                break;
            case 8: // Bach to Basics
                setTreble();
                mStaff.queueAlien(MusicNote.Note.G4, 0, 2, -1);
                mStaff.queueAlien(MusicNote.Note.C4, 0, 4, -1);
                mStaff.queueAlien(MusicNote.Note.D4, 0, 6, -1);
                mStaff.queueAlien(MusicNote.Note.E4, 0, 8, -1);
                mStaff.queueAlien(MusicNote.Note.F4, 0, 10, -1);
                mStaff.queueAlien(MusicNote.Note.G4, 0, 12, -1);
                mStaff.queueAlien(MusicNote.Note.C4, 12, 2, -1);
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
                    Thread.sleep(500); // TODO: implement BPM
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void onProgressUpdate(Void... progress) {
            mStaff.tick();

            if (mStaff.isFinished()) {
                if (mStaff.isStuck())
                    showStuckDialog();
                else
                    showFinishDialog(mStaff.isWin());
            }
        }
    }
}

