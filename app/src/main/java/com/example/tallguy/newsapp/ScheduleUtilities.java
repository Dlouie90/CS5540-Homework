package com.example.tallguy.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by TallGuy on 7/26/2017.
 */

/*
    This class uses firebase dispatcher and schedule the NewsJob to run every minute.
 */
public class ScheduleUtilities {
    private static final int SCHEDULE_INTERVAL_MINUTES = 1;
    private static final int SCHEDULE_INTERVAL_SECONDS = (int)(TimeUnit.MINUTES.toSeconds(SCHEDULE_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = SCHEDULE_INTERVAL_SECONDS;
    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleRefresh(@NonNull final Context context) {
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class) // The Job that will be running every minute
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER) // The Job will run until the phone dies
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                        SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXTIME_SECONDS))// every minute
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}
