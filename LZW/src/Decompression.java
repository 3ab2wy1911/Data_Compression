import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Decompression {
    private final List<Integer> tags = new ArrayList<>();
    private final HashMap<Integer, String> dictionary;
    int dictSize = 128;
    private String text;

//______________________________________________________________________________________________________________________

    public Decompression(String text) {
        text = text.replaceAll("[\\[\\]<>]", "");
        List<String> tagsString = List.of(text.split(","));
        for (String tag : tagsString) {
            tags.add(Integer.parseInt(tag.trim()));
        }

        this.text = "";
        dictionary = new HashMap<>();
        for (int i = 0; i < 128; i++) {
            dictionary.put(i,String.valueOf(((char) i)));  // Fills the ASCII with the chars till 127.
        }
    }

//______________________________________________________________________________________________________________________

    public String decompress() {

        String t = ""; // Initialize 't'

        for (int s : tags) {
            if (s < 128){
                t = String.valueOf((char) s); // Append directly without resetting 't'
            }
            else if (!dictionary.containsKey(s)){
                t = t + t.charAt(0); // Append first character of 't'
            }
            else {
                t = dictionary.get(s); // Assign 't' based on the dictionary value for 's'
            }

            text = text.concat(t);

            // Update dictionary
            if (!dictionary.containsValue(t + t.charAt(0))) {
                dictionary.put(dictSize++, t + t.charAt(0));
            }
        }
        return text;
    }


//______________________________________________________________________________________________________________________

}
