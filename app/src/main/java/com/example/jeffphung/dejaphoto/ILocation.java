package com.example.jeffphung.dejaphoto;

import android.location.Location;

/**
 * Created by romel on 6/8/2017.
 */

public interface ILocation {
    float distanceTo(ILocation iloc);
    double getLatitude();
    double getLongitude();
    Location getLoc();
}
