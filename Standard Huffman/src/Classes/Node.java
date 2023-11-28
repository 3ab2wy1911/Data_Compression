package Classes;

public class Node implements Comparable<Node>{
    private final int frequency ;
    private Node left ;
    private Node right ;

    /**
     *<pre>
     *Constructor to initialize the {@code frequency attribute} for the leaf
     *</pre>
     * @param frequency <strong style="color:'white'"> represent the frequency of the node</strong>
     */
    public Node(int frequency) {
        this.frequency = frequency;
    }

    /**
     *<pre>
     *Constructor to initialize the {@code Node attributes}
     *</pre>
     * @param left <strong style="color:'white'"> represent the left child of the node</strong>
     * @param right <strong style="color:'white'"> represent the right child of the node</strong>
     */
    public Node(Node left, Node right) {
        this.frequency = left.getFrequency() + right.getFrequency();
        this.left = left;
        this.right = right;
    }

    public int getFrequency() {
        return frequency;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    /**
     * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that
     * {@code x.compareTo(y)} must throw an exception if and only if {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z)) == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param other
     *         the object to be compared.
     *
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     *
     * @throws NullPointerException
     *         if the specified object is null
     * @throws ClassCastException
     *         if the specified object's type prevents it from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any class that implements the
     * {@code Comparable} interface and violates this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is inconsistent with equals."
     */
    @Override
    public int compareTo(Node other) {
        return Integer.compare(frequency, other.getFrequency()) ;
    }
}
