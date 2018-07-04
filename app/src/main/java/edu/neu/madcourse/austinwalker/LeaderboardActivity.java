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
    private ListView mListView;
    private FirebaseDatabase mDatabase;
    private boolean showGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaderboard);

        mListView = (ListView) findViewById(R.id.my_list_view);

        mDatabase = FirebaseDatabase.getInstance();
        final String imei = MainActivity.fetchIMEI(this);

        Spinner dropdown = findViewById(R.id.leaderboard_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Local (final score)", "Global (final score)", "Local (highest word)", "Global (highest word)"});

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Query data;

                switch (position) {
                    case 0:
                        data = mDatabase.getReference("userScores").child(imei).orderByChild("finalRanking").limitToFirst(10);
                        showGlobal = false;
                        break;
                    case 1:
                        data = mDatabase.getReference("allScores").orderByChild("finalRanking").limitToFirst(10);
                        showGlobal = true;
                        break;
                    case 2:
                        data = mDatabase.getReference("userScores").child(imei).orderByChild("highestWordRanking").limitToFirst(10);
                        showGlobal = false;
                        break;
                    default:
                        data = mDatabase.getReference("allScores").orderByChild("highestWordRanking").limitToFirst(10);
                        showGlobal = true;
                        break;
                }

                setAdapter(data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setAdapter(mDatabase.getReference("userScores").child(imei).orderByChild("finalRanking").limitToFirst(10));
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

    private void setAdapter(Query data) {
        mListView.setAdapter(new FirebaseListAdapter<UserScore>(this, UserScore.class, android.R.layout.simple_list_item_1, data) {
            @Override
            protected void populateView(View v, UserScore model, int position) {
                TextView view = (TextView) v;
                model.setLocal(!showGlobal);
                model.setText(view);
            }
        });
    }
}
