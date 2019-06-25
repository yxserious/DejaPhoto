package com.example.jeffphung.dejaphoto;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    PhotoList photoList; // PhotoList contains all photo in the app
    PhotoList dejaPhotoList;
    PhotoList dejaPhotoListCopied;
    PhotoList dejaPhotoListFriend;
    DejaVuMode dejaVuMode; // DejaVumode class
    PhotoLoaderTask photoLoader; // PhotoLoader class: load photo to app from photo
    // PhotoSorter class: sort the photo according to location and time

    FirebaseDatabase database;
    DatabaseReference myRef;

    EditText waitTimeText;
    String waitTimeStr = "";
    int waitTimeInt = -1;
    Intent intent;

    boolean allowed = false;
    final int photoPickerID = 1;
    final int takePhotoID = 2;
    final int changeLocationID = 3;
    final String dejaPhoto = "DejaPhoto";
    final String dejaPhotoCopied = "DejaPhotoCopied";
    final String dejaPhotoFriend = "DejaPhotoFriends";

    File image;

    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

    private List<String> emailList;
    private String email;

    private static String newLocName;

    ShareManager sharer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /* set up realtime database */



        //myRef = database.getReferenceFromUrl("https://dejaphoto-cce7b.firebaseio.com/");


        /* set up realtime database */



        sharer = new ShareManager(this);
        emailList = new ArrayList<>();
        email = "";

        Button waitTimeButton = (Button) findViewById(R.id.waitTimeButton);
        waitTimeText = (EditText) findViewById(R.id.waitTimeEditText);
        intent = new Intent(this, AutoChangeWallPaper.class);
        waitTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                waitTimeInt = -1;
                waitTimeStr = waitTimeText.getText().toString();
                boolean isNum = true;
                for (int i = 0; i < waitTimeStr.length(); i++) {
                    if (!Character.isDigit(waitTimeStr.charAt(i))) {
                        i = waitTimeStr.length();
                        isNum = false;
                    }
                }
                if (isNum) {
                    waitTimeInt = Integer.parseInt(waitTimeStr);
                }
                intent.putExtra("waitTimeInt", waitTimeInt);
                Toast.makeText(v.getContext(), "Set wait time to " + waitTimeInt + " seconds", Toast.LENGTH_SHORT).show();
                startService(intent);

            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestServerAuthCode("233552778796-7fm2m9cd3h8cjforhuo8p8q0v5sse786.apps.googleusercontent.com")
                .requestScopes(new Scope(PeopleScopes.CONTACTS), new Scope(PeopleScopes.USERINFO_EMAIL))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //TODO
                    }
                }/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
                Toast.makeText(v.getContext(), "YOU HAVE NOW SIGNED IN", Toast.LENGTH_SHORT).show();

            }
        });


        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File wallpaperDirectory = new File(path.toString() + "/adddd");
        wallpaperDirectory.mkdirs();

        PhotoListManager photoListManager = PhotoListManager.getPhotoListManagerInstance();
        photoListManager.setMainAcitivtyRef(this);

        /* initialization */
        photoList = new PhotoList("main");
        // add three photLists into photolistManager
        dejaPhotoList = new PhotoList(dejaPhoto);
        dejaPhotoListCopied = new PhotoList(dejaPhotoCopied);
        dejaPhotoListFriend = new PhotoList(dejaPhotoFriend);
        PhotoListManager.getPhotoListManagerInstance().addPhotoList(dejaPhotoList);
        PhotoListManager.getPhotoListManagerInstance().addPhotoList(dejaPhotoListCopied);
        PhotoListManager.getPhotoListManagerInstance().addPhotoList(dejaPhotoListFriend);

        PhotoListManager.getPhotoListManagerInstance().setMainPhotoList(photoList);

        dejaVuMode = DejaVuMode.getDejaVuModeInstance();

        photoList.setContext(this);
        photoLoader = new PhotoLoaderTask(MainActivity.this);
        photoLoader.execute();

          /* toggleButtons */
        ToggleButton dButton = (ToggleButton) findViewById(R.id.sharebtn);
        dButton.setOnCheckedChangeListener(this);
        ToggleButton lButton = (ToggleButton) findViewById(R.id.friendbtn);
        lButton.setOnCheckedChangeListener(this);
        ToggleButton timeDayButton = (ToggleButton) findViewById(R.id.mebtn);
        timeDayButton.setOnCheckedChangeListener(this);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:233552778796:android:30604417558d9a73")
                .setDatabaseUrl("https://dejaphoto-cce7b.firebaseio.com/")
                .build();


        database = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(this,options,"AAA"));
        myRef = database.getReference();


        CreateDirs.createDir();


    }


    public void pickerClicked(View v) {


        Intent newIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(newIntent, photoPickerID);

    }
    public void changeLocationClicked(View v) {

        Intent newIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(newIntent, changeLocationID);

    }
    public void takePhotoClicked(View v) throws IOException {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File[] DCIMFiles = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).listFiles();
            int i = 0;
            for (i = 0; i < DCIMFiles.length; i++) {
                System.out.println("----------"+DCIMFiles[i].toString()+"");
                System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+dejaPhoto);
                if (DCIMFiles[i].toString().equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+dejaPhoto)){
                    break;
                }
            }
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).listFiles()[i];
            System.out.println("----------"+storageDir+"");
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents

            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",image);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            startActivityForResult(takePictureIntent, takePhotoID);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == photoPickerID){
            copyImages(data,dejaPhotoCopied);
        }
        else if(resultCode == RESULT_OK && requestCode == takePhotoID){
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            final Uri contentUri = Uri.fromFile(image);
            scanIntent.setData(contentUri);
            sendBroadcast(scanIntent);


            PhotoList photoList = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhoto);
            addToPhotoList(photoList,image.toString());
        }
        else if(resultCode ==RESULT_OK && requestCode == changeLocationID){

            //final EditText locEditText = (EditText) findViewById(R.id.editLocation);
            final AlertDialog.Builder locAlertDialog = new AlertDialog.Builder(this);
            locAlertDialog.setTitle("Change Photo Location");
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            final EditText locEditText = new EditText(this);
            locEditText.setLayoutParams(lp);
            locAlertDialog.setView(locEditText);
            locAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    newLocName = locEditText.getText().toString();

                    Uri pickedImage = data.getData();
                    //String imagePath = getRealPathFromURI(getActivity(),uri);
                    // Let's read picked image path using content resolver


                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    setLocationName(imagePath,newLocName);

                }
            });
            locAlertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //do nothing
                }
            });

            locAlertDialog.show();


        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void setLocationName(String imagePath, String newLocName){
        PhotoList p = PhotoListManager.getPhotoListManagerInstance().getMainPhotoList();
        for(int i = 0; i < p.size(); i++){
            if(p.getPhoto(i).getImgPath().equals(imagePath)){
                p.getPhoto(i).setLocationName(newLocName);
            }
        }
    }


    public void copyImages(Intent data, String album){

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() +
                "/"+album+"/" + "copied" + Calendar.getInstance().getTimeInMillis() + ".jpg");
        String path = file.toString();

        FileChannel source = null;
        FileChannel destination = null;

        Uri pickedImage = data.getData();
        //String imagePath = getRealPathFromURI(getActivity(),uri);
        // Let's read picked image path using content resolver


        String[] filePath = {MediaStore.Images.Media.DATA};

        Log.i("---------", filePath[0] + "");

        Log.i("---------",filePath[0]+"--"+pickedImage);

        Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        try {
            Log.i("-------",imagePath+"");
            source = new FileInputStream(new File(imagePath)).getChannel();
            destination = new FileOutputStream(file).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (destination != null && source != null) {
            try {
                destination.transferFrom(source, 0, source.size());
                final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                final Uri contentUri = Uri.fromFile(file);
                scanIntent.setData(contentUri);
                sendBroadcast(scanIntent);
                PhotoList p = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhotoCopied);
                addToPhotoList(p, path);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        if (source != null) {
            try {
                source.close();
                destination.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void addToPhotoList(PhotoList p, String path){
        Photo photo = ExifDataParser.createNewPhoto(path);
        UpdatePoints.updatePhotoPoint(photo);
        p.add(photo);
        PhotoListManager.getPhotoListManagerInstance().getMainPhotoList().add(photo);
        PhotoListManager.getPhotoListManagerInstance().getMainPhotoList().sort();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(MainActivity.this, photoList.size() + "", Toast.LENGTH_SHORT).show();

       // ShareManager sharer = new ShareManager();

        switch (buttonView.getId()) {
            case R.id.sharebtn:
                if (isChecked) {
                    // The toggle is enabled
                    if( allowed) {
                        int num = sharer.share(emailList);
                        sendToDatabase(num);
                    }

                } else {
                    // The toggle is disabled
                    if( allowed) {
                        sharer.unshare(emailList);
                        clearDataBase();
                    }
                }
                Options.setShareMyPhotos(isChecked);

                Log.i("Checking mysharePhotos", Options.isShareMyPhotos()+"");
                PhotoListManager.updateMainPhotolist();
                break;
            case R.id.friendbtn:
                if (isChecked) {
                    //sharer.friendCopy(emailList,"all",);

                } else {
                    // The toggle is disabled
                }
                Options.setShowFriendPhotos(isChecked);
                Log.i("Checking FriendPhoto", Options.isShowFriendPhotos()+"");
                PhotoListManager.updateMainPhotolist();
                break;
            case R.id.mebtn:
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
                Options.setShowMyPhotos(isChecked);
                Log.i("Checking myphoto", Options.isShowMyPhotos()+"");
                PhotoListManager.updateMainPhotolist();
                break;


        }

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("signintag", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String name = acct.getDisplayName();
            email = acct.getEmail();

            System.out.println("ahhh!" + email);

            // This is what we need to exchange with the server.
            String authcode = acct.getServerAuthCode();

            new PeoplesAsync().execute(acct.getServerAuthCode());

            System.out.println("the name is: " + name);


            updateUI(true);
        } else {

            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.signin).setVisibility(View.GONE);
        } else {
            findViewById(R.id.signin).setVisibility(View.VISIBLE);
        }
    }
    class PeoplesAsync extends AsyncTask<String, Void, List<String>> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... params) {

            List<String> nameList = new ArrayList<>();

            try {
                People peopleService = PeopleHelper.setUp(MainActivity.this, params[0]);

                ListConnectionsResponse response = peopleService.people().connections()
                        .list("people/me")
                        .setRequestMaskIncludeField("person.emailAddresses")
                        .execute();
                List<Person> connections = response.getConnections();

                Log.i("-------connections",connections.size()+"");

                //email = email.replace(".", "");
                emailList.add(email);
                int i = 0;

                for (Person person : connections) {

                    Log.i("-----person",i+"   "+person+"");
                    if (!person.isEmpty()) {

                        List<EmailAddress> emailAddresses = person.getEmailAddresses();

                        Log.i("-----email",emailAddresses+"");
                        if (emailAddresses != null){
                            //for (EmailAddress emailAddress : emailAddresses) {
                                //emailAddresses.get(0).setValue(emailAddresses.get(0).getValue().replace(".", ""));
                                emailList.add(emailAddresses.get(0).getValue());
                        }
                        //}

                    }
                    i++;
                }
                startFirebase();




            } catch (IOException e) {
                e.printStackTrace();
            }

            return nameList;
        }
    }

    public void startFirebase(){

        allowed = true;
        startFirebaseListener();
        //onStart();

    }


    public void sendToDatabase(int i){
        String name = emailList.get(0)+"";

        //myRef.child(name.split("\\.")[0]).setValue(new Integer(i).toString());

        PhotoList plist = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhoto);
        for( int a = 0 ; a < plist.size(); a++)
        {
            String[] pathL = plist.getPhoto(a).getImgPath().split("/");
            String fileName = pathL[pathL.length-1];
            name = name.replace(".","&");
            myRef.child(name).child(fileName.split("\\.")[0]).setValue(plist.getPhoto(a).getNumKarma());
        }

    }

    public void updateKarmaNum(String parent, String path, int num){
        if( parent.equals("user")){
            if( emailList!=null && emailList.size()!=0) {
                parent = emailList.get(0);
            }
        }

        parent = parent.replace(".","&");

        myRef.child(parent).child(path.split("\\.")[0]).setValue(num);


    }

    public void clearDataBase(){
        String parent = emailList.get(0).replace(".","&");
        myRef.child(parent).removeValue();
    }




    public void startFirebaseListener(){

       // super.onStart();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eName = snapshot.getKey();

                        if (!eName.equals(emailList.get(0))) {
                            for( DataSnapshot snapshot1: snapshot.getChildren()) {

                                eName = eName.replace("&",".");

                                //String num = snapshot1.getValue(String.class);
                                String imgName = snapshot1.getKey();
                                String num = snapshot1.getValue()+"";
                                Log.i(snapshot1.getKey(),num);
                                sharer.friendCopy(emailList, eName, imgName, Integer.parseInt(num));
                            }
                        }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}