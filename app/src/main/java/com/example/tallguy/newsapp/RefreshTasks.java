package com.example.tallguy.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tallguy.newsapp.data.DBHelper;
import com.example.tallguy.newsapp.data.DatabaseUtils;
import com.example.tallguy.newsapp.data.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TallGuy on 7/25/2017.
 */

public class RefreshTasks {

    static final String TAG = "RefreshTasks";

    /*
     The method refreshArticles takes in a context and refreshes the database by deleting it
     and recreating it from the JSON source.
      */
    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url); // Getting the JSON
            Log.d(TAG, "JSON: " + json);
            result = JSONParser.parseJSON(json); // Parsing the JSON
            DatabaseUtils.bulkInsert(db, result);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();

    }
}
