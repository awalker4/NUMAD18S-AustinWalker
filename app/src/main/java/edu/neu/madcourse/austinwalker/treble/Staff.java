package edu.neu.madcourse.austinwalker.treble;


import android.util.SparseArray;

// Add some abstraction on top of StaffView
// At this level, we deal with note names rather than staff ranking
public class Staff {

    private StaffView mStaffView;

    private boolean useFlats = false;
    private boolean mFinished;
    private int mNumTicks = 0;

    private SparseArray<MusicNote.Note> alienQueue;

    public Staff(StaffView view) {
        mStaffView = view;
        mFinished = false;

        alienQueue = new SparseArray<>();
    }

    public void setTreble(boolean treble) {
        mStaffView.setTreble(treble);
    }

    public boolean isFinished() {
        return mFinished;
    }

    // Queue an alien to be added after the specified tick count
    public void queueAlien(MusicNote.Note alienNote, int numTicks) {
        alienQueue.append(numTicks, alienNote);
    }

    // Add an alien to the specified note position
    private void addAlien(MusicNote.Note alienNote) {
        int alienPosition = getNotePosition(alienNote);
        mStaffView.addAlien(alienPosition);
    }

    private int numAliens() {
        return mStaffView.numAliens() + alienQueue.size();
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
        if (alienQueue.get(mNumTicks) != null) {
            addAlien(alienQueue.get(mNumTicks));
            alienQueue.remove(mNumTicks);
        }

        mStaffView.tick();

        if (numAliens() == 0) {
            closeStaff();
        }

        mNumTicks++;
    }

    private void closeStaff() {
        mStaffView.setClosed(true);
        mFinished = true;
    }

    private int getNotePosition(MusicNote.Note note) {
        int notePosition = note.getKeyNumber();

        if (mStaffView.isTreble())
            return notePosition - MusicNote.Note.E4.getKeyNumber();
        else
            return notePosition - MusicNote.Note.G2.getKeyNumber();
    }
}
