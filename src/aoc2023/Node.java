package aoc2023;

public class Node {
    final public String value;
    public Node leftPointer = null;
    public Node rightPointer = null;

    public Node(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' +
                ", leftPointer=" + leftPointer +
                ", rightPointer=" + rightPointer +
                '}';
    }
}
