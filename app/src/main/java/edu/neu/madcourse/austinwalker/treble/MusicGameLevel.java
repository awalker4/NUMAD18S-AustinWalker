package edu.neu.madcourse.austinwalker.treble;

import android.util.Log;

public class MusicGameLevel {
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

        mStaff.start();
    }


}
