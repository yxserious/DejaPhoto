package com.example.jeffphung.dejaphoto;

import android.location.Location;

/**
 * Created by romel on 6/8/2017.
 */

public class MockLocation implements ILocation {

    private double latitude;
    private double longitude;

    @Override
    public float distanceTo(ILocation loc2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(loc2.getLatitude()-latitude);
        double dLng = Math.toRadians(loc2.getLongitude()-longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(longitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLatitude(double l) {
        latitude = l;
    }
    public void setLongitude(double l) {
        longitude = l;
    }
    public Location getLoc() {return null;}

}
