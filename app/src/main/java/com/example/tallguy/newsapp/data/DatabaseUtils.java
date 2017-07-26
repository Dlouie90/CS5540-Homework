package com.example.tallguy.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.tallguy.newsapp.data.Contract.TABLE_NEWS.*;

/**
 * Created by TallGuy on 7/25/2017.
 */

public class DatabaseUtils {

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

    public static void bulkInsert (SQLiteDatabase db, ArrayList<NewsItem> items) {
        db.beginTransaction();
        try {
            for (NewsItem i : items) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_AUTHOR, i.getAuthor());
                cv.put(COLUMN_NAME_TITLE, i.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, i.getDescription());
                cv.put(COLUMN_NAME_URL, i.getUrl());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, i.getUrlToImage());
                cv.put(COLUMN_NAME_PUBLISHED_AT, i.getPublishedAt());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) { db.delete(TABLE_NAME, null, null); }
}
