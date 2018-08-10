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
    private LevelTile[] mLevelTiles = new LevelTile[9];

    private int mHighestUnlocked = 5;
    private int mCurrentlySelected = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_game_level_select);

        Button goButton = findViewById(R.id.button_choose_level);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentlySelected < 0)
                    return;

                LevelTile tile = mLevelTiles[mCurrentlySelected];

                if (tile.isUnlocked()) {
                    startLevel(mCurrentlySelected);
                }
            }
        });

        initLevelButtons();
    }

    private void initLevelButtons() {
        final TextView displayText = findViewById(R.id.select_level_display);

        for (int i = 0; i < 9; i++) {
            final int levelNum = i;
            final Button levelButton = findViewById(LEVEL_BUTTON_IDS[i]);

            LevelTile tile = MusicGameLevelFactory.GetLevelTile(i);
            tile.setView(levelButton);

            if (levelNum <= mHighestUnlocked)
                tile.setUnlocked();
            else
                tile.setLocked();

            // Just testing...
            if (levelNum == 5)
                tile.setIsTreble(false);

            mLevelTiles[levelNum] = tile;

            // Listener
            levelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentlySelected != -1) {
                        LevelTile oldTile = mLevelTiles[mCurrentlySelected];
                        oldTile.setUnselected();
                    }

                    LevelTile newTile = mLevelTiles[levelNum];

                    if (newTile.isUnlocked()) {
                        mCurrentlySelected = levelNum;
                        newTile.setSelected();
                    } else {
                        mCurrentlySelected = -1;
                    }

                    displayText.setText(newTile.getDescription());
                }
            });

            // Setup the display
            levelButton.setText("Level " + Integer.toString(levelNum + 1));


        }
    }

    private void startLevel(int levelNum) {
        Intent intent = new Intent(MusicGameLevelSelectActivity.this, MusicGameActivity.class);
        MusicGameActivity.levelNum = levelNum; // TODO this sucks
        startActivity(intent);
    }
}
