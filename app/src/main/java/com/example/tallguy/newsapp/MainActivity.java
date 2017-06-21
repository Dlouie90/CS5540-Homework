package com.example.tallguy.newsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText searchBoxEditText;

    private TextView newsTextView;

    private ProgressBar progress;

    private final String KEY = "ff4125520ed9458a9f80836e51a7e2b7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBoxEditText = (EditText) findViewById(R.id.search_box);

        newsTextView = (TextView) findViewById(R.id.news_text_data);

        progress = (ProgressBar) findViewById(R.id.progressBar);

        loadNewsData();
    }

    private void loadNewsData() {
        new FetchNewsTask().execute(KEY);
    }

    private void makeSearchQuery() {
        String query = searchBoxEditText.getText().toString();
        URL searchUrl = NetworkUtils.buildUrl(KEY);
    }

    public class FetchNewsTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String key = params[0];
            URL newsRequestUrl = NetworkUtils.buildUrl(key);

            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);
                return jsonNewsResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {

            progress.setVisibility(View.GONE);
            newsTextView.append(s + "\n");
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
