package com.example.jeffphung.dejaphoto;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import static android.R.attr.id;

/**
 * This class will receive the alarm call from MyAlarmManager every hour
 * and call sorter to sort all photos
 */


public class MyAlarmReceiver extends BroadcastReceiver {
    Location location;

    @Override
    public void onReceive(Context context, Intent intent) {



        ComponentName receiver = new ComponentName(context, MyAlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Log.i(id+"", "=====start sort list");
        //call sorter
        PhotoSorterTask photoSorterTask = new PhotoSorterTask(getCurrentLocation(context), context);
        photoSorterTask.execute();


    }

    public Location getCurrentLocation(Context mContext) {
        LocationManager locationManager =
                (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        if(locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
        return location;
    }
}
