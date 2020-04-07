import java.util.*;

public class TopSort{
    // returns a valid toplogical sort of the graph using Kahn's algorithm
    public static ArrayList<Node> Kahns(final DirectedGraph graph) {
        ArrayList<Node> sorted = new ArrayList<Node>();

        HashMap<Node,Integer> degreeMap = findDegrees(graph);
        Deque<Node> queue = new ArrayDeque<Node>();

        // add any starter nodes to the queue (degree == 0)
        for(Node n : degreeMap.keySet()) {
            if(degreeMap.get(n) == 0) {
                queue.add(n);
            }
        }

        // traverse through all nodes
        while(!queue.isEmpty()) {
            Node curr = queue.pop();
            if(!sorted.contains(curr)) {
                sorted.add(curr);
                // queue all children
                for(Node n : curr.adj) {
                    queue.add(n);
                }
            }
        }

        return sorted;
    }

    // helper function to find degrees based on incoming edges to each node
    private static HashMap<Node,Integer> findDegrees(final DirectedGraph g) {
        final HashMap<Node,Integer> inDegree = new HashMap<Node,Integer>();
        
        // Start each counter with 0
        for(Node n : g.getAllNodes()) {
            inDegree.put(n,0);
        }
        
        // increment each node's edge whenever the node appears as a target
        for(Node n : g.getAllNodes()) {
            for(Node target : n.adj) {
                inDegree.put(target,inDegree.get(target)+1);
            }
        }

        return inDegree;
    }

    // returns a valid toplogical sort of the graph using the mDFS algorithm
    public static ArrayList<Node> mDFS(final DirectedGraph graph) {
        ArrayList<Node> sorted = new ArrayList<Node>();
        HashSet<Node> inGraph = graph.getAllNodes();
        HashSet<Node> visited = new HashSet<Node>();
        Deque<Node> stack = new ArrayDeque<Node>();

        // There are possibly independent portions of the graph due to random generation
        for(Node n : inGraph) {
            if(!visited.contains(n)) {
                visited.add(n);
                stack.push(n);
            }
            while(!stack.isEmpty()) {
                Node curr = stack.pop();
                for(Node child : curr.adj) {
                    stack.add(child);
                }
                sorted.add(curr);
            }
        }

        return sorted;
    }
}