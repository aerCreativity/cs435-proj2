import java.util.*;

// Class which handles searching
public class GraphSearch {
    // Perform recursive Depth-First Search
    public static ArrayList<Node> DFSRec(final Node start, final Node end) {
        ArrayList<Node> traversed = new ArrayList<Node>();
        traversed.add(start);
        if(start == end) {
            return traversed;
        }
        for(Node n : start.adj) {
            if(traversed.contains(n)) {
                continue;
            }
            ArrayList<Node> temp = DFSRec(n,end);
            for(Node tempNode : temp) {
                traversed.add(tempNode);
                if(tempNode == end) {
                    return traversed;
                }
            }
        }

        return traversed;
    }

    // Perform iterative Depth-First Search
    public static ArrayList<Node> DFSIter(final Node start, final Node end) {
        ArrayList<Node> traversed = new ArrayList<Node>();
        // create a stack of items to search
        Deque<Node> stack = new ArrayDeque<Node>();
        stack.push(start);
        while(stack.size() != 0) {
            Node curr = stack.pop();
            traversed.add(curr);
            if(curr == end) {
                break;
            }
            for(Node n : curr.adj) {
                if(!traversed.contains(n))
                    stack.push(n);
            }
        }
        return traversed;
    }

    // Perform recursive Breadth-First Traversal
    public static ArrayList<Node> BFTRec(final Graph graph) {
        ArrayList<Node> traversed = new ArrayList<Node>();
        Node temp = new Node("temp");
        Graph graphCopy = graph;
        for(Node n : graph.getAllNodes()) {
            if(traversed.contains(n)) {
                continue;
            }
            temp = n;
        }
        graphCopy.removeNode(temp);
        for(Node n : temp.adj) {
            graphCopy.removeUndirectedEdge(temp, n);
            traversed.addAll(BFTRec(graphCopy));
        }
        return traversed;
    }

    // Perform iterative Breadth-First Traversal
    public static ArrayList<Node> BFTIter(final Graph graph) {
        ArrayList<Node> traversed = new ArrayList<Node>();
        for(Node n : graph.getAllNodes()) {
            if(traversed.contains(n)) {
                continue;
            }
            Queue<Node> q = new LinkedList<Node>();
            q.add(n);
            while(q.peek() != null) {
                Node temp = q.poll();
                traversed.add(temp);
                for(Node tempNode : temp.adj) {
                    q.add(tempNode);
                }
            }
        }
        return traversed;
    }
}