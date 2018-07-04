package edu.neu.madcourse.austinwalker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class WordGameMenuActivity extends AppCompatActivity {

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
                Intent intent = new Intent(WordGameMenuActivity.this, WordGameActivity.class);
                startActivity(intent);
            }
        });

        // Resume game button
        View resumeGame = findViewById(R.id.scroggle_resume_game_button);
        resumeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordGameMenuActivity.this, WordGameActivity.class);
                intent.putExtra(WordGameActivity.KEY_RESTORE, true);
                startActivity(intent);
            }
        });

        // Leaderboard game button
        View scoresButton = findViewById(R.id.scoroggle_leaderboard_button);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordGameMenuActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        // Acknowledgments button
        View ackButton = findViewById(R.id.scroggle_ack_button);
        ackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordGameMenuActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });

        // Username
        final Button userNameButton = (Button) findViewById(R.id.scroggle_username_button);
        final String imei = fetchIMEI();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        usersRef.orderByKey().equalTo(imei).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserName = dataSnapshot.child(imei).getValue(String.class);
                userNameButton.setText(mUserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mUserName = "None";
                userNameButton.setText(mUserName);
            }
        });

        userNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeNameDialog();
            }
        });
    }

    public void onPause() {
        super.onPause();

        if (mChangeNameDialog != null)
            mChangeNameDialog.dismiss();
    }

    private String fetchIMEI() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();

    }

    private void showChangeNameDialog() {
        final EditText changeNameText = new EditText(getApplicationContext());
        changeNameText.setText(mUserName);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Name");
        builder.setView(changeNameText);

        builder.setCancelable(false);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserName = changeNameText.getText().toString();

                        HashMap<String, Object> toUpdate = new HashMap<>();
                        toUpdate.put(fetchIMEI(), mUserName);

                        usersRef.updateChildren(toUpdate);
                    }
                });

        mChangeNameDialog = builder.show();
    }
}
