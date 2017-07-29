package com.example.tallguy.newsapp;


import android.util.Log;

import com.example.tallguy.newsapp.data.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/*
    This is my custom java class for parsing JSON.
 */
public class JSONParser {

    /*
    The method parseJSON takes in a JSON string and creates an arraylist of newsitems and
    returns it.
     */
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> list = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");

        for (int i = 0; i < articles.length(); i++) {
            JSONObject item = articles.getJSONObject(i);
            String author = item.getString("author");
            String title = item.getString("title");
            String description = item.getString("description");
            String url = item.getString("url");
            String urlToImage = item.getString("urlToImage");
            String publishedAt = item.getString("publishedAt");
            NewsItem ni = new NewsItem(author, title, description, url, urlToImage, publishedAt);
            list.add(ni);
        }
        Log.d("JSONParser", "Size of ArrayList: " + list.size());
        return list;
    }
}
