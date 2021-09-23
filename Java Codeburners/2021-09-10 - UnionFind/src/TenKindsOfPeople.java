import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TenKindsOfPeople {
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
	
	public static void main(String[] args) throws IOException {
		new TenKindsOfPeople().run();
	}
	
	public boolean[][] map; // Contains a binary representation of the spaces
	public int maxR, maxC; // The number of rows and number of columns
	
	public void run() throws IOException {
		// Create objects for efficient system in and system out
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(file.readLine());
        
        // Take in the number of rows and number of columns
        maxR = Integer.parseInt(st.nextToken());
        maxC = Integer.parseInt(st.nextToken());
        // Create the map to be of size maxR * maxC
        map = new boolean[maxR][maxC];
        // Iterate through the whole 2D array and assert whether a spot it a one or a zero
        for(int i = 0; i < maxR; i++) {
        	String[] line = file.readLine().split("");
        	for(int j = 0; j < maxC; j++) {
        		map[i][j] = Integer.parseInt(line[j]) == 1;
        	}
        }
        
        // Create two UnionFind objects, one for all of the connecting ones and one for all of the connecting zeros
        UnionFind ones = new UnionFind(maxR * maxC);
        UnionFind zeros = new UnionFind(maxR * maxC);
        // Iterate trough the map and take the union of two similar adjacent connecting nodes in the respective UinionFind object
        for(int i = 0; i < maxR; i++) {
        	for(int j = 0; j < maxC; j++) {
        		if(map[i][j]) { // If the spot is a one
        			this.getConnections(ones, true, i, j);
        		}
        		else { // Else, the spot is a zero
        			this.getConnections(zeros, false, i, j);
        		}
        	}
        }
        
        // Take in the number of test cases
        int numTimes = Integer.parseInt(file.readLine());
        while(numTimes-->0) { // For each test case... 
        	// Take in the start (r1,c1) and the destination (r2,c2)
        	st = new StringTokenizer(file.readLine());
        	int r1 = Integer.parseInt(st.nextToken()) - 1;
        	int c1 = Integer.parseInt(st.nextToken()) - 1;
        	int r2 = Integer.parseInt(st.nextToken()) - 1;
        	int c2 = Integer.parseInt(st.nextToken()) - 1;
        	
        	// Determine if either decimal, binary, or neither groups can make it from (r1,c1) to (r2,c2)
        	out.println(getOutput(zeros, ones, r1, c1, r2, c2));
        }
        
        out.close();
        file.close();
	}
	
	/**
	 * Determine all of the different connections between nodes of the same type
	 * @param connections	The UnionFind object storing the connections
	 * @param type			The type of space that we are trying to match 
	 * @param r				The starting row
	 * @param c				The starting column 
	 */
	public void getConnections(UnionFind connections, boolean type, int r, int c) {
		// All of the displacements for moving North, South, East, and West
		int[][] delta = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		for(int[] d : delta) { // Iterate through each of the displacements
			// Apply the displacement to the current (r,c) coordinate
			int currR = r + d[0]; 
			int currC = c + d[1];
			// If the new (currR,currC) is in bounds and (r,c) and (currR,currC) are the same value...
			if(inBounds(currR, currC) && map[r][c] == map[currR][currC]) {
				// Take the union of the two elements and their sets
				// Note: the conversion from (r,c) to the index for UnionFind.p can be calculated
				// by taking the number of rows times the current row plus the current column
				connections.union(((r * maxC) + c), ((currR * maxC) + currC));
			}
		}
	}
	
	/**
	 * Returns the condition of whether or not a given coordinate (r,c) is in bounds
	 * @param r	The row in question
	 * @param c	The column in question
	 * @return	Whether the coordinate (r,c) is in bounds or not
	 */
	public boolean inBounds(int r, int c) {
		// Return if the given (r,c) coordinate is in bounds
		return r < maxR && r >= 0 && c < maxC && c >= 0;
	}
	
	/**
	 * Determine if the path between (r1,c1) and (r2,c2) can be traversed
	 * by either a binary person, a decimal person, or by neither type
	 * @param zeros	All of the different connecting zero spots (binary)
	 * @param ones	All of the different connecting one spots (decimal)
	 * @param r1	The first row
	 * @param c1	The first column
	 * @param r2	The second row
	 * @param c2	The second column
	 * @return		Who can traverse the path between (r1,c1) and (r2,c2)
	 */
	public String getOutput(UnionFind zeros, UnionFind ones, int r1, int c1, int r2, int c2) {
		// Calculate the index for a given (r,c) coordinate
		int index1 = (r1 * maxC) + c1;
    	int index2 = (r2 * maxC) + c2;
    	
    	// Determine the value of performing the UnionFind.find(index) operation
    	int findZeros1 = zeros.find(index1);
    	int findZeros2 = zeros.find(index2);
    	int findOnes1 = ones.find(index1);
    	int findOnes2 = ones.find(index2);
    	
    	// Determine if there is a path between the two points
    	// 	-If a UnionFind.p[index] is a -1, that means that index is NOT  
    	// 	 a part of that UnionFind object (AKA, a one on the board  will 
    	// 	 return a -1 if we search for it in the zero UnionFind object)
    	// 	-If the two indices are the same, that means that they are both 
    	// 	 pointing to the same set representative, and therefore there is
    	// 	 a connection between the two points
    	boolean binary = (zeros.p[index1] != -1) && (zeros.p[index2] != -1) && (findZeros1 == findZeros2);
    	boolean decimal = (ones.p[index1] != -1) && (ones.p[index2] != -1) && (findOnes1 == findOnes2);
    	
    	// Determine which type of connection can be made
    	String output = "neither";
    	if(binary) {
    		output = "binary";
    	}
    	else if(decimal) {
    		output = "decimal";
    	}
    	
    	return output;
	}
}
