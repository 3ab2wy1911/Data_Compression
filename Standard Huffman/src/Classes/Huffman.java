package Classes;
import java.util.*;

public class Huffman {
    private Node root ;
    private final String text ;

    public Huffman(String text){
        this.text= text ;
    }

    private void generateHuffmanCodes(Node node, String code, Map<Character, String> huffmanCodes){
        if (node instanceof Leaf){
            huffmanCodes.put(((Leaf) node).getCharacter(), code) ;
            return;
        }
        generateHuffmanCodes(node.getLeft(), code.concat("0"), huffmanCodes) ;
        generateHuffmanCodes(node.getRight(), code.concat("1"), huffmanCodes) ;
    }

    private void generateHuffmanCharacters(Node node, String code, Map<String, Character> huffmanCharacters){
        if (node instanceof Leaf){
            huffmanCharacters.put(code, ((Leaf) node).getCharacter()) ;
            return;
        }
        generateHuffmanCharacters(node.getLeft(), code.concat("0"), huffmanCharacters) ;
        generateHuffmanCharacters(node.getRight(), code.concat("1"), huffmanCharacters) ;
    }

    private String generateEncodedText(Map<Character, String> huffmanCodes){
        StringBuilder stringBuilder = new StringBuilder() ;
        for (char character :
                text.toCharArray()) {
            stringBuilder.append(huffmanCodes.get(character)) ;
        }

        return stringBuilder.toString();
    }

    private String generateDecodedText(Map<String, Character> huffmanCharacters) {
        StringBuilder stringBuilder = new StringBuilder() ;
        String temp = "" ;

        for (char character : text.toCharArray()) {
            temp += character ;
            if (huffmanCharacters.containsKey(temp)){
                stringBuilder.append(huffmanCharacters.get(temp)) ;
                temp = "" ;
            }
        }
        return stringBuilder.toString() ;
    }

    private Node generateTree(Map<Character, Integer> huffmanFrequencies) {
        Queue<Node> nodeQueue = new PriorityQueue<>() ;
        huffmanFrequencies.forEach((character, frequency) ->
                nodeQueue.add(new Leaf(frequency, character))) ;

        while (nodeQueue.size() > 1){
            nodeQueue.add(new Node(nodeQueue.poll(), Objects.requireNonNull(nodeQueue.poll()))) ;
        }

        return nodeQueue.poll() ;
    }

    public String encode(){
        // frequencies part
        Map<Character, Integer> huffmanFrequencies;
        {
            huffmanFrequencies = new HashMap<>();
            for (char character :
                    text.toCharArray()) {
                huffmanFrequencies.put(character, huffmanFrequencies.getOrDefault(character, 0) + 1);
            }
        }
        // generate the tree
        root = generateTree(huffmanFrequencies);

        // generate the codes
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, "", huffmanCodes) ;

        return generateEncodedText(huffmanCodes) ;
    }

    public String decode(Map<Character, Integer> frequencies){
        // generate the tree
        root = generateTree(frequencies) ;

        // generate the codes
        Map<String, Character> huffmanCharacters = new HashMap<>();
        generateHuffmanCharacters(root, "", huffmanCharacters);

        return generateDecodedText(huffmanCharacters) ;
    }
}
