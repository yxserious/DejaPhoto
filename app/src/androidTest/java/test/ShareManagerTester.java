package test;


import com.example.jeffphung.dejaphoto.ShareManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by not us 6/8/17.
 */




public class ShareManagerTester {
    ShareManager shareManager;


    @Before
    public void setUp(){
        shareManager = new ShareManager();

    }

    @Test
    public void shareTest(){

        FirebaseStorage testStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = testStorage.getReference();

        List<String> testEmails = new LinkedList<>();
        testEmails.add("jeff@sbcglobal.net");

        /*
        shareManager.share(testEmails);

        String storagePic = storageReference.child("/jeff@sbcglobal.net/0").getPath();

        Assert.assertEquals("/jeff@sbcglobal.net/0", storagePic);
        */

    }

    @Test
    public void shareTest2(){
        FirebaseStorage testStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = testStorage.getReference();

    }
}
