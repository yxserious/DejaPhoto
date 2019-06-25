package com.example.jeffphung.dejaphoto;

import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by huang on 5/12/2017.
 */

public class MyWallPaperManager {
    Context mContext;
    WallpaperManager myWallPaperManager;
    int textSize = 42;
    Intent intent;
    PhotoList photoList;

    public MyWallPaperManager(Context mContext){
        myWallPaperManager = WallpaperManager.getInstance(mContext);
        this.mContext = mContext;

    }


    public void setWallPaper(Photo p){
        photoList = PhotoListManager.getPhotoListManagerInstance().getMainPhotoList();

        Log.i("-----",photoList.size()+"");
        if (photoList.size() == 0) {
            setDefaultWallpaper();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.new_app_widget);
            ComponentName thisWidget = new ComponentName(mContext, NewAppWidget.class);
            remoteViews.setImageViewResource(R.id.buttonKarma, R.drawable.karma_greyed);
            appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            return;
        }

        if (p != null) {
            String path = p.getImgPath();
            if (path != null) {
                // put behavior here:
                Log.i("myWallPaperManager", "start");
                Log.i("ImagePath", p.getImgPath());
                try {

                    if (path == null) {
                        Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                    } else {

                        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                        Display display = wm.getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int phoneWidth = size.x;
                        int phoneHeight = size.y;
                        Log.i("start set", "start");
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        Log.i("BITMAP", bitmap + "");
                        bitmap = Bitmap.createScaledBitmap(bitmap, phoneWidth, phoneHeight,true);
                        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                        Canvas c = new Canvas(bitmap);
                        Paint textPaint = new Paint();
                        textPaint.setTextSize(textSize);
                        textPaint.setColor(Color.WHITE);

                        // bitmap for the location name
                        if (p.getLocationName() != null)
                        {
                            Log.i("location name ", p.getLocationName());
                            c.drawText(p.getLocationName(), bitmap.getWidth() / 2 - phoneWidth / 2 + 30,
                                    bitmap.getHeight() / 2 + phoneHeight / 2 - 80, textPaint);
                        }

                        // bitmap for the number of Karmar

                        /*
                        else if(p.getKarmarNumber() != null)

                        Log.i("Karmar:  ", p.getKarmarNumber());
                        */

                            c.drawText("Karma:" + p.getNumKarma() , bitmap.getWidth()/2 + 80,
                            bitmap.getHeight() / 2 + phoneHeight / 2 -80, textPaint);


                        myWallPaperManager.setBitmap(bitmap);

                        bitmap.recycle();
                        //new stuff
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.new_app_widget);
                        ComponentName thisWidget = new ComponentName(mContext, NewAppWidget.class);
                        if (p.getKarma()) {
                            remoteViews.setImageViewResource(R.id.buttonKarma, R.drawable.karma_colored);
                        }
                        else {
                            remoteViews.setImageViewResource(R.id.buttonKarma, R.drawable.karma_greyed);
                        }
                        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
                        //new stuff
                        setTimer();


                        Log.i("finish set img", "finished");
                    }
                    //Toast.makeText(mContext, "Wallpaper set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();


                    // test
                }
            }
        }
        /*else if (PhotoList.getPhotoListInstance().size() == 0) {
            setDefaultWallpaper();
        }*/



    }

    public void setDefaultWallpaper(){

        Log.i("start set", "start");
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.defaultwhatever);
            myWallPaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void setTimer(){
        intent = new Intent(mContext, AutoChangeWallPaper.class);
        intent.putExtra("waitTimeInt", -1);
        mContext.startService(intent);
    }
}
