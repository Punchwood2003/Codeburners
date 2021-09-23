import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GettingThrough {
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
	 * This class handles both the Circles (obstacles) and the lines (boundaries)
	 * This class also determines if two circles intersect one another or if a
	 * circle intersects one of the left or right boundaries
	 */
	private class Shape {
		boolean isCircle; // Is the object we are creating a circle or a boundary
		double x, y, r; // If we are a circle, we need to keep track of (x, y) and r, else, just keep track of x
		double w; // This is the width of the hall
		double dr; // This is the value of delta r 
		
		/**
		 * Create a circle at (x, y) with radius r
		 * @param x	The x coordinate of the circle 
		 * @param y	The y coordinate of the circle
		 * @param r	The radius of the circle
		 */
		public Shape(double x, double y, double r, double w, double dr) {
			this.x = x;
			this.y = y;
			this.r = r;
			this.w = w;
			this.dr = dr; 
			this.isCircle = true;
		}
		
		/**
		 * Create a boundary (line) in the form x = ... 
		 * @param x	The x location of the left or right boundary
		 */
		public Shape(double x, double w, double dr) {
			this.x = x;
			this.w = w;
			this.dr = dr;
			isCircle = false;
		}
		
		/**
		 * Determine if two different shapes intersect each other
		 * If the two shapes are both circles...
		 * 	-Check to see if the distance between the two centers is 
		 * 	 less than or equal to the combination of the two radii
		 * If one of the two shapes is a line...
		 *  -Determine if the line is the left or the right bound 
		 * 	-Depending on which boundary it is, determine if the 
		 * 	 radius of the circle touches the boundary or not
		 * @param other	The other shape that we checking
		 * @return		Whether or not the two shapes intersect each other at any point
		 */
		public boolean intersect(Shape other) {
			if(this.isCircle && other.isCircle) { // If both shapes are circles
				// Calculate the distance between their centers
				double d = Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2)); 
				// Determine if the two radii combined are greater than or equal to the distance between the centers
				return this.r + other.r >= d;
			}
			else if(this.isCircle) { // If the current Shape is the circle
				if(other.x == this.dr) { // If the other Shape is the left boundary
					return this.x - this.r <= this.dr;
				}
				else { // The other Shape is the right boundary
					return this.x + this.r >= this.w - this.dr;
				}
			}
			else if (other.isCircle) { // The other shape is the circle
				if(this.x == this.dr) { // If the current Shape is the left boundary
					return other.x - other.r <= this.dr;
				}
				else { // The current shape is the right boundary
					return other.x + other.r >= this.w - this.dr;
				}
			}
			else { // Both shapes are boundaries 
				return false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new GettingThrough().run(); 
	}
	
	public void run() throws IOException {
		// Create objects for efficient system in and system out
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st;
        
        // Read in the number of test cases
        int numTimes = Integer.parseInt(file.readLine());
        while(numTimes-->0) {
        	// Take in the width of the hallway 
        	int w = Integer.parseInt(file.readLine());
        	// Take in the number of circles
        	int numCircles = Integer.parseInt(file.readLine());
        	
        	// Create an array of Shapes that represent the left and right boundary as well as the circles 
        	Shape[] original = new Shape[numCircles + 2];
        	// Make the left boundary 
        	original[0] = new Shape(0, w, 0);
        	// Make the right boundary
        	original[numCircles + 1] = new Shape(w, w, 0);
        	// Iterate through all of the circles
        	for(int i = 1; i <= numCircles; i++) {
        		st = new StringTokenizer(file.readLine());
        		// Create the circle object 
        		original[i] = new Shape(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), w, 0);
        	}
        	// If there is already no way to get through the hallway, then output 0
        	if(!pathExists(original)) {
        		out.println(0);
        	}
        	// Else, we binary search for the correct radius
        	else {
        		out.println(output(binarySearch(original, w)));
        	}
        }
        out.close();
	}
	
	/**
	 * Perform a binary search for the most optimal radius of a circle that still allows it
	 * to traverse through the hallway of obstacles. Each time that we perform a query as to
	 * whether or not our test radius is correct, we need to update the radii of the circle 
	 * obstacles as well as where our boundaries are. Essentially, by increasing the radius of 
	 * the existing circles and moving our boundaries inward by the same value, we can effectively
	 * find the largest value that the radius of this circle can be without trying to traverse an
	 * actual circle through the hallway. 
	 * @param original	The unaltered obstacles in the hallway
	 * @param w			The width of the hallway
	 * @return			The largest value of a radius of a circle such that it can traverse 
	 * 					through the hallway without getting stuck at one of the obstacles
	 */
	public double binarySearch(Shape[] original, double w) {
		// The largest value that the radius can be is half of the entire hallway's width
		double high = w / 2.0;
		// The smallest value that the radius can be is zero
		double low = 0;
		// Traditional mid value (this represents the current delta r that we are testing)
		double mid = (high + low) / 2.0;
		
		// Create the left and right boundary objects
		Shape leftBoundary = new Shape(0, w, mid);
		Shape rightBoundary = new Shape(w, w, mid);
		// Greedily search for the best delta radius (AKA the radius of the circle that we are looking for) 
		double bestDeltaR = Double.MIN_VALUE;
		
		// 32 was the minimum number of iterations needed to successfully solve the final test case.
		// Normally this would be some arbitrary value like 100 or 200 - just enough iterations to 
		// get close enough to the precision that we are looking for. 
		for(int i = 0; i < 32; i++) {
			// Recalculate mid
			mid = (high + low) / 2.0;
			// Create a new Shape array that will reflect the current delta r that we are testing
			Shape[] testShape = new Shape[original.length];
			// Make the left and right boundaries (note that their location should move with the changes in delta r)
			testShape[0] = new Shape(leftBoundary.x + mid, w, mid);
			testShape[testShape.length-1] = new Shape(rightBoundary.x - mid, w, mid);
			// Make changes to all of the circle objects
			for(int j = 1; j < testShape.length-1; j++) {
				Shape originalShape = original[j];
				// Change them to have their radius be their old radius plus delta r
				testShape[j] = new Shape(originalShape.x, originalShape.y, originalShape.r + mid, w, mid);
			}
			// Returns true if there is no connection between the left and right walls, 
			// AKA a path exists from top to bottom
			boolean testPass = pathExists(testShape); 
			// If a path exists
			if(testPass) {
				// Then greedily change the value of bestDeltaR to be the 
				// max value between the previous best delta r and the current delta r 
				bestDeltaR = Math.max(bestDeltaR, mid);
				// Set low equal to mid because we might be able to get a larger value of delta r
				low = mid;
			}
			// If a path doesn't exist...
			else {
				// Then we need a smaller value of delta r to test
				high = mid;
			}
		}
		// Return the largest radius of a circle that can pass through the hallway of obstacles
		return bestDeltaR;
	}
	
	/**
	 * Determines if we can get from top to bottom by determining if the left boundary 
	 * has a path to the right boundary AKA there is a wall of sensors preventing us 
	 * from getting from the top to the bottom
	 * @param sensors	The current list of Shapes 
	 * @return			Whether a path from the top to the bottom exists
	 */
	public boolean pathExists(Shape[] circles) {
		// Create a UnionFind object that will keep track of connecting sensors
		UnionFind unionFind = new UnionFind(circles.length);
		// Iterate through all of the two-pair permutations of Shapes
		for(int i = 0; i < circles.length - 1; i++) {
        	Shape node1 = circles[i]; // Retrieve the first Shape 
        	for(int j = i + 1; j < circles.length; j++) {
        		Shape node2 = circles[j]; // Retrieve the second shape
        		if(node1.intersect(node2)) { // If the two intersect each other
        			unionFind.union(i, j); // Then take the union of the two elements and the sets they are a part of already
        		}
        	}
        }
		int find1 = unionFind.find(0); // Retrieve the set representative of the left boundary 
		int find2 = unionFind.find(circles.length-1); // Retrieve the set representative of the right boundary
		return find1 != find2; // If the two are pointing at the same set representative, then they are connected; therefore, a path does not exist
	}
	
	/**
	 * Output up to 6 decimal places of precision (but only as many as are needed) 
	 * @param d	The floating point value 
	 * @return	The precise floating point value
	 */
	public String output(double d) {
		String output = "";
		int i;
		// Print up to 6 decimal places of precision
		String temp = String.format("%.6f", d);
		for(i = temp.length()-1; i >= 0; i--) {
			// Once we hit a number that isn't zero, we know we have our last significant digit
			if(temp.charAt(i) != '0') {
				break;
			}
		}
		// Truncate the six precision string up to the needed percision
		output = temp.substring(0, i+1);
		return output; 
	}
}
