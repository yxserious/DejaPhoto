package test;

        import com.example.jeffphung.dejaphoto.StringParser;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by beierli on 6/5/17.
 */

public class StringParserTester {
    String booltrue = "true";
    String boolfalse = "false";
    String number = "2";
    String location = "some location";

    String parse = "true;10;New York";

    @Test
    public void testdecodeString()
    {
        String array[] = StringParser.decodeString(parse);

        assertEquals("true", array[0]);
        assertEquals("10", array[1]);
        assertEquals("New York", array[2]);

        String array2[] = StringParser.decodeString("true;10;New York;ALABABABA");

        assertEquals(null, array2);

        String array3[] = StringParser.decodeString(" ");
        assertEquals(null,array3);

        String array4[] = StringParser.decodeString("true;10;null");
        assertEquals(array4[2],"null");
    }

    @Test
    public void testencodeString()
    {
        assertEquals(parse, StringParser.encodeString(true, 10, "New York"));
    }

    @Test
    public void testtoInt()
    {
        //test when the string is numeric
        assertEquals(2, StringParser.toInt(number));

        //test when the string is not numeric
        assertEquals(-1, StringParser.toInt(location));
    }

    @Test
    public void testtoBoolean()
    {
        //test release states
        assertTrue(StringParser.toBoolean(booltrue));

        //test release states
        assertFalse(StringParser.toBoolean(boolfalse));
    }
}
