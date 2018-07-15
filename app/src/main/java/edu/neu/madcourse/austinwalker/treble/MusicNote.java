package edu.neu.madcourse.austinwalker.treble;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

// Adapated from code at
// https://stackoverflow.com/questions/2413426/playing-an-arbitrary-tone-with-android
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

    // Private fields
    private static final String TAG = "MusicNote";
    private static AudioTrack audioTrack;

    private static double duration = 0.5; // Seconds

    public static void playTone(double frequency) {
        int sampleRate = 8000;              // a number

        double dnumSamples = duration * sampleRate;
        dnumSamples = Math.ceil(dnumSamples);
        int numSamples = (int) dnumSamples;
        double sample[] = new double[numSamples];
        byte generatedSnd[] = new byte[2 * numSamples];


        for (int i = 0; i < numSamples; ++i) {      // Fill the sample array
            sample[i] = Math.sin(frequency * 2 * Math.PI * i / (sampleRate));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        int ramp = numSamples / 20;

        // Ramp amplitude up to avoid clicks
        for (int i = 0; i < ramp; ++i) {
            double dVal = sample[i];
            // Ramp up to maximum
            final short val = (short) ((dVal * 32767 * i / ramp));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        // Max amplitude
        for (int i = ramp; i < numSamples - ramp; ++i) {
            double dVal = sample[i];
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        // Ramp amplitude down
        for (int i = numSamples - ramp; i < numSamples; ++i) {
            double dVal = sample[i];
            // Ramp down to zero
            final short val = (short) ((dVal * 32767 * (numSamples - i) / ramp));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        try {
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, (int) numSamples * 2,
                    AudioTrack.MODE_STATIC);
            audioTrack.write(generatedSnd, 0, generatedSnd.length);     // Load the track
            audioTrack.play();                                          // Play the track
        } catch (Exception e) {
            Log.e(TAG, "playTone: ", e);
        }

        int x = 0;
        do
        {                                                     // Montior playback to find when done
            if (audioTrack != null)
                x = audioTrack.getPlaybackHeadPosition();
            else
                x = numSamples;
        } while (x < numSamples);

        if (audioTrack != null) audioTrack.release();           // Track play done. Release track.

    }
}
