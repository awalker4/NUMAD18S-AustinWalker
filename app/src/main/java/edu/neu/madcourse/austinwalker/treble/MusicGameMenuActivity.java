package edu.neu.madcourse.austinwalker.treble;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import edu.neu.madcourse.austinwalker.R;
import edu.neu.madcourse.austinwalker.scroggle.WordGameFragment;

public class MusicGameMenuActivity extends AppCompatActivity {

    private static final String KEY_RESTORE = "key_restore";
    private static final String PREF_RESTORE = "pref_restore";
    private WordGameFragment mGameFragment;
    private String mUserName;
    private AlertDialog mChangeNameDialog;
    private DatabaseReference usersRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game_menu);

        // New game button
        View newButton = findViewById(R.id.scroggle_new_game_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicGameMenuActivity.this, MusicGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
