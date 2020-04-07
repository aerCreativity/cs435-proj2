// File used to run examples of other java code.
// Each problem will have its own local method.
import java.util.*;

class Main {
    final static String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static void main(final String[] args) {
        int testToRun = 6;
        int testSize = 1000;
        switch(testToRun) {
            case 3:
                problemThreeTest();
                break;
            case 4:
                problemFourTest();
                break;
            case 5:
                problemFiveTest(testSize);
                break;
            case 6:
                problemSixTest(testSize);
                break;
            case 7:
                problemSevenTest();
                break;
            default:
                break;
        }
    }

    // Test for problem three.
    private static void problemThreeTest() {
        final Graph llHundred = createLinkedList(100);
        final Graph llTenK = createLinkedList(10000);
        BFTRecLinkedList(llHundred);
        System.out.println("Finished Recursive traversal on 100");
        BFTIterLinkedList(llHundred);
        System.out.println("Finished Iterative traversal on 100");
        BFTRecLinkedList(llTenK);
        System.out.println("Finished Recursive traversal on 10000");
        BFTIterLinkedList(llTenK);
        System.out.println("Finished Iterative traversal on 10000");
    }

    // Test for problem four.
    private static void problemFourTest() {
        DirectedGraph dag = createRandomDAGIter(1000);
        printArr(TopSort.Kahns(dag));
        printArr(TopSort.mDFS(dag));
        return;
    }

    // Test for problem five.
    private static void problemFiveTest(int testSize) {
        WeightedGraph g = createRandomCompleteWeightedGraph(testSize);
        // pick a random node from the graph to be start, and another random to be end
        int startInd = (int)(Math.random()*testSize);

        // use iterator to traverse HashSet
        final Iterator<Node> it = g.getAllNodes().iterator();

        int index = 0;
        Node startNode = null;
        // pick two random nodes
        while (startNode == null) {
            final Node temp = it.next();
            if (startInd == index) {
                startNode = temp;
            }
        }

        printMap(dijkstras(startNode,g));
    }

    // Test for problem six.
    private static void problemSixTest(int testSize) {
        GridGraph gg = createRandomGridGraph(testSize);
        GridNode source = gg.findNode(0,0);
        GridNode dest = gg.findNode(testSize,testSize);
        ArrayList<GridNode> bestPath = astar(source,dest);
        for(GridNode n : bestPath) {
            System.out.print(n.value+", ");
        }
        System.out.println();
    }

    // Test for problem seven.
    private static void problemSevenTest() {
        // TODO
    }

    // Performs A* from sourceNode to destNode
    public static ArrayList<GridNode> astar(final GridNode sourceNode, final GridNode destNode) {
        ArrayList<GridNode> path = new ArrayList<GridNode>();
        HashSet<GridNode> untravelled = new HashSet<GridNode>();
        HashSet<GridNode> seen = new HashSet<GridNode>();
        Deque<GridNode> toTravel = new ArrayDeque<>();

        untravelled.add(sourceNode);
        toTravel.push(sourceNode);
        while(!toTravel.isEmpty()) {
            GridNode curr = toTravel.poll();
            untravelled.remove(curr);
            seen.add(curr);
            path.add(curr);
            // have we reached the destination?
            if(curr == destNode) {
                break;
            }
            // is this a dead end?
            if(curr.adj.isEmpty()) {
                // Backtracking until we have an alternate path that is untravelled
                boolean backtrack = true;
                while(backtrack) {
                    curr = path.get(path.size()-1);
                    path.remove(curr);
                    for(GridNode n : curr.adj) {
                        if(untravelled.contains(n)) {
                            backtrack = false;
                        }
                    }
                }
            } else {
                // Add adj nodes to stack to travel
                GridNode n1 = null;
                GridNode n2 = null;
                for(GridNode n : curr.adj) {
                    untravelled.add(n);
                    if(n1 == null) {
                        n1 = n;
                    } else {
                        n2 = n;
                    }
                }
                // if there's only one path to go
                if(n2 == null) {
                    toTravel.push(n2);
                    continue;
                } else {    // Apply heuristics
                    // pick whichever node has an x closer to its y to traverse first
                    if(Math.abs(n1.x-n1.y) < Math.abs(n2.x-n2.y)) {
                        toTravel.push(n2);
                        toTravel.push(n1);
                    } else {
                        toTravel.push(n1);
                        toTravel.push(n2);
                    }
                }
            }
        }

        return path;
    }

    // Generates a random grid of size n x n with randomly assigned edges
    public static GridGraph createRandomGridGraph(int n) {
        GridGraph g = new GridGraph();
        g.resizeGrid(n);    // resize to n x n grid
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                String randStr = randomString(n/2);
                g.addGridNode(i,j,randStr);
            }
        }

        // Create edges with a 50% chance
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                // Go up
                if(i<n-1 && Math.random()<.5) {
                    GridNode n1 = g.findNode(i,j);
                    GridNode n2 = g.findNode(i+1,j);
                    g.addUndirectedEdge(n1,n2);
                }
                // Go right
                if(j<n+1 && Math.random()<.5) {
                    GridNode n1 = g.findNode(i,j);
                    GridNode n2 = g.findNode(i,j+1);
                    g.addUndirectedEdge(n1,n2);
                }
            }
        }

        return g;
    }

    // uses dijkstras to create a map of each node with its distance from the start
    // we need an instance of 'g' since weights are stored within the graph
    public static HashMap<Node,Integer> dijkstras(final Node start, WeightedGraph g) {
        // Start by adding all adjacencies from start to the traversal.
        HashMap<Node,Integer> distances = g.getNeighborNodes(start);
        // Make a note of the nodes to travel
        HashSet<Node> untravelled = start.adj;
        // Make a queue of nodes to traverse, from closest to farthest
        Queue<Node> nextTravel = new LinkedList<Node>();

        for(int i=1;i <= distances.size();i++) {
            // add values that are in distances that arent travelled to queue
            for(Node n : distances.keySet()) {
                if(distances.get(n) == i && !untravelled.contains(n)) {
                    untravelled.add(n);
                    nextTravel.add(n);
                }
            }

            // exhaust the nodes to travel
            while(!nextTravel.isEmpty()) {
                Node curr = nextTravel.poll();
                HashMap<Node,Integer> currDist = g.getNeighborNodes(curr);
                for(Node n : curr.adj) {
                    // check if it's shorter for a node to travel through current node
                    int distToN = distances.get(curr)+currDist.get(n);
                    if(distances.get(n) < distToN) {
                        distances.put(n,distToN);
                    }
                }
            }

            // we're probably going to exhaust all options before reaching max distance
            if(untravelled.isEmpty()) {
                break;
            }
        }

        return distances;
    }

    // Generates a random directed acyclic graph, iteratively
    public static WeightedGraph createRandomCompleteWeightedGraph(final int n) {
        WeightedGraph g = new WeightedGraph();

        // generate n random nodes, keeping the length as short as possible.
        final HashSet<String> seenStrings = new HashSet<String>();
        final int len = (n % possibleCharacters.length()) + 1;
        for (int i = 0; i < n; i++) {
            // Generate random string of length "len"
            String temp = randomString(len);

            if (seenStrings.contains(temp)) {
                i--;
                continue;
            }
            g.addNode(temp);
        }

        // cannot have edges if n is 1 or fewer
        if (n <= 1) {
            return g;
        }

        final HashSet<Node> existingNodes = g.getAllNodes();
        // using two iterators to traverse a HashSet
        final Iterator<Node> it1 = existingNodes.iterator();
        int i1 = 0;
        while(it1.hasNext()) {
            Node temp1 = it1.next();
            // ensure that there's always a "best path"
            HashSet<Integer> seenNums = new HashSet<Integer>();
            final Iterator<Node> it2 = existingNodes.iterator();
            int i2 = 0;
            while(it2.hasNext()) {
                Node temp2 = it2.next();
                if(i1 == i2) {
                    continue;
                }
                // assign random weight
                int randomNum = (int) (Math.random() * n-1) + 1;
                while(seenNums.contains(randomNum)) {
                    randomNum = (int) (Math.random() * n-1) + 1;
                }
                seenNums.add(randomNum);
                g.addWeightedEdge(temp1,temp2,randomNum);
            }
        }

        return g;
    }

    // Generates a linked list as a weighted graph (5d)
    public static WeightedGraph createWeightedLinkedList(final int n) {
        WeightedGraph g = new WeightedGraph();

        // Creating nodes in order
        for (int i = 0; i < n; i++) {
            String temp = "";
            int val = i;
            for (int j = 0; j < (i / possibleCharacters.length()) + 1; j++) {
                temp = possibleCharacters.charAt(val % possibleCharacters.length()) + temp;
                val /= possibleCharacters.length();
            }
            g.addNode(temp);
        }

        // Linking nodes
        final HashSet<Node> existingNodes = g.getAllNodes();
        // use iterator to traverse HashSet
        final Iterator<Node> it = existingNodes.iterator();
        Node prevNode = it.next();
        while (it.hasNext()) {
            final Node currNode = it.next();
            g.addUndirectedEdge(prevNode, currNode);
            prevNode = currNode;
        }

        return g;
    }

    // Generates a random directed acyclic graph, iteratively
    public static DirectedGraph createRandomDAGIter(final int n) {
        DirectedGraph dag = new DirectedGraph();

        // generate n random nodes, keeping the length as short as possible.
        final HashSet<String> seenStrings = new HashSet<String>();
        final int len = (n % possibleCharacters.length()) + 1;
        for (int i = 0; i < n; i++) {
            String temp = randomString(len);

            if (seenStrings.contains(temp)) {
                i--;
                continue;
            }
            dag.addNode(temp);
        }

        // cannot have edges if n is 1 or fewer
        if (n <= 1) {
            return dag;
        }

        // randomly connect nodes
        final int edges = n * n / 4; // this number is because the possible total number of connections is approx
                                     // (n^2)/2
        final HashSet<Node> existingNodes = dag.getAllNodes();
        for (int i = 0; i < edges; i++) {
            // use iterator to traverse HashSet
            final Iterator<Node> it = existingNodes.iterator();

            // generate two unique indeces
            final int num1 = (int) (Math.random() * (n-1));
            int num2 = num1;
            while (num2 <= num1) {  // ensures that the graph stays acyclic
                num2 = (int) (Math.random() * n);
            }

            final int index = 0;
            Node n1 = null;
            Node n2 = null;
            // pick two random nodes
            while (n1 == null && n2 == null && it.hasNext()) {
                final Node temp = it.next();
                if (num1 == index) {
                    n1 = temp;
                }
                if (num2 == index) {
                    n2 = temp;
                }
            }

            // link the random nodes
            dag.addDirectedEdge(n1, n2);
        }

        return dag;
    }

    // Generates a random unweighted graph, iteratively.
    public static Graph createRandomUnweightedGraphIter(final int n) {
        final Graph g = new Graph();

        // generate n random nodes, keeping the length as short as possible.
        final HashSet<String> seenStrings = new HashSet<String>();
        final int len = (n % possibleCharacters.length()) + 1;
        for (int i = 0; i < n; i++) {
            // Generate random string of length "len"
            String temp = randomString(len);

            if (seenStrings.contains(temp)) {
                i--;
                continue;
            }
            g.addNode(temp);
        }

        // cannot have edges if n is 1 or fewer
        if (n <= 1) {
            return g;
        }

        // randomly connect nodes
        final int edges = n * n / 4; // this number is because the possible total number of connections is approx
                                     // (n^2)/2
        final HashSet<Node> existingNodes = g.getAllNodes();
        for (int i = 0; i < edges; i++) {
            // use iterator to traverse HashSet
            final Iterator<Node> it = existingNodes.iterator();

            // generate two unique indeces
            final int num1 = (int) (Math.random() * n);
            int num2 = num1;
            while (num2 == num1) {
                num2 = (int) (Math.random() * n);
            }

            final int index = 0;
            Node n1 = null;
            Node n2 = null;
            // pick two random nodes
            while (n1 == null && n2 == null && it.hasNext()) {
                final Node temp = it.next();
                if (num1 == index) {
                    n1 = temp;
                }
                if (num2 == index) {
                    n2 = temp;
                }
            }

            // link the random nodes
            g.addUndirectedEdge(n1, n2);
        }

        return g;
    }

    // Generates a linkedlist of size n as a graph
    public static Graph createLinkedList(final int n) {
        Graph g = new Graph();

        // Creating nodes in order
        for (int i = 0; i < n; i++) {
            String temp = "";
            int val = i;
            for (int j = 0; j < (i / possibleCharacters.length()) + 1; j++) {
                temp = possibleCharacters.charAt(val % possibleCharacters.length()) + temp;
                val /= possibleCharacters.length();
            }
            g.addNode(temp);
        }

        // Linking nodes
        final HashSet<Node> existingNodes = g.getAllNodes();
        // use iterator to traverse HashSet
        final Iterator<Node> it = existingNodes.iterator();
        Node prevNode = it.next();
        while (it.hasNext()) {
            final Node currNode = it.next();
            g.addUndirectedEdge(prevNode, currNode);
            prevNode = currNode;
        }

        return g;
    }

    // runs a BFT Recursive on a LinkedList
    public static ArrayList<Node> BFTRecLinkedList(final Graph graph) {
        return GraphSearch.BFTRec(graph);
    }

    // runs a BFT Iteratively on a LinkedList
    public static ArrayList<Node> BFTIterLinkedList(final Graph graph) {
        return GraphSearch.BFTIter(graph);
    }

    // Helper method. Generates a random string of length n using characters from possibleCharacters
    private static String randomString(int n){
        String s = "";
        for(int i=0; i<n; i++) {
            char c = possibleCharacters.charAt((int)(Math.random()*possibleCharacters.length()));
            s += c;
        }
        return s;
    }

    // Helper method. Prints an HashMap which is outputted from dijkstras.
    public static void printMap(HashMap<Node,Integer> map) {
        for(Node n : map.keySet()) {
            System.out.print(n.value+"="+map.get(n)+",");
        }
        System.out.println();
    }

    // Helper method. Prints an Arraylist of nodes to stdout
    public static void printArr(ArrayList<Node> arr) {
        for(Node n : arr) {
            System.out.print(n.value + ", ");
        }
        System.out.println();
    }
}