/*
BEGIN ANNOTATION
PROBLEM URL: https://open.kattis.com/problems/digicomp2
TAGS: topological-sort
EXPLANATION: 
rather than simulating what would happen if we dropped
a marble one by one, we can instead create the graph 
described, perform a topological sort to determine switches
where all marbles present have have been accounted for, 
use the parity of the number of marbles to determine if the
state of the switch needs to be toggled, and then split the
marbles to its children depending on the original orientation 
of the switch. Additionally an initial search for switches 
with in-degree 0 other than the switch where marbles are 
initially dropped needs to be done in order to allow their 
children to be searched during the final topological sort.
END ANNOTATION
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class DigiComp2 {
	public boolean[] directions;
	public long[] marbles;
	public int[] inDegree, leftIDs, rightIDs;
	public Queue<Integer> toSearch;

	public static void main(String[] args) throws IOException {
		new DigiComp2().run();
	}

	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		String[] line = file.readLine().split(" ");
		long numMarbles = Long.parseLong(line[0]);
		int numSwitches = Integer.parseInt(line[1]);

		// True for Right, R, and false for Left, L
		directions = new boolean[numSwitches + 1];
		// The number of marbles that will pass through this node
		marbles = new long[numSwitches + 1];
		// The connecting node to the left of this node
		leftIDs = new int[numSwitches + 1];
		// The connecting node to the right of this node
		rightIDs = new int[numSwitches + 1];
		// The number of nodes that connect to this node
		inDegree = new int[numSwitches + 1];

		// Read in all of the connections and initial directions
		for(int i = 1; i <= numSwitches; i++) {
			line = file.readLine().split(" ");
			directions[i] = line[0].equals("R");
			leftIDs[i] = Integer.parseInt(line[1]);
			rightIDs[i] = Integer.parseInt(line[2]);

			// Increase the in-degree of each of the children of this node
			inDegree[leftIDs[i]]++;
			inDegree[rightIDs[i]]++;
		}

		// Set the in-degree of the bottom of the machine to -1
		inDegree[0] = -1;

		// A list of nodes to search where all predecessors have already been searched
		toSearch = new LinkedList<Integer>();
		
		/*
		 * Complaint: There can be switches other than the top
		 * node that have an in-degree = 0. This means that we have
		 * to artificially reduce their children's in-degree by 1
		 * because they will never be searched by their parent.
		 * <<Only needed for test cases 14 and above>>
		 */
		for(int i = 2; i <= numSwitches; i++) {
			if(inDegree[i] == 0) {
				toSearch.offer(i);
			}
		}
		this.topologicalSort(true);
		
		
		// Add the initial node to that list
		toSearch.offer(1);
		// Assign all of the marbles to that node
		marbles[1] = numMarbles;
		// Run the actual topological sort now
		this.topologicalSort(false);


		// Print out each of the final conditions
		for(int i = 1; i <= numSwitches; i++) {
			out.print(directions[i] ? "R" : "L");
		}

		// Close the output
		out.close();
	}

	public void topologicalSort(boolean skipRootSwitch) {
		// Until all nodes have been processed...
		while(!toSearch.isEmpty()) {
			// Retrieve the ID, direction, left and right children, and number 
			// of marbles that will pass through each of this node's children
			int ID = toSearch.poll();
			boolean dir = directions[ID];
			int leftID = leftIDs[ID];
			int rightID = rightIDs[ID];
			long div = marbles[ID] / 2L;

			// If there are an even number of marbles passing through this node...
			if(marbles[ID] % 2L == 0L) {
				// The direction of this node will be unchanged
				// Assign additional marbles to each of this node's children
				// equal to exactly half of what this node had
				marbles[leftID] += div;
				marbles[rightID] += div;
			} else {
				// ...otherwise, there are an odd number of marbles passing through this node
				// The direction of this node will switch
				directions[ID] = !dir;
				// If this node was originally pointing to the right...
				if(dir) {
					// Add floor(div) marbles to the left child
					marbles[leftID] += div;
					// Add ceil(div) marbles to the right child
					marbles[rightID] += div + 1L;
				} else {
					// ...otherwise, this node was originally pointing to the left
					// Add ceil(div) marbles to the left child
					marbles[leftID] += div + 1L;
					// Add floor(div) marbles to the right child
					marbles[rightID] += div;
				}
			}

			// If we are not weeding out artificial in-degree 0s, or the left child was not the end
			if(!skipRootSwitch || leftID != 0) {
				inDegree[leftID]--;
				// ...and now has in-degree of 0...
				if(inDegree[leftID] == 0) {
					// Add the left child to be searched
					toSearch.offer(leftID);
				}
			}
			// If we are not weeding out artificial in-degree 0s, or the right child was not the end
			if(!skipRootSwitch || rightID != 0) {
				inDegree[rightID]--;
				// ...and now has in-degree of 0...
				if(inDegree[rightID] == 0) {
					// Add the right child to be searched
					toSearch.offer(rightID);
				}
			} 
		}
	}
}