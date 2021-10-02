import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Alehouse {
	/**
	 * Class that handles different key times
	 */
	public class Time implements Comparable<Time> {
		// What the current time is
		public BigInteger time;
		
		// Whether or not the current time is the start to an interval
		public boolean isStart;
		
		/**
		 * Construct a Time object that has a value of t and is either defined as being 
		 * a start or end time dependent on the value of i
		 * @param t	The time that this object represents
		 * @param i	Whether this current time is the start to an interval or not
		 */
		public Time(BigInteger t, boolean i) {
			this.time = t;
			this.isStart = i;
		}
		
		@Override
		public int compareTo(Time other) {
			// Compare the two times
			int comp = this.time.compareTo(other.time);
			// If they are the same numeric time
			if(comp == 0) {
				// If both are the same type of time, then they are completely identical
				if(this.isStart == other.isStart) {
					comp = 0;
				}
				// We want the start times to come sooner than the end ones
				else if(this.isStart) {
					comp = -1;
				}
				// This means that the other time is the start to an interval
				else {
					comp = 1;
				}
			}
			return comp;
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Alehouse().run();
	}
	
	public void run() throws IOException {
		// Efficient System in and out 
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		
		// Take in the number of people that the alehouse sees in a week
		int numPeople = Integer.parseInt(st.nextToken());
		// Take in the number of milliseconds that we are allowed to be in the alehouse
		BigInteger k = new BigInteger(st.nextToken());
		// Create an array of interesting time points
		Time[] times = new Time[numPeople*2];
		int i = 0;
		// For each person in the input
		while(numPeople-->0) {
			st = new StringTokenizer(file.readLine()); 
			// Take in the start time 
			BigInteger start = new BigInteger(st.nextToken());
			// Take in the end 
			BigInteger end = new BigInteger(st.nextToken());
			// Add increase the interval by k milliseconds
			end = end.add(k);
			
			// Create the starting Time object
			Time time1 = new Time(start, true);
			// Create the ending Time object
			Time time2 = new Time(end, false);
			// Add the start time to the array
			times[i++] = time1;
			// Add the ending time to the array
			times[i++] = time2;
		}
		// Sort the times array
		Arrays.sort(times);
		// A counter of the current number of people in the Alehouse
		int currNumPeople = 0;
		// Variable that keeps track of the solution (greedy)
		long solution = Long.MIN_VALUE;
		// Iterate through each of the interesting times
		for(i = 0; i < times.length; i++) {
			//Retrieve the current time
			Time curr = times[i];
			// If it is the start to an interval, then a new person has entered the alehouse
			if(curr.isStart) {
				// Increment the number of people currently in the alehouse
				currNumPeople++;
			}
			// Else, then that means someone is leaving
			else {
				// Greedily store the current maximum number of people that have been in the alehouse at any point in time
				solution = Math.max(solution, currNumPeople);
				// Remove the person that just left the alehouse
				currNumPeople--;
			}
		}
		// Print the maximum number of people in the alehouse at any given time
		out.println(solution);
		out.close();
		file.close();
	}
}
