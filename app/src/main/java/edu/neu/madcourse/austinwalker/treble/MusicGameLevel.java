package edu.neu.madcourse.austinwalker.treble;

import android.util.Log;

public class MusicGameLevel implements Runnable {
    public static final String TAG = "MusicGameLevel";

    private Staff mStaff;
    private PianoView mPianoView;

    public MusicGameLevel(StaffView staffView, PianoView pianoView) {
        mStaff = new Staff(staffView);
        mPianoView = pianoView;

        mPianoView.setRange(MusicNote.Note.C4, MusicNote.Note.C5);
        mStaff.setTreble(true);

        final MusicNote mn = new MusicNote();
        mPianoView.setKeyPressedListener(new PianoView.KeyPressedListener() {
            @Override
            public void onKeyDown(MusicNote.Note notePressed) {
                Log.d(TAG, "onKeyDown: " + notePressed.name());
                mn.playNote(notePressed.getFrequency());
                mStaff.placeNote(notePressed);
            }
        });

        mStaff.addAlien(MusicNote.Note.A4);
        mStaff.addAlien(MusicNote.Note.C5);
    }

    // Can't touch the UI with this
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(1000);
                mStaff.tick();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
