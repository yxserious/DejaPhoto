package com.example.jeffphung.dejaphoto;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * This class will update the location change every 5 secs, and for every 500 ft change
 * it will call PhotoSoter to sort the photos
 */

public class AutoGPSTimer extends Service implements LocationListener {

    Location location;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    final long TIMER = 1000*5;  //1000*100 milliseconds = 10 seconds
    final float LOCATIONCHANGE = 152.4f; // 500 ft/152.4 meters


    @Override
    public void onCreate(){
        Toast.makeText(this, "GPS running gps in the background", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    }


    @Override
    public void onDestroy(){
        Toast.makeText(this, "GPS background gps stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return START_NOT_STICKY;
        }
        // check if network provider available
        else if(locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    TIMER, LOCATIONCHANGE, this);
            if(locationManager!= null){
                //update location
                location= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        // check if gps provider available
        else if(locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    TIMER, LOCATIONCHANGE, this);

            if(locationManager!= null){
                //update location
                location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            }
        }


        return START_NOT_STICKY;

    }


    // call photoSorter to sort list
    private void callPhotoSorter() {
        PhotoSorterTask photoSorterTask = new PhotoSorterTask(location, AutoGPSTimer.this);
        photoSorterTask.execute();

    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            this.location = location;
            callPhotoSorter();
        }
        Log.i("location change","on location changed");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
