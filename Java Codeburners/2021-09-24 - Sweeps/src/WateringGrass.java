import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class WateringGrass {
	// The length of the strip and the height of the strip
	public double fieldLength, fieldHeight;

	/**
	 * Class that will handle a given sprinkler
	 */
	class Sprinkler implements Comparable<Sprinkler> {
		// The (x,y) coordinate, the radius of the circle
		// The leftBound indicates the leftmost x-coordinate of the strip that the sprinkler covers
		// The rightBound indicated the rightmost x-coordinate of the strip that the sprinkler covers.
		public double x, y, r, leftBound, rightBound;

		/**
		 * Create a Sprinkler Object with at the coordinate (x,y) and radius of r
		 * @param x	The x coordinate
		 * @param y	The y coordinate
		 * @param r	The radius
		 */
		public Sprinkler(double x, double y, double r) {
			this.x = x;
			this.y = y;
			this.r = r;
			// Calculate the right and left bounds of the circle on the strip
			this.getBounds();
		}

		/**
		 * Efficiently calculates the right and left bounds of the circle on the strip
		 */
		public void getBounds() {
			// Because Math.sqrt() is a costly function, 
			// only use it once and store the result in a temporary variable
			double temp = Math.sqrt(Math.pow(this.r, 2) - Math.pow(fieldHeight/2, 2));
			// Calculate the right bound
			this.rightBound = temp + this.x;
			// Calculate the left bound
			this.leftBound = this.x - temp;
		}

		@Override
		public int compareTo(Sprinkler other) {
			// Determine which sprinkler comes first by decreasing left bound
			return  Double.compare(this.leftBound, other.leftBound);
		}
	}

	public static void main(String[] args) throws IOException {
		new WateringGrass().run();
	}

	public void run() throws IOException {
		// Efficient System in and System out 
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st;

		// Read in the line
		String line = file.readLine();
		// Keep reading in lines until we run out of lines to read
		while(line != null) {
			st = new StringTokenizer(line);
			// Take in the number of sprinklers on the strip
			int numSprinklers = Integer.parseInt(st.nextToken());
			// Take in the length of the strip
			fieldLength = Integer.parseInt(st.nextToken());
			// Take in the height of the strip 
			fieldHeight = Integer.parseInt(st.nextToken());
			// Determine the y position of every sprinkler
			double center = fieldHeight / 2.0;
			// Create an array of sprinkler
			Sprinkler[] sprinklers = new Sprinkler[numSprinklers];
			// Read in each sprinkler
			for(int i = 0; i < numSprinklers; i++) {
				st = new StringTokenizer(file.readLine());
				sprinklers[i] = new Sprinkler(Integer.parseInt(st.nextToken()), center, Integer.parseInt(st.nextToken()));
			}

			// Sort the sprinklers first by lowest leftBound and second by largest rightBound
			// This will give us a list of ordered sprinklers where we have the leftmost sprinkler
			// that covers the most distance on the strip
			Arrays.sort(sprinklers);

			// This variable describes what the current maximum acceptable leftBound 
			// is that we can accept without causing a gap
			double maxAcceptableLeftBound = 0;
			// This variable describes the current rightmost coverage 
			// indicating values [0,rightMostCoverage] have been covered
			double rightMostCoverage = 0;
			// The number of sprinklers that we have used
			int numSprinklersUsed = 0;
			// The ith sprinkler that we are evaluating
			int i = 0;

			// Until we have covered the entire length of the field...
			while(rightMostCoverage < fieldLength) {
				// Create a variable that describes the current iteration's best rightBound
				double newRightMostCoverage = maxAcceptableLeftBound;
				// Indicates which ith sprinkler is the best to add
				int farthest = -1;
				// Find the sprinkler with the farthest rightmost coverage without creating a gap
				for(; i < sprinklers.length; i++) {
					// If adding the current sprinkler would create a gap, then 
					// we cannot search any further for this current iteration
					if(sprinklers[i].leftBound > maxAcceptableLeftBound) {
						break;
					}
					// If the current sprinkler's rightBound gives us the most coverage, then update it
					if(sprinklers[i].rightBound >= newRightMostCoverage) {
						newRightMostCoverage = sprinklers[i].rightBound;
						// Update which element is the best to add 
						farthest = i;
					}
				}
				// If no sprinkler was able to be made, that means that there is a 
				// gap to the left and therefore we cannot cover the entire strip
				if(farthest == -1) {
					break;
				}
				// Increment the number of sprinklers that were used
				numSprinklersUsed++;
				// Update the current rightMostCoverage and maxAcceptableLeftBound to 
				// be equal to the most optimal sprinkler's right bound
				rightMostCoverage = maxAcceptableLeftBound = newRightMostCoverage;
			}
			// If we haven't covered the entire strip by the time that we are finished, then output -1
			if(rightMostCoverage < fieldLength) {
				out.println(-1);
			}
			// Else, we covered the entire strip; therefore, print the number of sprinklers needed to do so
			else {
				out.println(numSprinklersUsed);
			}
			line = file.readLine();
		}
		out.close();
		file.close();
	}
}
