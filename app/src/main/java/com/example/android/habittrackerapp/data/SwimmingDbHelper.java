package com.example.android.habittrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittrackerapp.data.SwimmingContact.SwimmingEntry;

/**
 * Created by Kasia on 2017-07-19.
 */

public class SwimmingDbHelper extends SQLiteOpenHelper {

    // Name of the database file.
    private static final String DATABASE_NAME = "habit.db";

    //Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    //Constructs a new instance of {@link SwimmingDbHelper}.
    // @param context of the app.
    public SwimmingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //This is called when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the swimming table.
        String SQL_CREATE_SWIMMING_TABLE = "CREATE TABLE " + SwimmingEntry.TABLE_NAME + " ("
                + SwimmingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SwimmingEntry.COLUMN_DAY + " INTEGER NOT NULL, "
                + SwimmingEntry.COLUMN_STYLE + " TEXT NOT NULL, "
                + SwimmingEntry.COLUMN_TIME + " INTEGER NOT NULL DEFAULT 0,"
                + SwimmingEntry.COLUMN_NOTE + " TEXT);";

        // Execute the SQL statement.
        db.execSQL(SQL_CREATE_SWIMMING_TABLE);
    }

    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}