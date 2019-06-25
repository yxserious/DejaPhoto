package test;

//import android.support.test.rule.ActivityTestRule;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.example.jeffphung.dejaphoto.MainActivity;
import com.example.jeffphung.dejaphoto.MockLocation;
import com.example.jeffphung.dejaphoto.PhotoSorterTask;
import com.example.jeffphung.dejaphoto.UpdatePoints;

import org.junit.*;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class UpdatePointsTester {


    PhotoSorterTask photoSorterTask;




    @Before
    public void setUp(){
        photoSorterTask = new PhotoSorterTask();

    }


    @Test
    public void testWithinHours(){

        GregorianCalendar gregorianCalendar = new GregorianCalendar(2011,2,3,9,1,0);
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2011,2,3,10,1,0);
        assertTrue(UpdatePoints.withinHours(gregorianCalendar,gregorianCalendar1));

        gregorianCalendar = new GregorianCalendar(2015,5,3,9,1,0);
        gregorianCalendar1 = new GregorianCalendar(2015,5,3,10,1,0);
        assertTrue(UpdatePoints.withinHours(gregorianCalendar,gregorianCalendar1));

        gregorianCalendar = new GregorianCalendar(2015,5,3,9,1,0);
        gregorianCalendar1 = new GregorianCalendar(2020,8,3,2,2,0);
        assertFalse(UpdatePoints.withinHours(gregorianCalendar,gregorianCalendar1));

    }


    @Test
    public void testIsLocationClose(){
        MockLocation m1 = new MockLocation();
        MockLocation m2 = new MockLocation();

        m1.setLatitude(0);
        m1.setLongitude(0);
        m2.setLatitude(0.001);
        m2.setLongitude(0.001);

        assertTrue(UpdatePoints.isLocationClose(m1,m2));

        m2.setLongitude(3);
        assertFalse(UpdatePoints.isLocationClose(m1,m2));
    }
    @Test
    public void testToSecond(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2011,2,3,9,1,0);
        assertEquals(32460,UpdatePoints.calendarToSecond(gregorianCalendar));

        gregorianCalendar = new GregorianCalendar(2011,5,5,5,5,50);
        assertEquals(18350,UpdatePoints.calendarToSecond(gregorianCalendar));

        gregorianCalendar = new GregorianCalendar(2011,1,1,0,0,0);
        assertEquals(0,UpdatePoints.calendarToSecond(gregorianCalendar));


    }

    @Test
    public void testSameDayOfWeek(){
        //calendar constructor's month start at 0, which is 1 represents Feb
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2016,1,4,9,1,0);
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2017,4,11,9,1,0);
        assertTrue(UpdatePoints.sameDayOfWeek(gregorianCalendar,gregorianCalendar1));

        gregorianCalendar = new GregorianCalendar(2017,5,14,9,1,0);
        gregorianCalendar1 = new GregorianCalendar(2017,5,21,9,1,0);
        assertTrue(UpdatePoints.sameDayOfWeek(gregorianCalendar,gregorianCalendar1));

        gregorianCalendar = new GregorianCalendar(2016,5,14,9,1,0);
        gregorianCalendar1 = new GregorianCalendar(2017,6,4,9,1,0);
        assertTrue(UpdatePoints.sameDayOfWeek(gregorianCalendar,gregorianCalendar1));
    }



}
