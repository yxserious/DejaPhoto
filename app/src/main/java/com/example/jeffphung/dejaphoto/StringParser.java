package com.example.jeffphung.dejaphoto;

/**
 * Created by beierli on 6/5/17.
 */

public class StringParser {
    public static String [] decodeString (String s)
    {
        String array[];
        if(s !=null) {
            array = s.split(";");

            if (array.length == 3) {
                return array;
            }
        }
        return null;
    }

    public static String encodeString (boolean a, int b, String c)
    {
        String result;
        if(!a)
        {
            result = "false;";
        }
        else
        {
            result = "true;";
        }

        result = result + Integer.toString(b) + ";" + c;
        return result;
    }

    public static int toInt(String b)
    {
        int number;
        try{
            number = Integer.parseInt(b);
        }
        catch(NumberFormatException e)
        {
            number = -1;
        }
        return number;
    }

    public static boolean toBoolean (String b)
    {

        return b.equals("true");
    }

}

