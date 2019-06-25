package test;

import com.example.jeffphung.dejaphoto.Photo;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class PhotoTester {
    Photo photo1;
    Photo photo2;
    Photo photo3;

    @Before
    public void setUp(){
        photo1 = new Photo("/storage/sdcard/DCIM/Camera/img6.JPG");
        photo2 = new Photo("/storage/sdcard/DCIM/Camera/img5.JPG");

        photo3 = new Photo("/storage/sdcard/DCIM/Camera/img4.jpg");
        photo3.addPoints(2);

    }

    @Test
    public void testCompareTo(){

        //compares points/calendars
        assertEquals(-1, photo1.compareTo(photo2));

        assertEquals(-1, photo2.compareTo(photo3));

        //compares points
        assertEquals(1, photo3.compareTo(photo2));
    }

    @Test
    public void testPoints(){

        //initial condition
        assertEquals(0, photo1.getPoints());

        //test addpoints
        photo1.addPoints(2);
        assertEquals(2, photo1.getPoints());
        photo1.addPoints(2);
        photo1.addPoints(2);
        assertEquals(6, photo1.getPoints());
    }

    @Test
    public void testKarma(){

        //initial condition
        assertFalse(photo1.getKarma());

        //test karma switches
        photo1.setKarma(true);
        assertTrue(photo1.getKarma());

        photo1.setKarma(false);
        assertFalse(photo1.getKarma());


    }

    @Test
    public void testReleased(){
        //initial condition
        assertFalse(photo1.isReleased());

        //test release states
        photo1.setReleased(true);
        assertTrue(photo1.isReleased());

        photo1.setReleased(false);
        assertFalse(photo1.isReleased());
    }

}
