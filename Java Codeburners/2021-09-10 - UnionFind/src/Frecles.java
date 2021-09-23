import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Frecles {
	/**
	 * This class handles two actions:
	 *	-Taking the Union of two sets
	 * 	-Finding the set in which an element is a part of
	 */
	private class UnionFind {
        // p[i] is the node that element i points at
        // if element i is a set representative, p[i] = -size instead
        public int[] p;
       
        /**
         * Creates a UnionFind object with size n
         * @param n	The maximum number of elements that we are working with
         */
        public UnionFind(int n) {
            p = new int[n];
            // initially, every node is the representative of it's own set
            Arrays.fill(p, -1);
        }
        
        /**
         * Find the set that the integer x is a part of 
         * and return the set representative of that set
         * @param x	The element that we are searching for
         * @return	The set representative of the element we are searching for
         */
        public int find(int x) {
            if(p[x] < 0) { // in this case, x is the set representative and we can return
                return x;
            }
            int px = find(p[x]); // get set representative from parent
            p[x] = px; // path compression
            return px;
        }
        
        /**
         * Take the union of two sets
         * Find the sets that both x and y are a part of 
         * Find the set representatives of both of those sets
         * If x and y are pointing at different values...
         * 	-Take the union of the two sets
         * 	-Make the smaller set representative point at the larger set representative
         * If x and y are pointing at the same element...
         * 	-They are a part of the same set
         * 	-Do not perform the union
         * @param x	The first element that we are taking the union of
         * @param y	The second element that we are taking the union of
         * @return	True if we performed the union or false if we didn't
         */
        public boolean union(int x, int y) {
            int px = find(x); // get set representative of x
            int py = find(y); // get set representative of y
            if(px == py) { // if x and y are in the same set, stop
                return false;
            }
            if(p[px] < p[py]) { // if px has more elements than py (remember that the sizes are negative here)
                int save = px;
                px = py;
                py = save;
            }
            p[py] += p[px]; // increase y set size by x set size; 
            p[px] = py; // make x set representative point to y set representative
            return true;
        }
    }
    
	/**
	 * This class handles holding both of the (x,y) coordinates of an edge
	 * as well as the distance between the two edges
	 */
    private class Edge implements Comparable<Edge> {
        double[] node1, node2; // The (x,y) coordinate of the two points in space as well as their integer identifier
        double distance; // the distance between the two nodes
        
        /**
         * Create a node object between two points and calculate their distance
         * @param node1	The first coordinate (x1, y1) and unique integer identifier
         * @param node2	The second coordinate (x2, y2) and unique integer identifier
         */
        public Edge(double[] node1, double[] node2) {
            this.node1 = node1;
            this.node2 = node2;
            this.distance = this.getDistance();
        }
        
        /**
         * Calculate the distance between the two Nodes 
         * Note: we can save a lot of time by only performing the square root 
         * function when we need to (it is an expensive operation) so only 
         * perform that operation in the main code
         * @return	The squared distance between two points
         */
        public double getDistance() {
            return Math.pow((this.node1[0] - this.node2[0]), 2) + Math.pow((this.node1[1] - this.node2[1]), 2);
        }
        
        /**
         * Used for sorting Edges from shortest to longest distance
         */
        public int compareTo(Edge other) {
            return Double.compare(this.distance, other.distance);
        }
    }
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        new Frecles().run();
    }
    
    public void run() throws NumberFormatException, IOException {
    	// Create objects for efficient system in and system out
    	BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st;
        
        // take in the number of test cases
        int numTimes = Integer.parseInt(file.readLine()); 
        while(numTimes-->0) {
            file.readLine();
            
            // take in the number of nodes
            int numNodes = Integer.parseInt(file.readLine()); 
            // create an array of nodes of size numNodes
            double[][] nodes = new double[numNodes][2]; 
            for(int i = 0; i < numNodes; i++) {
                st = new StringTokenizer(file.readLine());
                // read in the (x,y) coordinate and store that in a new Node object in the nodes array
                nodes[i] = new double[] {Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), i}; 
            }
            
            // a set of all possible edges between every given points sorted in shortest to largest distance
            PriorityQueue<Edge> edges = getPermutations(nodes); 

            // Used for making connections between two nodes 
            UnionFind unionFind = new UnionFind(numNodes); 
            // The cost to traverse all nodes
            double cost = 0.0; 
            // The number of edges that have been made (the program will end once we have numNodes-1 edges) 
            int numEdges = 0;
            while(!edges.isEmpty()) {
            	// Retrieve the current Edge
                Edge currEdge = edges.poll();
                double[] node1 = currEdge.node1; // retrieve node 1
                double[] node2 = currEdge.node2; // retrieve node 2
                
                int num1 = (int) node1[2]; // retrieve the identifier for node1
                int num2 = (int) node2[2]; // retrieve the identifier for node2
                
                // if both node1 and node2 are not contained within the same set, 
                // increment the total distance by the current distance
                // else, do nothing
                boolean added = unionFind.union(num1, num2);
                // (save time by only performing the square root function when needed)
                cost += added ? Math.sqrt(currEdge.distance) : 0;
                numEdges += added ? 1 : 0;
                // If we have made all of the connections needed, then stop 
                if(numEdges == numNodes - 1) {
                    break;
                }
            }
            // print the total cost of visiting all nodes
            out.printf("%.2f%n", cost); 
        }
        out.close();
        file.close();
    }
    
    /**
     * Generate s set of all connections between all of the different points
     * @param nodes	All of the different nodes 
     * @return		All of the different Edges between all of the nodes
     */
    public PriorityQueue<Edge> getPermutations(double[][] nodes) {
        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
        // Iterate through every permutation of nodes
        for(int i = 0; i < nodes.length-1; i++) {
            for(int j = i+1; j < nodes.length; j++) {
                double[] node1 = nodes[i];
                double[] node2 = nodes[j];
                edges.add(new Edge(node1, node2));
            }
        }
        return edges;
    }
}