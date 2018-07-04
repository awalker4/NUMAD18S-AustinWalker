package edu.neu.madcourse.austinwalker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import static edu.neu.madcourse.austinwalker.AboutActivity.imeiStr;

public class MainActivity extends AppCompatActivity {

    final static String tag = "MainActivity";
    private static final int PERMISSION_READ_PHONE_STATE = 0;
    private static String imeiString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, "onCreate");

        setContentView(R.layout.activity_main);
    }

    public static String fetchIMEI(Activity currentActivity) {
        if (ActivityCompat.checkSelfPermission(currentActivity, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(currentActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_PHONE_STATE);
            imeiString = "null";
        } else {
            TelephonyManager tm = (TelephonyManager) currentActivity.getSystemService(Context.TELEPHONY_SERVICE);
            imeiString = tm.getDeviceId();
        }

        return imeiString;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    imeiString = tm.getDeviceId();
                } else {
                    imeiStr = "Permission denied";
                }

                return;
            }
        }
    }
}
