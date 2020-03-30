import java.util.*;

public class Graph {
    // do stuff
    private static HashSet<Node> nodeList;
    private static HashMap<Node,HashSet<Node>> adjList;

    // Adds a node to the graph.
    public static void addNode(final String nodeVal) {
        Node temp = new Node(nodeVal);
        if(nodeList.contains(temp)) {
            return;
        }
        nodeList.add(temp);
        // Sets up the Node for adjacencies
        HashSet<Node> emptySet = new HashSet<Node>();
        adjList.put(temp,emptySet);
    }

    // Adds an undirected edge between two nodes.
    public static void addUndirectedEdge(final Node first, final Node second) {
        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            System.err.print("Check if these Nodes are in Graph!");
            return;
        }
        // Add both pairs to the adjacency list
        adjList.get(first).add(second);
        adjList.get(second).add(first);
    }

    // Remove an undirected edge between two nodes.
    public static void removeUndirectedEdge(final Node first, final Node second) {
        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            System.err.print("Check if these Nodes are in Graph!");
            return;
        }
        // Remove nodes from each others' list.
        if(adjList.get(first).contains(second)) {
            adjList.get(first).remove(second);
        }
        if(adjList.get(second).contains(first)) {
            adjList.get(second).remove(first);
        }
    }

    // Returns a hashSet of all Nodes in the graph
    public static HashSet<Node> getAllNodes() {
        return nodeList;
    }
}