package com.example.jeffphung.dejaphoto;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by kaijiecai on 4/30/17.
 */

public class PhotoSorterTask extends AsyncTask<Void,String,String>{

    PhotoList photoList;

    GregorianCalendar currentCalendar;
    DejaVuMode dejaVuMode;
    Location currentLocation;
    Context mContext;


    /* photoSorterTask constructor, pass current dejaVuMode as parmater */
    public PhotoSorterTask(Context context){
        mContext = context;



    }

    public PhotoSorterTask(){

    }


    public PhotoSorterTask(Location currentLocation, Context context){
        mContext = context;
        this.currentLocation = currentLocation;
        Log.i("current Location",currentLocation+"");
    }

    @Override
    protected void onPreExecute(){
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();
        currentCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        photoList= PhotoListManager.getPhotoListManagerInstance().getMainPhotoList();

    }

    @Override
    public void onPostExecute (String result){

    }


    @Override
    protected String doInBackground(Void...params) {
        Log.i("photo sorter","--------------");
        Log.i("photo sorter", "sorter invoked");
            if (dejaVuMode.isDejaVuModeOn()) {
                UpdatePoints.setCurrentLocation(currentLocation);
                UpdatePoints.setCurrentCalendar(currentCalendar);

                for (int i = 0; i < photoList.size(); i++) {
                    Photo photo = photoList.getPhoto(i);
                    Log.i("Path", photo.getPoints() + "" + photo.getImgPath());
                /* check if photo is null or if the photo is released by user */
                    if (photo != null && !photo.isReleased()) {
                        UpdatePoints.updatePhotoPoint(photo);
                    }
                    Log.i("Path", photo.getPoints() + "" + photo.getImgPath());
                }

        }


        Log.i("photo sorter ends","--------------");
        //sort the list according to points
        photoList.sort();
        photoList.setIndex(0);
        // set the first photo in the list as background
        MyWallPaperManager myWallPaperManager = new MyWallPaperManager(mContext);
        myWallPaperManager.setWallPaper(photoList.getPhoto(0));

        return "";
    }

}
