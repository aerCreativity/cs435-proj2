import java.util.*;

public class WeightedGraph extends Graph {
    private static HashSet<Node> nodeList;
    private static HashMap<Node,HashMap<Node,Integer>> adjList;

    // Note that the methods "addNode" and "getAllNodes" are implemented and inherited from Graph

    // Adds a directed, weighted edge to the graph
    public void addWeightedEdge(final Node first, final Node second, final int edgeWeight) {
        // cannot connect null values
        if(first == null || second == null) {
            return;
        }

        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            return;
        }
        // Add both pairs to the adjacency list
        adjList.get(first).put(second,edgeWeight);
        first.adj.add(second);
    }

    
    // Remove an undirected edge between two nodes.
    public void removeUndirectedEdge(Node first, Node second) {
        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            return;
        }
        // Remove node from adjacency
        if(adjList.get(first).containsKey(second)) {
            adjList.get(first).remove(second);
            first.adj.remove(second);
        }
    }

    // Method to find all neighboring nodes of n
    public HashMap<Node,Integer> getNeighborNodes(Node n) {
        return adjList.get(n);
    }
}