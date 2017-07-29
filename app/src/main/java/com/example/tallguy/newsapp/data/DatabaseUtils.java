package com.example.tallguy.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static com.example.tallguy.newsapp.data.Contract.TABLE_NEWS.*;

/**
 * Created by TallGuy on 7/25/2017.
 */

/*
    DatabaseUtils is where I create my three methods that will be useful for maintaining my db.
 */

public class DatabaseUtils {

    // The getAll method returns a cursor that has the results of everything in the table
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    /* The method bulkInsert takes in a db and a list of news items and inserts
        all the news items into the database.
      */
    public static void bulkInsert (SQLiteDatabase db, ArrayList<NewsItem> items) {
        db.beginTransaction();
        try {
            for (NewsItem i : items) {
                ContentValues cv = new ContentValues();
                Log.d("DatabaseUtils: ", "Author's name: " + i.getAuthor());
                cv.put(COLUMN_NAME_AUTHOR, i.getAuthor());
                cv.put(COLUMN_NAME_TITLE, i.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, i.getDescription());
                cv.put(COLUMN_NAME_URL, i.getUrl());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, i.getUrlToImage());
                cv.put(COLUMN_NAME_PUBLISHED_AT, i.getPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            Log.d("DatabaseUtils: ", "List size: " + items.size());
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    // The method deleteAll deletes the table I created
    public static void deleteAll(SQLiteDatabase db) { db.delete(TABLE_NAME, null, null); }
}
