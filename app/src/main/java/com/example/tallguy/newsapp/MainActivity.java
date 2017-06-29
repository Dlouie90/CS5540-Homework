package com.example.tallguy.newsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;

    private JSONParser jParser;

    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);

        jParser = new JSONParser();

        progress = (ProgressBar) findViewById(R.id.progressBar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        newsRecyclerView.setLayoutManager(layoutManager);

        FetchNewsTask task = new FetchNewsTask();
        task.execute();

    }

    public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {
            ArrayList<NewsItem> list = null;

            URL newsRequestUrl = NetworkUtils.buildUrl(KeyContainer.KEY);
            Log.d("MainActivity", "url: " + newsRequestUrl.toString());
            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);
                Log.d("MainActivity", "JSON String: " + jsonNewsResponse);
                list = jParser.parseJSON(jsonNewsResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progress.setVisibility(View.GONE);
            if (data != null) {
                NewsAdapter adapter = new NewsAdapter(data);
                newsRecyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if(itemClicked == R.id.action_search) {
            Context context = MainActivity.this;
            String message = "Search Clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
