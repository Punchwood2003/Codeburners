import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

public class KhadgarBFS {
	public static void main(String[] args) throws IOException {
		new KhadgarBFS().run();
	}
	/* Time Save: Cut down on how long it takes to find out that it doesn't exist */
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		String start = file.readLine();
		char[] startBits = start.toCharArray();
		String goal = file.readLine();
		char[] goalBits = goal.toCharArray();
		System.out.println(this.bfs(startBits, goalBits) ? "Khadgar can transform " + start + " to " + goal : "Khadgar cannot transform " + start + " to " + goal);
		file.close();
		out.close();
	}
	private boolean bfs(char[] startBits, char[] goalBits) {
		if(Arrays.compare(startBits, goalBits) == 0) {
			return true;
		}
		Queue<Query> toSearch = new LinkedList<Query>();
		toSearch.add(new Query(new TreeSet<Transformation>(), startBits));
		while(!toSearch.isEmpty()) {
			Query currQuery = toSearch.poll();
			char[] currBits = currQuery.bits;
			//System.out.println(currQuery + "\n");
			if(currQuery.equals(goalBits)) {
				return true;
			}
			ArrayList<Transformation> currTransformations = this.getAllPossibleTransformationsForCurrentState(currBits);
			for(Transformation t : currTransformations) {
				if(!currQuery.contains(t)) {
					toSearch.add(currQuery.request(t));
				}
			}
		}
		return false;
	}
	/* O(N) */
	private ArrayList<Transformation> getAllPossibleTransformationsForCurrentState(char[] bits) {
		ArrayList<Transformation> allTransformations = new ArrayList<Transformation>();
		char[][] transformationToBePerformed = {{'0', '0', '1'}, {'1', '0', '0'}, {'0', '1', '1'}, {'1', '1', '0'}};
		for(int i = 0; i < bits.length-2; i++) {
			if(bits[i] == '1' && bits[i+1] == '0' && bits[i+2] == '0') {
				allTransformations.add(new Transformation(i, transformationToBePerformed[0]));
			} else if(bits[i] == '0' && bits[i+1] == '0' && bits[i+2] == '1') {
				allTransformations.add(new Transformation(i, transformationToBePerformed[1]));
			} else if(bits[i] == '1' && bits[i+1] == '1' && bits[i+2] == '0') {
				allTransformations.add(new Transformation(i, transformationToBePerformed[2]));
			} else if(bits[i] == '0' && bits[i+1] == '1' && bits[i+2] == '1') {
				allTransformations.add(new Transformation(i, transformationToBePerformed[3]));
			}
		}
		//System.out.println(allTransformations);
		return allTransformations;
	}
}
class Query {
	public TreeSet<Transformation> previousTransformations;
	public char[] bits;
	public Query(TreeSet<Transformation> p, char[] b) {
		this.previousTransformations = p;
		this.bits = b;
	}
	public boolean contains(Transformation t) {
		return this.previousTransformations.contains(t);
	}
	public Query request(Transformation t) {
		TreeSet<Transformation> newPreviousTransformations = new TreeSet<Transformation>(this.previousTransformations);
		newPreviousTransformations.add(t);
		return new Query(newPreviousTransformations, this.transform(t));
	}
	/* O(N) */
	private char[] transform(Transformation t) {
		int index = t.index;
		char[] transformation = t.transformation;
		char[] newBits = new char[this.bits.length];
		for(int i = 0; i < index; i++) {
			newBits[i] = this.bits[i];
		}
		for(int i = index; i <= index+2; i++) {
			newBits[i] = transformation[i-index];
		}
		for(int i = index+3; i < newBits.length; i++) {
			newBits[i] = this.bits[i];
		}
		return newBits;
	}
	/* O(N) */ 
	public boolean equals(char[] goalBits) {
		for(int i = 0; i < this.bits.length; i++) {
			if(this.bits[i] != goalBits[i]) {
				return false;
			}
		}
		return true;
	}
	public String toString() {
		return "Current bits: " + Arrays.toString(this.bits) + 
				"\nTransformations used: " + this.previousTransformations;
	}
}
class Transformation implements Comparable<Transformation> {
	public int index;
	public char[] transformation;
	public Transformation(int i, char[] t) {
		this.index = i;
		this.transformation = t;
	}
	/* O(1) */ 
	public int compareTo(Transformation other) {
		if(Arrays.compare(this.transformation, other.transformation) == 0 && Integer.compare(this.index, other.index) == 0) {
			return 0;
		}
		int comp = Arrays.compare(this.transformation, other.transformation);
		if(comp == 0) {
			comp = Integer.compare(this.index, other.index);
		}
		return comp;
	}
	public String toString() {
		return "From index [" + this.index + ", " + (this.index + 2) + "] transforming to " + Arrays.toString(this.transformation);
	}
}
