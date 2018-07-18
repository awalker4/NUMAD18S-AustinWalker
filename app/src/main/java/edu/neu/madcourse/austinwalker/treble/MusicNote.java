package edu.neu.madcourse.austinwalker.treble;

public class MusicNote {

    // http://pages.mtu.edu/~suits/notefreqs.html
    public static double C4 = 261.63;
    public static double C4S = 277.18;
    public static double D4 = 293.66;
    public static double D4S = 311.13;
    public static double E4 = 329.63;
    public static double F4 = 349.23;
    public static double F4S = 369.99;
    public static double G4 = 392;
    public static double G4S = 415.30;
    public static double A4 = 440;
    public static double A4S = 466.16;
    public static double B4 = 493.88;
    public static double C5 = 523.25;

    // Private fields
    private static final String TAG = "MusicNote";

    public void playNote(double frequency) {
        MusicPlayer mp = new MusicPlayer(frequency);
        mp.start();
    }
}
