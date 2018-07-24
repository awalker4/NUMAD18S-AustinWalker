package edu.neu.madcourse.austinwalker.treble;

public class Staff {

    private StaffView mStaffView;

    private boolean isTreble = true;

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

        mStaffView.drawNote(notePosition);
    }
}
