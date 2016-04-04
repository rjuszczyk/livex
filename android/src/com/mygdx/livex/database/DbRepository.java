package com.mygdx.livex.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Radek on 03.04.2016.
 */
public class DbRepository {


    public static SQLiteDatabase getDb(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getWritableDatabase();
    }
}

