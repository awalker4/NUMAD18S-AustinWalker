package edu.neu.madcourse.austinwalker.treble;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameLevel {
    public static final String TAG = "MusicGameLevel";

    private Context mContext;
    private Staff mStaff;
    private PianoView mPianoView;

    private GameTimer mGameTimer;
    private AlertDialog mDialog;

    private String levelName;
    private String levelText;

    public MusicGameLevel(Context context, StaffView staffView, PianoView pianoView) {
        mContext = context;
        mStaff = new Staff(staffView);
        mPianoView = pianoView;

        final MusicNote mn = new MusicNote();
        mPianoView.setKeyPressedListener(new PianoView.KeyPressedListener() {
            @Override
            public void onKeyDown(MusicNote.Note notePressed) {
                Log.d(TAG, "onKeyDown: " + notePressed.name());
                mn.playNote(notePressed.getFrequency());
                mStaff.placeNote(notePressed);
            }
        });

        // Get level state
        levelName = "Level 1";
        levelText = "You have to help us defeat the aliens!";
    }

    private void startTimer() {
        mGameTimer = new GameTimer();
        mGameTimer.execute();
    }

    public void start() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(levelName);
        builder.setMessage(levelText);
        builder.setIcon(R.drawable.sharp_sign); // TODO: better icon
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

    public void testTreble() {
        mPianoView.setRange(MusicNote.Note.C4, MusicNote.Note.C5);
        mStaff.setTreble(true);

        mStaff.queueAlien(MusicNote.Note.C4, 0, 1);
        mStaff.queueAlien(MusicNote.Note.E4, 0, 3);
        mStaff.queueAlien(MusicNote.Note.G4, 0, 5);

        start();
    }

    public void testBass() {
        mPianoView.setRange(MusicNote.Note.C3, MusicNote.Note.C4);
        mStaff.setTreble(false);

        mStaff.queueAlien(MusicNote.Note.C3, 0, 1);
        mStaff.queueAlien(MusicNote.Note.E3, 0, 3);
        mStaff.queueAlien(MusicNote.Note.G3, 0, 5);

        start();
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
