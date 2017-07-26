package com.example.tallguy.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public static final String ACTION_REFRESH = "refresh";

    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = JSONParser.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();

    }
}
