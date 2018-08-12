package edu.neu.madcourse.austinwalker.treble;

import java.util.PriorityQueue;

// Add some abstraction on top of StaffView
// At this level, we deal with note names rather than staff ranking
public class Staff {

    private StaffView mStaffView;

    private boolean mUseFlats = false;
    private boolean mFinished;
    private int mNumTicks = 0;

    private PriorityQueue<EnemyQueueItem> enemyQueue;

    public Staff(StaffView view) {
        mStaffView = view;
        reset();
    }

    public void setTreble(boolean treble) {
        mStaffView.setTreble(treble);
    }

    public void setUseFlats(boolean flat) {
        mUseFlats = flat;
    }

    public boolean isFinished() {
        return mFinished;
    }

    // We have aliens, but there are no bullets to clear them
    public boolean isStuck() {
        return (mStaffView.hasUnreachableAliens() && enemyQueue.size() == 0);
    }

    public void setFinished() {
        mFinished = true;
    }

    public boolean isWin() {
        return mStaffView.isClosed();
    }

    public void placeNote(MusicNote.Note note) {
        int notePosition = getNotePosition(note);

        if (note.isWhite()) {
            mStaffView.drawNote(notePosition);
        } else if (mUseFlats) {
            // All notes in the enum are sharp; flats need to move up a spot
            mStaffView.drawFlatNote(notePosition + 1);
        } else {
            mStaffView.drawSharpNote(notePosition);
        }
    }

    // Queue an alien to be added after the specified tick count
    // Queue its bullet to shoot after specified delay (-1 for no bullet)
    // Alien goes away if removeDelay is positive
    public void queueAlien(MusicNote.Note note, int numTicks, int shootDelay, int removeDelay) {
        assert shootDelay <= removeDelay; // Don't disappear and then shoot

        enemyQueue.add(new EnemyQueueItem(note, true, numTicks));

        if (shootDelay >= 0)
            enemyQueue.add(new EnemyQueueItem(note, false, numTicks + shootDelay));

        if (removeDelay >= 0) {
            EnemyQueueItem item = new EnemyQueueItem(note, true, numTicks + removeDelay);
            item.setRemove();
            enemyQueue.add(item);
        }
    }

    public void tick() {
        addItemsForTick();
        mStaffView.tick();
        mNumTicks++;

        if (numAliens() == 0) {
            mStaffView.setIsClosed(true);
            mFinished = true;
        } else if (mStaffView.isClefHit() || isStuck()) {
            mFinished = true;
        }
    }

    public void reset() {
        mFinished = false;
        mNumTicks = 0;
        enemyQueue = new PriorityQueue<>();

        mStaffView.reset();
    }

    // Add any items that may have appeared at this tick
    private void addItemsForTick() {
        while (enemyQueue.size() > 0 && enemyQueue.peek().tickNumber <= mNumTicks) {
            EnemyQueueItem item = enemyQueue.poll();

            if (item.isAlien) {
                if (item.isRemove)
                    mStaffView.removeAlien(item.position);
                else
                    mStaffView.addAlien(item.position);
            } else
                mStaffView.addBullet(item.position);
        }
    }

    private int numAliens() {
        return mStaffView.numAliens() + enemyQueue.size();
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
        public boolean isAlien; // FIXME: enum of items
        public boolean isRemove;
        public int position;
        public int tickNumber;

        public EnemyQueueItem(MusicNote.Note note, boolean isAlien, int tick) {
            position = getNotePosition(note);
            this.isAlien = isAlien;
            tickNumber = tick;
        }

        public void setRemove() {
            isRemove = true;
        }

        public int compareTo(EnemyQueueItem other) {
            return tickNumber - other.tickNumber;
        }
    }
}
