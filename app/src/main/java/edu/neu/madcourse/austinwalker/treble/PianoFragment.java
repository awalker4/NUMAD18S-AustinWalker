package edu.neu.madcourse.austinwalker.treble;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class PianoFragment extends Fragment {

    public interface PianoListener {
        void onKeyPressed(int key);
    }

    private PianoListener keyListener;

    public PianoFragment() {


        keyListener = null;
    }

    public void setKeyPressedListener(PianoListener l) {
        keyListener = l;
    }

};