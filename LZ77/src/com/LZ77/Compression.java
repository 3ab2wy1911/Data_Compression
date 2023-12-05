package com.LZ77;

// Responsible for the compression operation.

import java.util.Vector;    // to use Vector.

public class Compression {
    private String search_buffer;   // search  buffer.
    private final String look_ahead_buffer;   // look-ahead buffer.
    private final Vector <String> tags = new Vector<>();  // tags vector.

    //----------------------------------------------------------------

    // Constructor
    public Compression (String input){ // constructor.

        look_ahead_buffer = input;  // assigning the look_ahead_buffer with the given string.
        search_buffer = String.valueOf(input.charAt(0));    // add the first  character to the search buffer.
        tags.add("<0,0,"+input.charAt(0)+">");  // Add the first tag to the tags vector.
    }


    //----------------------------------------------------------------

    public Vector <String> compress () {   // compressing function.

        //  i for looking in the look_ahead_buffer .
        int i = 1 ;

        // while i still not reached the last character of the look_ahead_buffer.
        while (i < look_ahead_buffer.length()) {

            // The length and the position of the tag.
            int length = 0 , position = 0 ;

            // Start filling our match variable.
            String match = String.valueOf(look_ahead_buffer.charAt(i));

            if (!search_buffer.contains(match)){    // if we didn't find a match in the search buffer.
                tags.add("<0,0,"+match+">");    // we add the tag with len & pos 0 and the character.
                search_buffer = search_buffer.concat(match); // we add the character to the search buffer.
                ++i;    // Then we increment the i to go to the second character.
                continue;   // Here we continue because we don't need the rest of the search operation.
            }

            // We found a match in the search buffer, so we will tend to find the maximum possible match.
            int start = i;  //  Our starting index in the look-ahead buffer.
            while (search_buffer.contains(match) && i < look_ahead_buffer.length()-1){  // Every time we found a match, and we didn't go out of bounds.
                position = start - search_buffer.lastIndexOf(match);    // we update the position.
                search_buffer = search_buffer.concat(String.valueOf(look_ahead_buffer.charAt(i)));   // we add the character to the search buffer.
                length = match.length();    // we update the length.
                ++i;    // go to next character.
                match += look_ahead_buffer.charAt(i);   // we add the character to the match we compare with.
            }


            search_buffer = search_buffer.concat(String.valueOf(look_ahead_buffer.charAt(i)));   // we add the next character to the search buffer.
            tags.add("<" + position + "," + length + "," + look_ahead_buffer.charAt(i) + '>');  // we add the new tag to tags vector.
            ++i;    // we go to the next character.
        }

        //----------------------------------------------------------------

        return tags;

    }
}
