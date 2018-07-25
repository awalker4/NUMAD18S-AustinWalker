package edu.neu.madcourse.austinwalker.treble;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

// Adapated from code at
// https://stackoverflow.com/questions/2413426/playing-an-arbitrary-tone-with-android
public class MusicPlayer implements Runnable {
    private AudioTrack audioTrack;
    private int sampleRate = 8000;
    private double duration = 0.5; // seconds
    private int numSamples;
    private double frequency;

    public MusicPlayer(double freq) {
        frequency = freq;
        double dnumSamples = duration * sampleRate;
        dnumSamples = Math.ceil(dnumSamples);
        numSamples = (int) dnumSamples;

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                numSamples * 2,
                AudioTrack.MODE_STATIC);
    }

    public void run() {
        double sample[] = new double[numSamples];
        byte generatedSnd[] = new byte[2 * numSamples];

        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin((2 * Math.PI - .001) * i / (sampleRate / frequency));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        int ramp = numSamples / 20;

        // Ramp amplitude up to avoid clicks
        for (int i = 0; i < ramp; ++i) {
            // scale to maximum amplitude
            final short val = (short) ((sample[i] * 32767) * i / ramp);
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        // Max amplitude
        for (int i = ramp; i < numSamples - ramp; ++i) {
            // scale to maximum amplitude
            final short val = (short) ((sample[i] * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        // Ramp amplitude down
        for (int i = numSamples - ramp; i < numSamples; i++) {
            final short val = (short) ((sample[i] * 32767) * (numSamples - i) / ramp);
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        try {
            audioTrack.write(generatedSnd, 0, generatedSnd.length);
            audioTrack.play();
        } catch (Exception e) {
            Log.e("MusicPlayer", "run: ", e);
        }

        int x = 0;

        // Montior playback to find when done
        do {
            if (audioTrack != null)
                x = audioTrack.getPlaybackHeadPosition();
            else
                x = numSamples;
        } while (x < numSamples);

        // Track finished
        if (audioTrack != null)
            audioTrack.release();
    }
}
