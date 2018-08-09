package edu.neu.madcourse.austinwalker.treble;

import android.graphics.drawable.Drawable;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.austinwalker.R;

public class LevelSelectTile {

    private View mView;

    private static Drawable trebleClef;
    private static Drawable bassClef;
    private static Drawable buttonDrawable;

    private String mLevelName;
    private String mLevelDescription;

    private Boolean mSelected;
    private Boolean mUnlocked;

    public LevelSelectTile(View context, String name, String desc) {
        mView = context;
        mLevelName = name;
        mLevelDescription = desc;
        mSelected = false;
        mUnlocked = true;

        if (trebleClef == null)
            trebleClef = mView.getResources().getDrawable(R.drawable.treble_clef);

        if (bassClef == null)
            bassClef = mView.getResources().getDrawable(R.drawable.bass_clef);

        if (buttonDrawable == null)
            buttonDrawable = mView.getResources().getDrawable(R.drawable.select_level_button);
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
        mSelected = true;
        mView.setBackground(trebleClef);

        Button button = (Button) mView;
        button.setText("");
    }

    public void setUnselected() {
        mSelected = false;
        removeClef();

        if (mUnlocked)
            mView.getBackground().setLevel(1);
        else
            mView.getBackground().setLevel(0);
    }

    public void setUnlocked() {
        mUnlocked = true;
        mView.getBackground().setLevel(1);
    }

    public void setLocked() {
        mUnlocked = false;
        mView.getBackground().setLevel(0);
    }

    private void removeClef() {
        mView.setBackground(buttonDrawable);

        Button button = (Button) mView;
        button.setText(mLevelName);
    }
}
