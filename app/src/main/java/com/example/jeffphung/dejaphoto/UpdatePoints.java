package com.example.jeffphung.dejaphoto;

import android.location.Location;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kaijiecai on 6/5/17.
 */

public class UpdatePoints {

    static GregorianCalendar currentCalendar;
    static ILocation currentLocation;
    private final static int KARMA_POINTS = 10;
    private final static int LOCATION_POINTS = 10;
    private final static int TIME_POINTS = 10;
    private final static int DAY_POINTS = 10;
    private final static int WITHINTIME = 2*3600; // within 2 hours
    private final static double WITHINRANGE = 304.8; // within 1000 feet/304.8 meters

    static DejaVuMode dejaVuMode = DejaVuMode.getDejaVuModeInstance();

    public static void updatePhotoPoint(Photo photo){


        photo.setPoints(0);
        GregorianCalendar calendar = photo.getCalendar(); //photo's calendar
        ILocation iloc = new LocationAdapter(photo.getLocation()); //photo's location

        //check day of week
        if (calendar != null && currentCalendar != null && dejaVuMode.isDayModeOn()) {
            if (sameDayOfWeek(currentCalendar, calendar)) {
                photo.addPoints(DAY_POINTS);
                Log.i("Same Day of Week: ", calendar.get(calendar.DAY_OF_WEEK) + "");
            }
        }

        //check within two hours
        if (calendar != null && currentCalendar != null && dejaVuMode.isTimeModeOn()) {
            if (withinHours(currentCalendar, calendar)) {
                photo.addPoints(TIME_POINTS);
                Log.i("Hour of Day: ", calendar.get(calendar.HOUR_OF_DAY) + "");
            }
        }

        //check withinLocation
        if (iloc.getLoc() != null && currentLocation.getLoc() != null && dejaVuMode.isLocationModeOn()) {
            if (isLocationClose(currentLocation, iloc)) {
                photo.addPoints(LOCATION_POINTS);
                Log.i("Within Location: ", iloc.getLoc() + "");

            }


        }
        //check Karma
        if (photo.getKarma()) {
            photo.addPoints(KARMA_POINTS);
            Log.i("Karma Pressed: ", "true");
        }

    }


    public static void setCurrentLocation(Location l){
        currentLocation = new LocationAdapter(l);
    }

    public static void setCurrentCalendar(GregorianCalendar c){
        currentCalendar = c;
    }


    /* check if two location are close */
    /*public static boolean isLocationClose(ILocation currentLocation, ILocation location){
        if(currentLocation.distanceTo(location) <= WITHINRANGE) {
            return true;
        }
        return false;
    }*/
    public static boolean isLocationClose(ILocation currentLocation, ILocation location){
        if(currentLocation.distanceTo(location) <= WITHINRANGE) {
            return true;
        }
        return false;
    }

    /* check if same day of week */
    public static boolean sameDayOfWeek(GregorianCalendar currentCalendar, GregorianCalendar calendar){
        Log.i("current day of week: ",""+currentCalendar.get(Calendar.DAY_OF_WEEK));
        Log.i("photo's day of week: ",""+calendar.get(Calendar.DAY_OF_WEEK));
        System.out.print(""+currentCalendar.get(Calendar.DAY_OF_WEEK));
        if(currentCalendar.get(Calendar.DAY_OF_WEEK)==
                (calendar.get(Calendar.DAY_OF_WEEK))) {
            return true;
        }

        return false;
    }


    /* convert calendar time to second */
    public static int calendarToSecond(GregorianCalendar calendar){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return hour*3600+minute*60+second;
    }

    /* check if two calendar time are close */
    public static boolean withinHours(GregorianCalendar currentCalendar,GregorianCalendar calendar){
        Log.i("sorter current UTC time", currentCalendar.get(Calendar.HOUR_OF_DAY)+"");
        Log.i("sorter photo's UTC time", calendar.get(Calendar.HOUR_OF_DAY)+"");
        int currentTime = calendarToSecond(currentCalendar);
        int calendarTime = calendarToSecond(calendar);
        if(Math.abs(currentTime - calendarTime) <= WITHINTIME){
            return true;
        }
        return false;
    }
}
