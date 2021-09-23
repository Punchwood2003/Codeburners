import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class BridgesAndTunnels {
	/**
	 * This class handles two actions:
	 *	-Taking the Union of two sets
	 * 	-Finding the set in which an element is a part of
	 */
	private static class UnionFind {
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
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // Create a way to translate unique string id's into integer indices 
        TreeMap<String, Integer> nodeNames = new TreeMap<String, Integer>();
        // The max number of unique String identifications is 200000
        UnionFind unionFind = new UnionFind(200000);
        // The smallest unused integer index
        int availableNode = 1;
        // The number of connections
        int n = scan.nextInt(); scan.nextLine();
        while(n-->0) {
        	// Take in the two connecting nodes
            String node1 = scan.next();
            String node2 = scan.next();
            // The two node's indices in the UnionFind's p[] 
            int numRepresentation1 = -1;
            int numRepresentation2 = -1;
            // If we don't already have an entry for the first node
            if(!nodeNames.containsKey(node1)) {
            	// Create an entry using the smallest available index and increment the index
                nodeNames.put(node1, availableNode++);
            }
            // If we don't already have an entry for the second node
            if(!nodeNames.containsKey(node2)) {
            	// Create an entry using the smallest available index and increment the index
            	nodeNames.put(node2, availableNode++);
            }
            // Retrieve the integer equivalents of the Strings
            numRepresentation1 = nodeNames.get(node1);
            numRepresentation2 = nodeNames.get(node2);
            // Connect the two nodes
            unionFind.union(numRepresentation1, numRepresentation2);
            // Find the set representative of the now combined sets
            int index = unionFind.find(numRepresentation1);
            // Print the number of elements in the set of connecting nodes
            System.out.println(unionFind.p[index] * -1);
        }
        scan.close();
    }
}