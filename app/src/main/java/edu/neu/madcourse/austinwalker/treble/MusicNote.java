package edu.neu.madcourse.austinwalker.treble;

public class MusicNote {

    public enum Note {
        C4(261.63f),
        C4S(277.18f, false),
        D4(293.66f),
        D4S(311.13f, false),
        E4(329.63f),
        F4(349.23f),
        F4S(369.99f, false),
        G4(392),
        G4S(415.30f, false),
        A4(440f),
        A4S(466.16f, false),
        B4(493.88f),
        C5(523.25f);

        private float frequency;
        private boolean isWhite;

        Note(float freq) {
            frequency = freq;
            isWhite = true;
        }

        Note(float freq, boolean white) {
            frequency = freq;
            isWhite = white;
        }

        public float getFrequency() {
            return frequency;
        }

        public boolean isWhite() {
            return isWhite;
        }
    }

    // http://pages.mtu.edu/~suits/notefreqs.html


    // Private fields
    private static final String TAG = "MusicNote";

    public void playNote(double frequency) {
        MusicPlayer mp = new MusicPlayer(frequency);
        mp.start();
    }
}
