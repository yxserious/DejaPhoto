package com.example.jeffphung.dejaphoto;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * This class will receive the message from AutoChamgeWallpaper, and will call
 * MyWallPaperManager to change the background
 */

public class AutoWallPaperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        ComponentName receiver = new ComponentName(context, AutoWallPaperReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Log.i("------------","------------");
        //call to change the wallPaper.
        MyWallPaperManager myWallPaperManager = new MyWallPaperManager(context);
        myWallPaperManager.setWallPaper(PhotoListManager.getPhotoListManagerInstance().getMainPhotoList().next());
    }
}
