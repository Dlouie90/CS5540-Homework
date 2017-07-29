package com.example.tallguy.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.tallguy.newsapp.data.Contract;
import com.example.tallguy.newsapp.data.DBHelper;
import com.example.tallguy.newsapp.data.DatabaseUtils;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener {

    static final String TAG = "mainactivity";
    private RecyclerView newsRecyclerView;
    private NewsAdapter adapter;
    private ProgressBar progress;
    private SQLiteDatabase db;
    private Cursor cursor;

    private static final int NEWS_LOADER = 1;

    /*
        When the MainActivity is created, the recycler view, progress bar, shared preferences, etc.
        will be initialized.
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(layoutManager);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        /*
         Checking if the app has been installed before. If it has not been installed before,
         change isfirst to false in SharedPreferences.
          */
        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);
    }

    // The method onStart initializes the database, cursor, adapter, and recycler view.
    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor, this);
        newsRecyclerView.setAdapter(adapter);
    }

    // When the app is stopped, close the database and close the cursor.
    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.action_search) {
            load();
        }
        return true;
    }

    // When the loader is created, it will return a AsyncTaskLoader.
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "on CreateLoader has started");
        return new AsyncTaskLoader<Void>(this) {

            // Show the progress bar when loading
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.d(TAG, "on StartLoading has started");
                progress.setVisibility(View.VISIBLE);

            }

            // In the background thread, refresh the news items
            @Override
            public Void loadInBackground() {
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    /*
        When the load is finished, the progress bar goes invisible and reinitialize the db,
        cursor, adapter, and the recycler view.
      */
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        Log.d(TAG, "on Load Finished has started");
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        adapter = new NewsAdapter(cursor, this);
        newsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    /*
        When you click on the item, it will create an intent and take you to a separate webpage
        with the corresponding url for that news.
      */
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    // The method load is mainly used for creating a loader or restarting a loader.
    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }
}
