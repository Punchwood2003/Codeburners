import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author MatthewSheldon
 */
public class UnDetected {
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
	 * This class handles both the Circles (sensors) and the lines (boundaries)
	 * This class also determines if two circles intersect one another or if a
	 * circle intersects one of the left or right boundaries
	 */
	private class Shape {
		boolean isCircle; // Is the object we are creating a circle or a boundary
		int x, y, r; // If we are a circle, we need to keep track of (x, y) and r, else, just keep track of x
		
		/**
		 * Create a circle at (x, y) with radius r
		 * @param x	The x coordinate of the circle 
		 * @param y	The y coordinate of the circle
		 * @param r	The radius of the circle
		 */
		public Shape(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
			this.isCircle = true;
		}
		
		/**
		 * Create a boundary (line) in the form x = ... 
		 * @param x	The x location of the left or right boundary
		 */
		public Shape(int x) {
			this.x = x;
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
				if(other.x == 0) { // If the other Shape is the left boundary
					return this.x - this.r <= 0;
				}
				else { // The other Shape is the right boundary
					return this.x + this.r >= 200;
				}
			}
			else if (other.isCircle) { // The other shape is the circle
				if(this.x == 0) { // If the current Shape is the left boundary
					return other.x - other.r <= 0;
				}
				else { // The current shape is the right boundary
					return other.x + other.r >= 200;
				}
			}
			else { // Both shapes are boundaries (they will never intersect)
				return false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new UnDetected().run(); 
	}
	
	public void run() throws IOException {
		// Create objects for efficient system in and system out
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st;
        
        // Take in the number of sensors
        int numSensors = Integer.parseInt(file.readLine());
        // Create a UnionFind object that will keep track of connecting sensors
        unionFind = new UnionFind(numSensors+2);
        
        // Create the ArrayList of Shapes
        Shape[] sensors = new Shape[numSensors+2];
        // Create the left boundary shape
        sensors[0] = new Shape(0);
        // Create the right boundary shape
        sensors[numSensors+1] = new Shape(200);
        
        // The highest value k such that if sensors i...k were turned on we would still be able to get from the top to the bottom
        int output = -1;
        int i;
        // Iterate through all of the sensors
        for(i = 1; i <= numSensors; i++) {
        	// Take in the current line and store it in the StringTokenizer
        	st = new StringTokenizer(file.readLine());
        	// Add the new Circle Shape into the ArrayList of sensors
        	sensors[i] = new Shape(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        	
        	if(pathExists(sensors, i)) { // If we can still get from the top to the bottom after adding the current sensor 
        		continue; 
        	}
        	else { // We can no longer get from the top to the bottom
        		output = i - 1; // Therefore, this is the largest value of k that we can use to get from the top to the bottom
        		break; // We don't need to read in or do anything else past this point
        	}
        }
        if(output < 0) { // If we were able to successfully add all of the sensors and still get from top to bottom
        	output = i; // Then k = number of sensors
        }
        out.println(output); // Print the value of k
        out.close();
        file.close();
	}
	
	UnionFind unionFind;
	/**
	 * Determines if we can get from top to bottom by determining if the left boundary 
	 * has a path to the right boundary AKA there is a wall of sensors preventing us 
	 * from getting from the top to the bottom
	 * @param sensors	The current list of Shapes 
	 * @return			Whether a path from the top to the bottom exists
	 */
	public boolean pathExists(Shape[] sensors, int index) {
		Shape leftBoundary = sensors[0];
		Shape rightBoundary = sensors[sensors.length-1];
		Shape current = sensors[index];
		// If the current element intersects with the left boundary
		if(leftBoundary.intersect(current)) {
			unionFind.union(0, index);
		}
		// Iterate through all of the new pairs of connections by activating the kth sensor=
		for(int i = 1; i < index; i++) {
			Shape other = sensors[i];
			if(current.intersect(other)) {
				unionFind.union(i, index);
			}
		}
		// If the current element intersects with the right boundary
		if(rightBoundary.intersect(current)) {
			unionFind.union(sensors.length-1, index);
		}
		
		int find1 = unionFind.find(0); // Retrieve the set representative of the left boundary 
		int find2 = unionFind.find(sensors.length-1); // Retrieve the set representative of the right boundary
		return find1 != find2; // If the two are pointing at the same set representative, then they are connected; therefore, a path does not exist
	}
}
