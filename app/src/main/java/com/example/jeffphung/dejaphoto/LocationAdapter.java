package com.example.jeffphung.dejaphoto;

import android.location.Location;

/**
 * Created by romel on 6/8/2017.
 */

public class LocationAdapter implements ILocation {

    private Location loc;

    public LocationAdapter(Location l) {
        loc = l;
    }

    @Override
    public float distanceTo(ILocation loc2) {
        Location l = new Location("");
        l.setLatitude(loc2.getLatitude());
        l.setLongitude(loc2.getLongitude());
        return loc.distanceTo(l);

    }
    public double getLatitude() {
        return loc.getLatitude();
    }
    public double getLongitude() {
        return loc.getLongitude();
    }
    public Location getLoc() {
        return loc;
    }
}
