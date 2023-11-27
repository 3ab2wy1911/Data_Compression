package Classes;

public class Leaf extends Node{
    private final char character ;

    /**
     * <pre>
     * Constructor to initialize the {@code Node attributes}
     * </pre>
     *
     * @param frequency
     *         <strong style="color:'white'"> represent the frequency of the node</strong>
     * @param character
     *         <strong style="color:'white'"> represent the character of the leaf</strong>
     */
    public Leaf(int frequency, char character) {
        super(frequency);
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}
