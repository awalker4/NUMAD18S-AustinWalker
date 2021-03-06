package edu.neu.madcourse.austinwalker;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import edu.neu.madcourse.austinwalker.scroggle.WordDatabase;

public class MyApplication extends Application {

    public SQLiteDatabase dictionaryDb;

    @Override
    public void onCreate() {
        super.onCreate();
        WordDatabase dbLoader = new WordDatabase(getApplicationContext());
        dictionaryDb = dbLoader.getReadableDatabase();
    }

}
