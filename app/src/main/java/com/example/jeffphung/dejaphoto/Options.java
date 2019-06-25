package com.example.jeffphung.dejaphoto;

/**
 * Created by kaijiecai on 6/7/17.
 */

public class Options {

    static Options optionsInstance = new Options();
    private static boolean shareMyPhotos = false;
    private static boolean showFriendPhotos = false;
    private static boolean showMyPhotos = false;

    public Options(){

    }

    public static Options getOptionsInstance(){
        return optionsInstance;
    }


    public static boolean isShareMyPhotos() {
        return shareMyPhotos;
    }

    public static void setShareMyPhotos(boolean shareMyPhotos) {
        Options.shareMyPhotos = shareMyPhotos;
    }

    public static boolean isShowFriendPhotos() {
        return showFriendPhotos;
    }

    public static void setShowFriendPhotos(boolean showFriendPhotos) {
        Options.showFriendPhotos = showFriendPhotos;
    }

    public static boolean isShowMyPhotos() {
        return showMyPhotos;
    }

    public static void setShowMyPhotos(boolean showMyPhotos)
    {
        Options.showMyPhotos = showMyPhotos;
    }

}
