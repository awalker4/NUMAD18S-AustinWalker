package edu.neu.madcourse.austinwalker.treble;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Scanner;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameLevelSelectActivity extends AppCompatActivity {

    public static final String PREF_LEVELS = "pref_levels";

    private final static int[] LEVEL_BUTTON_IDS = {R.id.button_select_level1, R.id.button_select_level2, R.id.button_select_level3, R.id.button_select_level4, R.id.button_select_level5, R.id.button_select_level6, R.id.button_select_level7, R.id.button_select_level8, R.id.button_select_level9};
    private LevelTile[] mLevelTiles = new LevelTile[9];

    private static int mHighestUnlocked = 0;
    private int mCurrentlySelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_game_level_select);

        Button goButton = findViewById(R.id.button_choose_level);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentlySelected < 0 || mCurrentlySelected >= 9)
                    return;

                LevelTile tile = mLevelTiles[mCurrentlySelected];

                if (tile.isUnlocked()) {
                    startLevel(mCurrentlySelected);
                }
            }
        });

        initLevelButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get saved highest level (or use current static value if it just went up)
        String gameData = getPreferences(MODE_PRIVATE).getString(PREF_LEVELS, "0");
        Scanner scanner = new Scanner(gameData);
        mHighestUnlocked = Math.max(scanner.nextInt(), mHighestUnlocked);

        showUnlockedLevels();
    }

    protected void onPause() {
        super.onPause();

        String gameData = Integer.toString(mHighestUnlocked);
        getPreferences(MODE_PRIVATE).edit().putString(PREF_LEVELS, gameData).commit();
    }

    public static void setHighestUnlocked(int levelPlayed) {
        mHighestUnlocked = Math.max(levelPlayed + 1, mHighestUnlocked);
    }

    private void initLevelButtons() {
        final TextView displayText = findViewById(R.id.select_level_display);

        for (int i = 0; i < 9; i++) {
            final int levelNum = i;
            final Button levelButton = findViewById(LEVEL_BUTTON_IDS[i]);

            MusicGameLevel levelData = MusicGameLevelFactory.GetLevel(i);
            LevelTile tile = levelData.getLevelTile();

            tile.setView(levelButton);

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

    private void showUnlockedLevels() {
        for (int i = 0; i < 9; i++) {
            if (i <= mHighestUnlocked)
                mLevelTiles[i].setUnlocked();
            else
                mLevelTiles[i].setLocked();
        }
    }

    private void startLevel(int levelNum) {
        Intent intent = new Intent(MusicGameLevelSelectActivity.this, MusicGameActivity.class);
        MusicGameActivity.levelNum = levelNum; // TODO this sucks
        startActivity(intent);
    }
}
