package test;


import android.location.Location;
//import android.support.test.rule.ActivityTestRule;

import com.example.jeffphung.dejaphoto.MainActivity;
import com.example.jeffphung.dejaphoto.PhotoLoaderTask;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class PhotoLoaderTester {

    PhotoLoaderTask photoLoaderTask;
    /*
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setup(){
        photoLoaderTask = new PhotoLoaderTask(mainActivity.getActivity());
    }

    @Test
    public void testToBoolean() {
        assertTrue(photoLoaderTask.toBoolean("true"));
        //more test cases
        assertFalse(photoLoaderTask.toBoolean("false"));
        assertFalse(photoLoaderTask.toBoolean("-1"));
    }

    @Test
    public void testToGregorianCalendar(){
        // calendar constructor month start at 0, 8 means Sept
        // toCalendar method will take care of this.
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        calendar.set(2015,8,14,2,28,0);
        assertEquals(calendar,photoLoaderTask.toGregorianCalendar("2015:9:14", "2:28:0"));

        calendar.set(2016,11,30,1,30,59);
        assertEquals(calendar,photoLoaderTask.toGregorianCalendar("2016:12:30", "1:30:59"));

        calendar.set(1990,6,1,1,1,1);
        assertEquals(calendar,photoLoaderTask.toGregorianCalendar("1990:7:1", "1:1:1"));
    }

    @Test
    public void testToDouble(){
        DecimalFormat twoDForm = new DecimalFormat("#.####");

        assertEquals(new Double(-10.1669),
                Double.valueOf(twoDForm.format(photoLoaderTask.toDouble("10/1,10/1,100/100","W"))));
        //more test cases
        //google DMS to decimal convertor

        assertEquals(new Double(-20.5003),Double.valueOf(twoDForm.format(photoLoaderTask.toDouble("20/1,30/1,100/100","W"))));

        assertEquals(new Double(-1.0169),Double.valueOf(twoDForm.format(photoLoaderTask.toDouble("1/1,1/1,100/100","W"))));
        assertEquals(new Double(-10.1694),Double.valueOf(twoDForm.format(photoLoaderTask.toDouble("10/1,10/1,1000/100","W"))));

        assertEquals(new Double(-1.0003),Double.valueOf(twoDForm.format(photoLoaderTask.toDouble("0/0,60/1,100/100","W"))));

        assertEquals(new Double(-20.5003),Double.valueOf(twoDForm.format(photoLoaderTask.toDouble("20/1,30/1,100/100","W"))));



    }

    @Test
    public void testToLocation(){
        final double DELTA = 1e-15;
        Location location = new Location("location");
        location.setLongitude(photoLoaderTask.toDouble("10/1,10/1,100/100","E"));
        location.setLatitude(photoLoaderTask.toDouble("10/1,10/1,100/100","N"));

        assertEquals(location.getLatitude(),
                photoLoaderTask.toLocation("10/1,10/1,100/100","E","10/1,10/1,100/100","N").getLatitude()
                ,DELTA);


        assertEquals(location.getLatitude(),
                photoLoaderTask.toLocation("10/1,10/1,100/100","E","10/1,10/1,100/100","N").getLongitude()
                ,DELTA);

    }
    */
}
