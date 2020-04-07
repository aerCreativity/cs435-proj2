import java.util.*;

public class DirectedGraph extends Graph {
    private static HashSet<Node> nodeList;
    private static HashMap<Node,HashSet<Node>> adjList;

    // No need to recreate addNode and getAllNodes due to inheritance.

    // Adds an undirected edge between two nodes.
    public void addDirectedEdge(Node first, Node second) {
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
        first.adj.add(second);
    }

    // Remove an undirected edge between two nodes.
    public void removeUndirectedEdge(Node first, Node second) {
        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            return;
        }
        // Remove node from adjacency
        if(adjList.get(first).contains(second)) {
            adjList.get(first).remove(second);
            first.adj.remove(second);
        }
    }
}