import java.util.*;

public class Graph {
    private static HashSet<Node> nodeList;
    private static HashMap<Node,HashSet<Node>> adjList;

    // default constructor
    Graph() {
        nodeList = new HashSet<Node>();
        adjList = new HashMap<Node,HashSet<Node>>();
    }

    // Adds a node to the graph.
    public void addNode(final String nodeVal) {
        Node temp = new Node(nodeVal);
        if(nodeList.contains(temp)) {
            return;
        }
        nodeList.add(temp);
        // Sets up the Node for adjacencies
        HashSet<Node> emptySet = new HashSet<Node>();
        adjList.put(temp,emptySet);
    }

    // Removes a node from the graph.
    public void removeNode(final Node n) {
        nodeList.remove(n);
    }

    // Adds an undirected edge between two nodes.
    public void addUndirectedEdge(Node first, Node second) {
        // cannot connect null values
        if(first == null || second == null) {
            return;
        }

        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            return;
        }
        // Add both pairs to the adjacency list
        adjList.get(first).add(second);
        adjList.get(second).add(first);
        first.adj.add(second);
        second.adj.add(first);
    }

    // Remove an undirected edge between two nodes.
    public void removeUndirectedEdge(final Node first, final Node second) {
        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            return;
        }
        // Remove nodes from each others' list.
        if(adjList.get(first).contains(second)) {
            adjList.get(first).remove(second);
            first.adj.remove(second);
        }
        if(adjList.get(second).contains(first)) {
            adjList.get(second).remove(first);
            second.adj.remove(first);
        }
    }

    // Returns a hashSet of all Nodes in the graph
    public HashSet<Node> getAllNodes() {
        return nodeList;
    }
}