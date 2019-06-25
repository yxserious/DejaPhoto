package com.example.jeffphung.dejaphoto;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jeffphung on 6/8/17.
 */

public class FirebaseDatabaseService extends Service{

    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    public void onCreate(){
        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:233552778796:android:30604417558d9a73")
                .setDatabaseUrl("https://dejaphoto-cce7b.firebaseio.com/")
                .build();


        database = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(this,options,"secondary"));
        myRef = database.getReferenceFromUrl("https://dejaphoto-cce7b.firebaseio.com/");




        return START_NOT_STICKY;
    }


    public void send(){

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
