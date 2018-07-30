package edu.neu.madcourse.austinwalker.treble;


// Add some abstraction on top of StaffView
// At this level, we deal with note names rather than staff ranking
public class Staff {

    private StaffView mStaffView;
    private boolean useFlats = false;
    private boolean mFinished;

    public Staff(StaffView view) {
        mStaffView = view;
        mFinished = false;
    }

    public void setTreble(boolean treble) {
        mStaffView.setTreble(treble);
    }

    public boolean isFinished() {
        return mFinished;
    }

    public void addAlien(MusicNote.Note alienNote) {
        int alienPosition = getNotePosition(alienNote);

        mStaffView.addAlien(alienPosition);
    }

    public void placeNote(MusicNote.Note note) {
        int notePosition = getNotePosition(note);

        if (note.isWhite()) {
            mStaffView.drawNote(notePosition);
        } else if (useFlats) {
            // All notes in the enum are sharp; flats need to move up a spot
            mStaffView.drawFlatNote(notePosition + 1);
        } else {
            mStaffView.drawSharpNote(notePosition);
        }
    }

    public void tick() {
        mStaffView.tick();

        if (mStaffView.numAliens() == 0) {
            mStaffView.setClosed(true);
            mFinished = true;
        }
    }

    private int getNotePosition(MusicNote.Note note) {
        int notePosition = note.getKeyNumber();

        if (mStaffView.isTreble())
            return notePosition - MusicNote.Note.E4.getKeyNumber();
        else
            return notePosition - MusicNote.Note.G2.getKeyNumber();
    }
}
