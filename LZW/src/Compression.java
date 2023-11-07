import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compression {
    private final List<String> tags;
    private final HashMap<String, Integer> dictionary;
    int dictSize = 128;
    private final String text;
    private String t = "";

//______________________________________________________________________________________________________________________

    public Compression(String text) {
        tags = new ArrayList<>();
        this.text = text;
        dictionary = new HashMap<>();
        for (int i = 0; i < 128; i++) {
            dictionary.put(String.valueOf(((char) i)), i);  // Fills the ASCII with the chars till 127.
        }
    }

//______________________________________________________________________________________________________________________

    public List<String> compress() {

        for (char s : text.toCharArray()) { // Read Symbol S.

            String pattern = t + s; // Get the pattern.
            if (dictionary.containsKey(pattern)) {
                t = pattern;    // if we find the pattern in the dictionary then assign it to the t "which we will add to tags".
            }
            else {
                // If we don't find the pattern then we add the index of t to the tags list.
                // Then we add the pattern to the dictionary with incrementing the index.
                // Then we initialize the t with the value of s to refill the tags with a new tag.
                tags.add("<" + dictionary.get(t) + ">");
                dictionary.put(pattern, dictSize++);
                t = String.valueOf(s);
            }
        }

        return tags;
    }

//______________________________________________________________________________________________________________________

}