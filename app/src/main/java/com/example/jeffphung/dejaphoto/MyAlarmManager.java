package com.example.jeffphung.dejaphoto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * This class will set a alarm which will invoke receiver every hour
 */

public class MyAlarmManager extends Service {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private long INTERVAL = AlarmManager.INTERVAL_HOUR;
    private int id = 0;
    private String alarmName = "alarm to sort photos";
    public MyAlarmManager(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){

        Log.i(alarmName, "start "+alarmName);
        Toast.makeText(this, alarmName + "running in the background", Toast.LENGTH_SHORT).show();
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(this, MyAlarmReceiver.class);

        alarmIntent = PendingIntent.getBroadcast(this, id, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.i(alarmName, alarmName+ " set timer");
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                INTERVAL, alarmIntent);
        Log.i(alarmName, alarmName+" ends setting timer");

        return START_STICKY;
    }


    
    @Override public IBinder onBind(Intent intent) {
        return null;
    }




}
