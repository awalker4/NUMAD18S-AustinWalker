package edu.neu.madcourse.austinwalker.treble;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameLevel {
    public static final String TAG = "MusicGameLevel";

    private final static String[] LEVEL_INTROS = {"You have to help us defeat the aliens!", "Blah", "Blah", "Blah", "Blah", "Blah", "Blah", "Blah", "Blah"};

    private Context mContext;
    private Staff mStaff;
    private PianoView mPianoView;

    private GameTimer mGameTimer;
    private AlertDialog mDialog;

    private int mLevelNumber = 1;

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
    }

    private void startTimer() {
        mGameTimer = new GameTimer();
        mGameTimer.execute();
    }

    public void start() {
        String introText = LEVEL_INTROS[mLevelNumber-1];

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Incoming Transmission...");
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

    public void setupLevel(int levelNum) {
        switch (levelNum) {
            case 1:
                setTreble();
                mStaff.queueAlien(MusicNote.Note.F4, 0, 1);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                setBass();
                mStaff.queueAlien(MusicNote.Note.F4, 0, 1);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    private void setTreble() {
        mStaff.setTreble(true);
        mPianoView.setRange(MusicNote.Note.C4, MusicNote.Note.C5);
    }

    private void setBass() {
        mStaff.setTreble(false);
        mPianoView.setRange(MusicNote.Note.C4, MusicNote.Note.C5); // An octave down is just too low to hear
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
