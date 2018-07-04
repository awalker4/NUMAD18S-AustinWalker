package edu.neu.madcourse.austinwalker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "LeaderboardActivity";
    private boolean mShowGlobal;
    private ListView mListView;
    private FirebaseListAdapter<UserScore> mAdapter;
    private DatabaseReference mScoresRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaderboard);

        mListView = (ListView) findViewById(R.id.my_list_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mScoresRef = database.getReference("scores");

        Spinner dropdown = findViewById(R.id.leaderboard_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Local", "Global"});
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showLocalScores();
                        break;
                    case 1:
                        showGlobalScores();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showLocalScores();
            }
        });

    }

    private void showLocalScores() {
        String imei = MainActivity.fetchIMEI(this);
        Query localScores = mScoresRef.orderByChild("imei").equalTo(imei).limitToFirst(10);

        mListView.setAdapter(new FirebaseListAdapter<UserScore>(this, UserScore.class, android.R.layout.simple_list_item_1, localScores) {
            @Override
            protected void populateView(View v, UserScore model, int position) {
                TextView view = (TextView) v;
                model.setLocal(true);
                model.setText(view);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;

                UserScore itemValue = (UserScore) mListView.getItemAtPosition(itemPosition);
                Toast.makeText(getApplicationContext(),
                        "Pinging users not implemented yet",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void showGlobalScores() {
        Query globalScores = mScoresRef.orderByPriority().limitToFirst(10);

        mListView.setAdapter(new FirebaseListAdapter<UserScore>(this, UserScore.class, android.R.layout.simple_list_item_1, globalScores) {
            @Override
            protected void populateView(View v, UserScore model, int position) {
                TextView view = (TextView) v;
                model.setLocal(false);
                model.setText(view);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;

                UserScore itemValue = (UserScore) mListView.getItemAtPosition(itemPosition);
                Toast.makeText(getApplicationContext(),
                        "Pinging users not implemented yet",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}