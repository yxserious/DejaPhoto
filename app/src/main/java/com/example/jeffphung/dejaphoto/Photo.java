package com.example.jeffphung.dejaphoto;

import android.location.Location;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Created by kaijiecai on 4/29/17.
 */

public class Photo implements Comparable<Photo> {


    final private String TAG_KARMA = ExifInterface.TAG_USER_COMMENT;
    final private String TAG_RELEASED = ExifInterface.TAG_IMAGE_DESCRIPTION;
    private String imgPath;
    private GregorianCalendar calendar;
    private String locationName;
    private Location location;
    private Boolean karma = false;
    private Boolean released = false;
    private int points = 0;
    private Integer width;
    private Integer height;
    private int numKarma = 0;

    private String parent = "user";



    private String user = "";


    /* this is a constructor for test purpose, use this constructor to declare a new Photo */
    public Photo(String imgPath){
        this.imgPath = imgPath;
    }

    public Photo(
            String imgPath,
            Integer width,
            Integer height,
            GregorianCalendar calendar,
            Location location,
            String locationName,
            Boolean karma){

        this.imgPath = imgPath;
        this.width = width;
        this.height = height;
        this.calendar = calendar;
        this.location = location;
        this.locationName = locationName;
        Log.i(imgPath,locationName+"");
        this.karma = karma;
    }



    /*
     * add points to photo
     */
    public void addPoints(int points){
        this.points +=points;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getKarma() {
        return karma;
    }

    /*
     * set Karma to be true or false, and write it to the photo
     */
    public void setKarma(Boolean karma) {
        if(karma) {
            this.karma = karma;
            try {



                ExifInterface exifInterface = new ExifInterface(imgPath);

                String photoInfo = StringParser.encodeString(karma, numKarma, locationName);
                exifInterface.setAttribute(TAG_KARMA, photoInfo);
                exifInterface.saveAttributes();

                Log.i("write karma to photo", "successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            this.karma = false;
        }
    }

    public Boolean isReleased() {
        return released;
    }


    /*
     * remove the photo from list
     * and mark it as removed in the photo
     */
    public void setReleased(Boolean released) {
        this.released = released;
        try {
            ExifInterface exifInterface = new ExifInterface(imgPath);
            exifInterface.setAttribute(TAG_RELEASED,released+"");//// TODO: 5/13/17  
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * return the points of photo
     */
    public int getPoints() {
        return points;
    }

    /*
     * set points
     */
    public void setPoints(int points) {
        this.points = points;
    }


    /*
     * return calendar of photo
     */
    public GregorianCalendar getCalendar() {
        return calendar;
    }

    /*
     * return image path in photo
     */
    public String getImgPath() { return imgPath;}



    /*
     * compare two photos
     * return 1 if the photo has higher points
     * return -1 if the photo has lower points
     * if two photos have same points
     *   return 1 if photo was taken recent
     * if two photos have same points and taken at the same time, then 0 will be returned
     */
    @Override
    public int compareTo(Photo o) {
        //compare points
        if(getPoints() > o.getPoints()){
            return 1;
        }
        else if(getPoints() < o.getPoints())
            return -1;

        //compare taken time
        else if (getCalendar() != null && o.getCalendar() != null) {
            if (getCalendar().compareTo(o.getCalendar())>0) {
                return 1;
            }
            else if(getCalendar().compareTo(o.getCalendar())<0){
                return -1;

            }
        }

        //if photo has no time info, then return -1
        else if( getCalendar() == null){
            return -1;
        }
        else if( o.getCalendar() == null){
            return 1;
        }

        // return 0 if photos have same points and taken at same time
        return 0;
    }

    public Location getLocation() {
        return location;
    }


    /* return a city name string, it will return null if no such information */
    public String getLocationName() {
        Log.i("location name: ",locationName+"");
        return locationName;
    }

    public void setLocationName(String strLocation){
        locationName = strLocation;
    }

    public Integer getWidth(){
        return width;
    }

    public Integer getHeight(){
        return  height;
    }

    public int getNumKarma(){
        return numKarma;
    }

    public void setNumKarma(int n){
        numKarma = n;
    }

    public void incrementKarma(){
        numKarma++;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
