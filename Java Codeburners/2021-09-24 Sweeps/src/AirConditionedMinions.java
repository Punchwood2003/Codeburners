import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer; 

/**
 * Class that allows us to pair a minion to its specific temperatures
 */
class Minion implements Comparable<Minion> {
	// The starting end ending temperatures of a minion
	public int start, end;
	
	/**
	 * Create a Minion object that likes temperatures within the interval [start,end]
	 * @param start	The lower bound of the interval
	 * @param end	The upper bound of the interval
	 */
	public Minion(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int compareTo(Minion other) {
		return this.end - other.end;
	}
}

/**
 * Main class
 */
public class AirConditionedMinions {
	// A list of all of the minions
	ArrayList<Minion> minions;
	
	public static void main(String[] args) throws IOException {
		new AirConditionedMinions().run();
	}
	
	public void run() throws IOException {
		// Efficient System in and out
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st;
		
		// Take in the number of minions
		int numMinions = Integer.parseInt(file.readLine());
		// Create an ArrayList of the minions
		minions = new ArrayList<Minion>();
		
		// Read in the temperature ranges
		while(numMinions-->0) {
			// Read in the starting and ending temperatures
			st = new StringTokenizer(file.readLine());
			int startTemperature = Integer.parseInt(st.nextToken());
			int endTemperature = Integer.parseInt(st.nextToken());
			// Create the current Minion
			Minion currMinion = new Minion(startTemperature, endTemperature);
			// Add the current Minion to the list
			minions.add(currMinion);
		}
		
		// Sort the list of Minions
		Collections.sort(minions);
		// The number of rooms that need to be formed
		int numRooms = 1;
		// Grab the first minion
		Minion currMinion = minions.get(0);
		// Iterate through each of the minions
		for(int i = 1; i < minions.size(); i++) {
			Minion testMinion = minions.get(i);
			if(testMinion.start > currMinion.end) {
				numRooms++;
				currMinion = testMinion;
			}
		}
		out.println(numRooms);
		out.close();
		file.close();
	}
}