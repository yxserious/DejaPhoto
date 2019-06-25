package com.example.jeffphung.dejaphoto;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by kaijiecai on 6/2/17.
 */

public class CreateDirs {

    static String mediaStorePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();


    static String[] dirs = {"DejaPhoto","DejaPhotoCopied","DejaPhotoFriends"};

    public CreateDirs(){

    }
    public static void createDir(){
        for(int i = 0; i < dirs.length; i++){
            File newDir = new File(mediaStorePath+"/"+dirs[i]);
            if(!newDir.exists()) {
                newDir.mkdirs();
            }
            else{
                Log.i("Directory exists",newDir+"");
            }
        }


    }
}
