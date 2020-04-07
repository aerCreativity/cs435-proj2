import java.util.*;
// Helping class Node.
public class Node {
    public String value;
    public HashSet<Node> adj;

    public Node(String val) {
        value = val;
        adj = new HashSet<Node>();
    }
}