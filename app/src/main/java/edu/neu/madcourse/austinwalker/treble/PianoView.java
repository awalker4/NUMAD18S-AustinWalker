package edu.neu.madcourse.austinwalker.treble;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import edu.neu.madcourse.austinwalker.R;

public class PianoView extends View {

    public PianoView(Context context) {
        super(context);
        setupView(context);
    }

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    private void setupView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
    }

    // TODO: draw the keys based on input attrs, add onKeyDownListener
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
