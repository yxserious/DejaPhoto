package test;

import com.example.jeffphung.dejaphoto.DejaVuMode;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by kaijiecai on 4/29/17.
 *
 * Test DejaVuMode class
 */

public class DejaVuModeTester {


    DejaVuMode dejaVuMode;

    @Before
    public void setUp(){
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();
    }

    @Test
    public void testSetDejaModeOn() {
    }
}
