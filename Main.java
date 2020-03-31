// File used to run examples of other java code.
// Each problem will have its own local method.
import java.util.*;

class Main {
    public static void main(String[] args) {
        problemThreeTest();
    }

    private static void problemThreeTest() {
        return;
    }

    // Generates a random unweighted graph, iteratively.
    public static Graph createRandomUnweightedGraphIter(int n) {
        Graph g = new Graph();
        final String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // generate n random nodes, keeping the length as short as possible.
        HashSet<String> seenStrings = new HashSet<String>();
        int len = (n%possibleCharacters.length())+1;
        for(int i=0; i<n; i++) {
            // Generate random string of length "len"
            String temp = "";
            for(int j=0; j<len; j++) {
                // Random character from "possibleCharacters"
                temp = temp + possibleCharacters.charAt((int)(Math.random()*possibleCharacters.length()));
            }

            if(seenStrings.contains(temp)) {
                i--;
                continue;
            }
            g.addNode(temp);
        }

        // cannot have edges if n is 1 or fewer
        if(n <= 1) {
            return g;
        }

        // randomly connect nodes
        int edges = n*n/4;      // this number is because the possible total number of connections is approx (n^2)/2
        HashSet<Node> existingNodes = g.getAllNodes();
        for(int i=0; i<edges; i++) {
            // use iterator to traverse HashSet
            Iterator<Node> it = existingNodes.iterator();

            // generate two unique indeces
            int num1 = (int)(Math.random()*n);
            int num2 = num1;
            while(num2 == num1) {
                num2 = (int)(Math.random()*n);
            }

            int index=0;
            Node n1 = null;
            Node n2 = null;
            // pick two random nodes
            while(n1 == null && n2 == null && it.hasNext()) {
                Node temp = it.next();
                if(num1 == index) {
                    n1 = temp;
                }
                if(num2 == index) {
                    n2 = temp;
                }
            }

            // link the random nodes
            g.addUndirectedEdge(n1, n2);
        }

        return g;
    }

    // Generates a linkedlist of size n as a graph
    public static Graph createLinkedList(int n) {
        Graph g = new Graph();
        final String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Creating nodes in order
        for(int i=0; i<n; i++) {
            String temp="";
            int val=i;
            for(int j=0; j<(i%possibleCharacters.length())+1; j++) {
                temp = possibleCharacters.charAt(val%possibleCharacters.length()) + temp;
                val /= possibleCharacters.length();
            }
        }

        // Linking nodes
        HashSet<Node> existingNodes = g.getAllNodes();
        // use iterator to traverse HashSet
        Iterator<Node> it = existingNodes.iterator();
        Node prevNode = it.next();
        while(it.hasNext()) {
            Node currNode = it.next();
            g.addUndirectedEdge(prevNode, currNode);
            prevNode = currNode;
        }

        return g;
    }
}