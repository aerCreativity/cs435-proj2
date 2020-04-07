import java.util.*;

public class GridGraph {
    // Note that getAllNodes is inherited from Graph and therefore not implemented.
    private static HashSet<GridNode> nodeList;
    private static HashMap<GridNode,HashSet<GridNode>> adjList;

    public GridGraph() {
        nodeList = new HashSet<GridNode>();
        adjList = new HashMap<GridNode,HashSet<GridNode>>();
    }

    // adds a gridnode at pos x,y with value nodeVal
    public void addGridNode(final int x, final int y, final String nodeVal) {
        GridNode newNode = new GridNode(x,y,nodeVal);
        nodeList.add(newNode);
        HashSet<GridNode> temp = new HashSet<GridNode>();
        adjList.put(newNode,temp);
    }

    // Adds an undirected edge between two nodes.
    public void addUndirectedEdge(GridNode first, GridNode second) {
        // cannot connect null values
        if(first == null || second == null) {
            return;
        }

        // if these nodes don't exist, display an error
        if(!(nodeList.contains(first) &&  nodeList.contains(second))) {
            return;
        }

        // Check if nodes are adjacent
        int xDiff = first.x-second.x;
        int yDiff = first.y-second.y;
        if((xDiff == 0 && (yDiff == -1 || yDiff == 1)) || ((xDiff == -1 || xDiff == 1) && yDiff == 0)) {
            // Add both pairs to the adjacency list
            adjList.get(first).add(second);
            adjList.get(second).add(first);
            first.adj.add(second);
            second.adj.add(first);
        } else {
            System.err.print("Nodes are not adjacent.");
        }
    }

    // Remove an undirected edge between two nodes.
    public void removeUndirectedEdge(final GridNode first, final GridNode second) {
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
    public HashSet<GridNode> getAllNodes() {
        return nodeList;
    }

    // Helper method to assist in finding Nodes using the NodeMap.
    public GridNode findNode(int x, int y) {
        for(GridNode n : nodeList) {
            if(n.x == x && n.y == y) {
                return n;
            }
        }
        return null;
    }
}