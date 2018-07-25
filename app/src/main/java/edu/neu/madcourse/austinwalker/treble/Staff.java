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

    public void tick() {
        mStaffView.tick();

        if (mStaffView.numAliens() == 0)
            mStaffView.setClosed(true);
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

        tick();
    }

    private int getNotePosition(MusicNote.Note note) {
        int notePosition = note.getKeyNumber();

        if (mStaffView.isTreble())
            return notePosition - MusicNote.Note.E4.getKeyNumber();
        else
            return notePosition - MusicNote.Note.G2.getKeyNumber();
    }

}
