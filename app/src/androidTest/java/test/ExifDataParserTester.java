package test;


import android.location.Location;

import com.example.jeffphung.dejaphoto.ExifDataParser;

import org.junit.Assert;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//import android.support.test.rule.ActivityTestRule;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class ExifDataParserTester {

    /*
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setup(){
        photoLoaderTask = new PhotoLoaderTask(mainActivity.getActivity());
    }
    */
    @Test
    public void testToBoolean() {
        assertTrue(ExifDataParser.toBooleanTruth("true"));
        //more test cases
        assertFalse(ExifDataParser.toBooleanTruth("false"));
        assertFalse(ExifDataParser.toBooleanTruth("-1"));
    }

    @Test
    public void testToGregorianCalendar() {
        // calendar constructor month start at 0, 8 means Sept
        // toCalendar method will take care of this.
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        calendar.set(2015, 8, 14, 2, 28, 0);


        GregorianCalendar calendar1 = com.example.jeffphung.dejaphoto.ExifDataParser.toGregorianCalendar("2015:9:14", "2:28:0");

        Assert.assertEquals(calendar.HOUR, calendar1.HOUR);

        calendar.set(2016, 11, 30, 1, 30, 59);

        calendar1 = com.example.jeffphung.dejaphoto.ExifDataParser.toGregorianCalendar("2016:12:30", "1:30:59");
        Assert.assertEquals(calendar.MONTH, calendar1.MONTH);

        calendar.set(1990, 6, 1, 1, 1, 1);
        calendar1 = com.example.jeffphung.dejaphoto.ExifDataParser.toGregorianCalendar("1990:7:1", "1:1:1");
        Assert.assertEquals(calendar.HOUR, calendar1.HOUR);
    }
    @Test
    public void testToDouble(){
        DecimalFormat twoDForm = new DecimalFormat("#.####");

        assertEquals(new Double(-10.1669),
                Double.valueOf(twoDForm.format(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("10/1,10/1,100/100","W"))));
        //more test cases
        //google DMS to decimal convertor

        assertEquals(new Double(-20.5003),Double.valueOf(twoDForm.format(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("20/1,30/1,100/100","W"))));

        assertEquals(new Double(-1.0169),Double.valueOf(twoDForm.format(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("1/1,1/1,100/100","W"))));
        assertEquals(new Double(-10.1694),Double.valueOf(twoDForm.format(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("10/1,10/1,1000/100","W"))));

        assertEquals(new Double(-1.0003),Double.valueOf(twoDForm.format(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("0/0,60/1,100/100","W"))));

        assertEquals(new Double(-20.5003),Double.valueOf(twoDForm.format(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("20/1,30/1,100/100","W"))));



    }

    @Test
    public void testToLocation(){
        final double DELTA = 1e-15;
        Location location = new Location("location");
        location.setLongitude(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("10/1,10/1,100/100","E"));
        location.setLatitude(com.example.jeffphung.dejaphoto.ExifDataParser.toDouble("10/1,10/1,100/100","N"));

        Assert.assertEquals(location.getLatitude(),
                com.example.jeffphung.dejaphoto.ExifDataParser.toLocation("10/1,10/1,100/100","E","10/1,10/1,100/100","N").getLatitude()
                ,DELTA);


        Assert.assertEquals(location.getLatitude(),
                com.example.jeffphung.dejaphoto.ExifDataParser.toLocation("10/1,10/1,100/100","E","10/1,10/1,100/100","N").getLongitude()
                ,DELTA);


    }
}
