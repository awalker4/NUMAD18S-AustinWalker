package edu.neu.madcourse.austinwalker.treble;


import java.util.PriorityQueue;

// Add some abstraction on top of StaffView
// At this level, we deal with note names rather than staff ranking
public class Staff {

    private StaffView mStaffView;

    private boolean useFlats = false;
    private boolean mFinished;
    private int mNumTicks = 0;

    private PriorityQueue<EnemyQueueItem> enemyQueue;

    public Staff(StaffView view) {
        mStaffView = view;
        mFinished = false;

        enemyQueue = new PriorityQueue<>();
    }

    public void setTreble(boolean treble) {
        mStaffView.setTreble(treble);
    }

    public boolean isFinished() {
        return mFinished;
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

    // Queue an alien to be added after the specified tick count
    // Queue its bullet to shoot after specified delay
    public void queueAlien(MusicNote.Note alienNote, int numTicks, int shootDelay) {
        enemyQueue.add(new EnemyQueueItem(alienNote, true, numTicks));
        enemyQueue.add(new EnemyQueueItem(alienNote, false, numTicks + shootDelay));
    }

    public void tick() {
        addItemsForTick();
        mStaffView.tick();
        mNumTicks++;

        if (numAliens() == 0) {
            closeStaff();
        }
    }

    // Add any items that may have appeared at this tick
    private void addItemsForTick() {
        while (enemyQueue.size() > 0 && enemyQueue.peek().tickNumber <= mNumTicks) {
                EnemyQueueItem item = enemyQueue.poll();

                if (item.isAlien)
                    mStaffView.addAlien(item.position);
                else
                    mStaffView.addBullet(item.position);
        }
    }

    private int numAliens() {
        return mStaffView.numAliens() + enemyQueue.size();
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

    // Used to store aliens/bullets on a PriorityQueue
    // that will add elements to the staff at various ticks
    private class EnemyQueueItem implements Comparable<EnemyQueueItem> {
        public boolean isAlien;
        public int position;
        public int tickNumber;

        public EnemyQueueItem(MusicNote.Note note, boolean isAlien, int tick) {
            position = getNotePosition(note);
            this.isAlien = isAlien;
            tickNumber = tick;
        }

        public int compareTo(EnemyQueueItem other) {
            return tickNumber - other.tickNumber;
        }
    }
}
