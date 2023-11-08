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
        StringBuilder result = new StringBuilder();
        int previousTag = tags.get(0); // Initialize with the first tag
        String previous = dictionary.get(previousTag);
        result.append(previous);

        for (int i = 1; i < tags.size(); i++) {
            int currentTag = tags.get(i);

            String current;
            if (dictionary.containsKey(currentTag)) {
                current = dictionary.get(currentTag);
            } else {
                current = previous + previous.charAt(0); // For early LZW implementations
            }

            result.append(current);

            // Add new entry to the dictionary
            String entry = previous + current.charAt(0);
            dictionary.put(dictionary.size(), entry);

            previous = current;
        }

        return result.toString();
    }


//______________________________________________________________________________________________________________________

}
