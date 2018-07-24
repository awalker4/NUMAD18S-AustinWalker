package edu.neu.madcourse.austinwalker.treble;

public class MusicNote {

    // http://pages.mtu.edu/~suits/notefreqs.html
    public enum Note {
        G2(-10, 98f),
        G2S(-10, 103.83f, false),
        A2(-9, 110f),
        A2S(-9, 116.54f, false),
        B2(-8, 123.47f),
        C3(-7, 130.81f),
        C3S(-7, 138.59f, false),
        D3(-6, 146.83f),
        D3S(-6, 155.56f, false),
        E3(-5, 164.81f),
        F3(-4, 174.61f),
        F3S(-4, 185f, false),
        G3(-3, 196f),
        G3S(-3, 207.65f, false),
        A3(-2, 220f),
        A3S(-2, 233.08f, false),
        B3(-1, 246.94f),
        C4(0, 261.63f),
        C4S(0, 277.18f, false),
        D4(1, 293.66f),
        D4S(1, 311.13f, false),
        E4(2, 329.63f),
        F4(3, 349.23f),
        F4S(3, 369.99f, false),
        G4(4, 392f),
        G4S(4, 415.30f, false),
        A4(5, 440f),
        A4S(5, 466.16f, false),
        B4(6, 493.88f),
        C5(7, 523.25f),
        D5(8, 587.33f),
        D5S(8, 622.25f, false),
        E5(9, 659.25f),
        F5(10, 698.46f),
        F5S(10, 739.99f, false);

        private float frequency;
        private int keyNumber;
        private boolean isWhite;

        Note(int num, float freq) {
            keyNumber = num;
            frequency = freq;
            isWhite = true;
        }

        Note(int num, float freq, boolean white) {
            keyNumber = num;
            frequency = freq;
            isWhite = white;
        }

        public int getKeyNumber() {
            return keyNumber;
        }

        public float getFrequency() {
            return frequency;
        }

        public boolean isWhite() {
            return isWhite;
        }
    }

    public void playNote(double frequency) {
        MusicPlayer mp = new MusicPlayer(frequency);
        mp.start();
    }
}
