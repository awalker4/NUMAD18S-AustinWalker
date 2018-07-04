package edu.neu.madcourse.austinwalker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    static String imeiStr = "null";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    protected void onResume() {
        super.onResume();
        TextView imei = findViewById(R.id.imei_text);
        String imeiLabel = getResources().getString(R.string.imei_text);

        // Get device IMEI or show null if not allowed
        imei.setText(imeiLabel + " " + MainActivity.fetchIMEI(this));
    }
}
