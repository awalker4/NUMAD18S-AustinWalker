package edu.neu.madcourse.austinwalker.treble;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.austinwalker.R;

public class LevelTile {

    private View mView;

    private static Drawable trebleClef;
    private static Drawable bassClef;
    private static Drawable buttonDrawable;

    private String mLevelName;
    private String mLevelDescription;

    private boolean mUnlocked = true;
    private boolean mIsTreble = true;

    public LevelTile(String name, String desc) {
        mLevelName = name;
        mLevelDescription = desc;
    }

    public void setView(View view) {
        mView = view;
    }

    public String getDescription() {
        if (mUnlocked)
            return mLevelDescription;
        else
            return "Locked";
    }

    public boolean isUnlocked() {
        return mUnlocked;
    }

    public void setSelected() {
        addClef();
    }

    public void setUnselected() {
        removeClef();
    }

    public void setUnlocked() {
        mUnlocked = true;
        mView.getBackground().setLevel(1);
    }

    public void setLocked() {
        mUnlocked = false;
        mView.getBackground().setLevel(0);
    }

    public void setIsTreble(boolean treble) {
        mIsTreble = treble;
    }

    private void addClef() {
        Button button = (Button) mView;
        button.setText("");

        if (trebleClef == null)
            trebleClef = mView.getResources().getDrawable(R.drawable.treble_clef);

        if (bassClef == null)
            bassClef = mView.getResources().getDrawable(R.drawable.bass_clef);

        if (mIsTreble)
            button.setBackground(trebleClef);
        else
            button.setBackground(bassClef);
    }

    private void removeClef() {
        if (buttonDrawable == null)
            buttonDrawable = mView.getResources().getDrawable(R.drawable.select_level_button);

        mView.setBackground(buttonDrawable);

        Button button = (Button) mView;
        button.setText(mLevelName);

        if (mUnlocked)
            mView.getBackground().setLevel(1);
        else
            mView.getBackground().setLevel(0);
    }
}
