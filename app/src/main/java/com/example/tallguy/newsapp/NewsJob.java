package com.example.tallguy.newsapp;

import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by TallGuy on 7/26/2017.
 */

/*
    This is the actual Job that will be running every minute that firebase dispatcher will use.
 */

public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters params) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            // RefreshTasks will refresh the News items in the background thread.
            @Override
            protected Object doInBackground(Object[] params) {
                RefreshTasks.refreshArticles(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(params, false);
                super.onPostExecute(o);
            }
        };

        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        if (mBackgroundTask != null) mBackgroundTask.cancel(false);

        return true;
    }
}
