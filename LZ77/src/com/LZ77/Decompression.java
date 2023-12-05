package com.LZ77;

// Responsible for decompression operation.

import java.util.Vector;    // to use Vector.

public class Decompression
{
    Vector <String> tags;   // Tags Vector
    String uncompressed_text;   // The Final Text.

    //----------------------------------------------------------------

    public Decompression (Vector<String>t){    // constructor
        tags = t;   // assign the tags.
        uncompressed_text = ""; // set the uncompressed text to be empty.
    }

    //----------------------------------------------------------------

    public String decompress (){   // Decompressing function.

        // Looping through the tags.
        for (String tag : tags){

            tag = tag.substring(1, tag.length() - 1); // Removing < and > from the tag
            String[] parts = tag.split(",");    // Split the tag.

            // Assign the position, length, and next character.
            int pos = Integer.parseInt(parts[0]);
            int len = Integer.parseInt(parts[1]);
            String next_symbol = parts[2];

            // In case that the character has appeared in the text before.
            if (pos != '0') {
                int j = uncompressed_text.length() - pos;   // set the start index.
                for (int i = 0; i < len; i++) {
                    uncompressed_text = uncompressed_text.concat(String.valueOf(uncompressed_text.charAt(j)));  // fill the text.
                    j++;
                }
            }
            uncompressed_text = uncompressed_text.concat(next_symbol);  // also add the next character.
        }

        //----------------------------------------------------------------

        return uncompressed_text;

    }
}
