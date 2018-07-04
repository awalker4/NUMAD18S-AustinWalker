package edu.neu.madcourse.austinwalker;

import android.app.Activity;
import android.content.Context;
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
    public int finalRanking; // Inverse of finalScore
    public String highestWord;
    public int highestWordRanking; // Inverse of word score
    public int highestWordScore;

    private String username;
    private TextView view;
    private boolean mLocal;

    private Context context;

    public UserScore(String id, String time, int score, String word, int wordScore) {
        imei = id;
        gameTime = time;
        finalScore = score;
        finalRanking = 0 - score;
        highestWord = word;
        highestWordScore = wordScore;
        highestWordRanking = 0 - wordScore;

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
        context = v.getContext();
        redisplayScore();
    }

    public void setLocal(boolean local) {
        mLocal = local;
    }

    private void redisplayScore() {
        if (mLocal) {
            view.setText("Score: " + Integer.toString(finalScore) +
                    "\nHighest Word Score: " + highestWordScore +
                    "\nHighest Word: " + highestWord);
        } else {
            String scoreStr = context.getResources().getString(R.string.global_score_label, username, finalScore, highestWord, highestWordScore);
            view.setText(scoreStr);
        }
    }
}
