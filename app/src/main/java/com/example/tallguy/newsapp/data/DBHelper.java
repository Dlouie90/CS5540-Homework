package com.example.tallguy.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DBHelper helps create the database and the table for the application.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsitems.db";
    private static final String TAG = "dbhelper";

    // The constructor takes in a database name, context, version and creates the database.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // The onCreate method creates the table when the database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_NEWS.TABLE_NAME + " ("+
                Contract.TABLE_NEWS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_NAME_TITLE + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_NAME_URL + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_NAME_PUBLISHED_AT + " TEXT " + "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    /* onUpgrade is when the old database is still installed in the phone and a new one is created
        so it will upgrade the old database into the new one.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table " + Contract.TABLE_TODO.TABLE_NAME + " if exists;");
    }
}