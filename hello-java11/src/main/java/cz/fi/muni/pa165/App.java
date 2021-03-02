package cz.fi.muni.pa165;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {

        List<String> messages = new ArrayList<>();
        messages.add("Hello" );
        messages.add("World" );
        for (String s : messages){
            System.out.println(s);
        }

    }
}
