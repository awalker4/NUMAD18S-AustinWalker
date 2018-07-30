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

        // New game button
        View newButton = findViewById(R.id.music_game_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicGameMenuActivity.this, MusicGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
