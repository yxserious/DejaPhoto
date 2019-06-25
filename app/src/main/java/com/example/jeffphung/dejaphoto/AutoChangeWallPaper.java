package com.example.jeffphung.dejaphoto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by kaijiecai on 5/13/17.
 */

public class AutoChangeWallPaper extends Service {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private long INTERVAL = 1000*300;
    private int id = 1;
    private String alarmName = "Auto change photo alarm";
    private int waitTimeInt = -1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){

        if (intent != null) {
            waitTimeInt = intent.getIntExtra("waitTimeInt", -1);
        }
        if (waitTimeInt != -1) {
            INTERVAL = 1000 * waitTimeInt;
        }
        Log.i(alarmName, "start "+alarmName);
        //Toast.makeText(this, alarmName + "running in the background", Toast.LENGTH_SHORT).show();

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);


        Intent mIntent = new Intent(this,AutoWallPaperReceiver.class);

        alarmIntent = PendingIntent.getBroadcast(this, id, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.i(alarmName, alarmName+ " set timer");

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+INTERVAL, alarmIntent);

        Log.i(alarmName, alarmName+" ends setting timer");

        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
