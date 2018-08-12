package edu.neu.madcourse.austinwalker.treble;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.neu.madcourse.austinwalker.R;

public class MusicGameMenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_game_menu);

        MusicGameLevelFactory.setContext(this);

        // Play game button
        View playButton = findViewById(R.id.play_music_game_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicGameMenuActivity.this, MusicGameLevelSelectActivity.class);
                startActivity(intent);
            }
        });

        // Reset game button
        View resetButton = findViewById(R.id.reset_music_game_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicGameLevelSelectActivity.resetHighestUnlocked();
            }
        });
    }
}
