package edu.neu.madcourse.austinwalker;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "LeaderboardActivity";
    ListView listView;
    FirebaseListAdapter<UserScore> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaderboard);

        listView = (ListView) findViewById(R.id.my_list_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("scores");

        mAdapter = new FirebaseListAdapter<UserScore>(this, UserScore.class, android.R.layout.simple_list_item_1, myRef.orderByPriority().limitToFirst(10)) {
            @Override
            protected void populateView(View v, UserScore model, int position) {
                TextView view = (TextView) v;

                model.setText(view);
            }
        };

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;

                UserScore itemValue = (UserScore) listView.getItemAtPosition(itemPosition);
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  IMEI : " + itemValue.imei, Toast.LENGTH_LONG)
                        .show();
            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    private String fetchIMEI() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}