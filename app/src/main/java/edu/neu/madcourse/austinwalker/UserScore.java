package edu.neu.madcourse.austinwalker;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserScore {
    private static final String TAG = "UserScore";

    public String imei;
    public String gameTime;
    public int finalScore;
    public String highestWord;
    public int highestWordScore;

    private String username;
    private TextView view;

    public UserScore(String id, String time, int score, String word, int wordScore) {
        imei = id;
        gameTime = time;
        finalScore = score;
        highestWord = word;
        highestWordScore = wordScore;

        Log.d(TAG, "UserScore: full");
    }

    public UserScore() {
        imei = "null";
        gameTime = "never";
        finalScore = 0;
        highestWord = "";
        highestWordScore = 0;

        Log.d(TAG, "UserScore: blank");
    }

    public String getName() {
        return username;
    }

    public void listenForName() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.orderByKey().equalTo(imei).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = dataSnapshot.child(imei).getValue(String.class);
                redisplayScore();
                Log.d(TAG, "onDataChange: " + username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                username = "blank";
                Log.d(TAG, "onCancelled: " + imei);
            }
        });
    }

    public void setText(TextView v) {
        view = v;
        listenForName();
        redisplayScore();
    }

    private void redisplayScore() {
        view.setText("User: " + username +
                "\nScore: " + Integer.toString(finalScore) +
                "\nHighest Word Score: " + highestWordScore +
                "\nHighest Word: " + highestWord);
    }
}
