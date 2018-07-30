package edu.neu.madcourse.austinwalker.treble;

import android.os.AsyncTask;
import android.util.Log;

public class MusicGameLevel {
    public static final String TAG = "MusicGameLevel";

    private GameTimerTask mGameTimer;

    private Staff mStaff;
    private PianoView mPianoView;

    public MusicGameLevel(StaffView staffView, PianoView pianoView) {
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

        // TODO: set up level state
        mPianoView.setRange(MusicNote.Note.C4, MusicNote.Note.C5);
        mStaff.setTreble(true);

        mStaff.queueAlien(MusicNote.Note.A4, 0);
        mStaff.queueAlien(MusicNote.Note.C5, 6);
    }

    public void start() {
        mGameTimer = new GameTimerTask();
        mGameTimer.execute();
    }

    private class GameTimerTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... time) {
            while (!mStaff.isFinished()) {
                try {
                    Thread.sleep(2000); // TODO: implement BPM
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress();
            }

            return null;
        }

        protected void onProgressUpdate(Void... progress) {
            mStaff.tick();
        }
    }
}
