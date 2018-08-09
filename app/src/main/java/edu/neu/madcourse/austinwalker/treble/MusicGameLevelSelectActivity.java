package edu.neu.madcourse.austinwalker.treble;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameLevelSelectActivity extends AppCompatActivity {

    private final static int[] LEVEL_BUTTON_IDS = {R.id.button_select_level1, R.id.button_select_level2, R.id.button_select_level3, R.id.button_select_level4, R.id.button_select_level5, R.id.button_select_level6, R.id.button_select_level7, R.id.button_select_level8, R.id.button_select_level9};

    // FIXME: Shouldn't hardcode these
    private final static String[] LEVEL_NAMES = {"Intro", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};

    private int mHighestUnlocked = 5;
    private int mCurrentlySelected = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.neu.madcourse.austinwalker.R.layout.activity_music_game_level_select);

        Button goButton = findViewById(R.id.button_choose_level);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentlySelected > 0 && mCurrentlySelected <= mHighestUnlocked) {
                    startLevel(mCurrentlySelected);
                }
            }
        });

        initLevelButtons();
    }

    private void initLevelButtons() {
        final TextView displayText = findViewById(R.id.select_level_display);

        for (int i = 1; i <= 9; i++) {
            final int levelNum = i;
            final Button levelButton = findViewById(LEVEL_BUTTON_IDS[i - 1]);

            // Listener
            levelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentlySelected = levelNum;

                    String text = (mCurrentlySelected <= mHighestUnlocked ? LEVEL_NAMES[mCurrentlySelected-1] : "Locked");
                    displayText.setText(text);
                }
            });

            // Display correctly
            if (i <= mHighestUnlocked) {
                levelButton.getBackground().setLevel(2);
            }
        }
    }

    private void startLevel(int levelNum) {
        Intent intent = new Intent(MusicGameLevelSelectActivity.this, MusicGameActivity.class);
        startActivity(intent);
    }
}
