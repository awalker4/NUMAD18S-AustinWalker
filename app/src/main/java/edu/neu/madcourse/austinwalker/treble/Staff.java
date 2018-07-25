package edu.neu.madcourse.austinwalker.treble;

public class Staff {

    private StaffView mStaffView;

    private boolean useFlats = false;

    public Staff(StaffView view) {
        mStaffView = view;
    }

    public void setTreble(boolean treble) {
        mStaffView.setTreble(treble);
    }

    public void placeNote(MusicNote.Note note) {
        int notePosition = note.getKeyNumber();

        if (mStaffView.isTreble())
            notePosition -= MusicNote.Note.E4.getKeyNumber();
        else
            notePosition -= MusicNote.Note.G2.getKeyNumber();

        if (note.isWhite()) {
            mStaffView.drawNote(notePosition);
        } else if (useFlats) {
            // Black keys are positioned as sharps, need to go up a spot
            mStaffView.drawFlatNote(notePosition + 1);
        } else {
            mStaffView.drawSharpNote(notePosition);
        }
    }
}
