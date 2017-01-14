package com.tynercontracting.pottyminder;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.common.api.GoogleApiClient;
import com.tynercontracting.pottyminder.common.logger.Log;
import com.tynercontracting.pottyminder.common.logger.LogFragment;
import com.tynercontracting.pottyminder.common.logger.LogWrapper;
import com.tynercontracting.pottyminder.common.logger.MessageOnlyLogFilter;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;

    public static final String TAG = "MainActivity";

    public static final String FRAGTAG = "RepeatingAlarmFragment";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        if (getSupportFragmentManager().findFragmentByTag(FRAGTAG) == null) {
/**
*            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
*            RepeatingAlarmFragment fragment = new RepeatingAlarmFragment();
*            transaction.add(fragment, FRAGTAG);
*            transaction.commit();
*/
            Log.i(TAG, "would have created alarm fragment");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "The Alarm Has Been Turned ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // BEGIN_INCLUDE (intent_fired_by_alarm)
                // First create an intent for the alarm to activate.
                // This code simply starts an Activity, or brings it to the front if it has already
                // been created.
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                // END_INCLUDE (intent_fired_by_alarm)

                // BEGIN_INCLUDE (pending_intent_for_alarm)
                // Because the intent must be fired by a system service from outside the application,
                // it's necessary to wrap it in a PendingIntent.  Providing a different process with
                // a PendingIntent gives that other process permission to fire the intent that this
                // application has created.
                // Also, this code creates a PendingIntent to start an Activity.  To create a
                // BroadcastIntent instead, simply call getBroadcast instead of getIntent.
                PendingIntent pendingIntent = PendingIntent.getActivity(getParent(), REQUEST_CODE,
                        intent, 0);

                // END_INCLUDE (pending_intent_for_alarm)

                // BEGIN_INCLUDE (configure_alarm_manager)
                // There are two clock types for alarms, ELAPSED_REALTIME and RTC.
                // ELAPSED_REALTIME uses time since system boot as a reference, and RTC uses UTC (wall
                // clock) time.  This means ELAPSED_REALTIME is suited to setting an alarm according to
                // passage of time (every 15 seconds, 15 minutes, etc), since it isn't affected by
                // timezone/locale.  RTC is better suited for alarms that should be dependant on current
                // locale.

                // Both types have a WAKEUP version, which says to wake up the device if the screen is
                // off.  This is useful for situations such as alarm clocks.  Abuse of this flag is an
                // efficient way to skyrocket the uninstall rate of an application, so use with care.
                // For most situations, ELAPSED_REALTIME will suffice.
                int alarmType = AlarmManager.ELAPSED_REALTIME;
                final int SIXTY_SEC_MILLIS = 60000;
                final int FIFTEEN_SEC_MILLIS = 15000;


                // The AlarmManager, like most system services, isn't created by application code, but
                // requested from the system.
                AlarmManager alarmManager = (AlarmManager)
                        getParent().getSystemService(Context.ALARM_SERVICE);

                // setRepeating takes a start delay and period between alarms as arguments.
                // The below code fires after 15 seconds, and repeats every 15 seconds.  This is very
                // useful for demonstration purposes, but horrendous for production.  Don't be that dev.
                alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
                        SIXTY_SEC_MILLIS, pendingIntent);
                // END_INCLUDE (configure_alarm_manager);
                Log.i("RepeatingAlarmFragment", "Alarm set.");


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeLogging();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.do_location:
                Toast.makeText(this, "You have chosen the " + getResources().getString(R.string.do_location) + " menu option",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.do_mute:
                Toast.makeText(this, "You have chosen the " + getResources().getString(R.string.do_mute) + " menu option",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.do_log:
                Toast.makeText(this, "You have chosen the " + getResources().getString(R.string.do_log) + " menu option",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.do_settings:
                Toast.makeText(this, "You have chosen the " + getResources().getString(R.string.do_settings) + " menu option",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.do_about:
                Toast.makeText(this, "You have chosen the " + getResources().getString(R.string.do_about) + " menu option",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.do_quit:
                Toast.makeText(this, "You have chosen the " + getResources().getString(R.string.do_quit) + " menu option",
                        Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void listRingtones() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            // Do something with the title and the URI of ringtone
        }
    }

    /**
     * Create a chain of targets that will receive log data
     */
    @TargetApi(21)
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());
        /**if (Build.VERSION.SDK_INT < 23) {

            logFragment.getLogView().setTextAppearance(this, R.style.Log);
        } else {
            logFragment.getLogView().setTextAppearance(R.style.Log);
        }
         */

        logFragment.getLogView().setTextAppearance(R.style.Log);
        logFragment.getLogView().setBackgroundColor(Color.WHITE);


        Log.i(TAG, "Ready");
    }


    @Override
    public void onStop() {
        super.onStop();

    }

}
