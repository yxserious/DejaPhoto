package com.example.jeffphung.dejaphoto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static com.example.jeffphung.dejaphoto.ExifDataParser.createNewPhoto;

/**
 * Created by kaijiecai on 4/29/17.
 */

/* this class will load photo from default camera album */
public class PhotoLoaderTask extends AsyncTask<Void,String,String> {





    String mediaStorePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

    final String dejaPhoto = "DejaPhoto";
    final String dejaPhotoCopied = "DejaPhotoCopied";
    final String dejaPhotoFriend = "DejaPhotoFriends";
    final String dejaPhotoDirPath = mediaStorePath+"/"+dejaPhoto;
    final String dejaPhotoCopiedDirPath = mediaStorePath+"/"+dejaPhotoCopied;
    final String dejaPhotoFriendDirPath = mediaStorePath+"/"+dejaPhotoFriend;



    Context mContext;
    ProgressDialog progressDialog;



    public PhotoLoaderTask(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(mContext,
                "ProgressDialog",
                "Waiting for loading");


    }



    @Override
    protected String doInBackground(Void... params) {
        Log.i("start loading","start loading-----------");

        //gets path to camera album photos
        File cameraDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());

        //there are multiple folders here, but we are only interested in the first one (ie Camera)
        File[] files = cameraDir.listFiles();

        PhotoList photoList;
        File dir;
        File[] imgs;
        for(int n = 0; n < files.length; n++){
            if(files[n].toString().equals(dejaPhotoDirPath)){
                photoList = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhoto);

            }
            else if(files[n].toString().equals(dejaPhotoCopiedDirPath)){
                photoList = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhotoCopied);
            }
            else if(files[n].toString().equals(dejaPhotoFriendDirPath)){
                photoList = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhotoFriend);

            }
            else{
                continue;
            }
            dir = files[n];
            imgs = dir.listFiles();

            for (int i = 0; i < imgs.length; i++) {

                Log.i("start loading", "load " + i + "th photo");

                String path = imgs[i].toString();
                if (!path.toLowerCase().substring(path.length() - 4).equals(".jpg")) {
                    continue;
                }

                ExifDataParser.setContext(mContext);
                Photo photo = createNewPhoto(path);
                photoList.add(photo);

                Log.i("end loading", "ends loading " + i + "th photo----");
            }
        }

      //  Toast.makeText(mContext,list.size()+"",Toast.LENGTH_SHORT).show();
        Log.i("end loading photos","end loading photos");
        return null;
    }



    @Override
    public void onPostExecute (String result){
        progressDialog.dismiss();
        PhotoListManager.updateMainPhotolist();
        //invoke autoGPStimer to sort list every 500ft change
        Intent intent = new Intent(mContext,AutoGPSTimer.class);
        mContext.startService(intent);

        //invoke AlarmManager to sort list every hour
        Intent alarmIntent = new Intent(mContext, MyAlarmManager.class);
        mContext.startService(alarmIntent);

        Intent wallPaperIntent = new Intent(mContext, AutoChangeWallPaper.class);
        mContext.startService(wallPaperIntent);

    }


}
