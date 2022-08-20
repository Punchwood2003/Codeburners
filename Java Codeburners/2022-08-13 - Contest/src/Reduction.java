import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Reduction {
	class Agency implements Comparable<Agency> {
		public String id;
		public int leastCost, reduceByOneCost, reduceByHalfCost;
		public Agency(String id, int one, int half) {
			this.id = id;
			this.reduceByOneCost = one;
			this.reduceByHalfCost = half;
		}
		public void getLeastCost(int original, int target) {
			int curr = original; 
			int cost = 0;
			while(curr/2 >= target && reduceByHalfCost <= (curr - curr/2) * reduceByOneCost) {
				curr /= 2;
				cost += reduceByHalfCost;
			}
			cost += (curr - target) * reduceByOneCost;
			this.leastCost = cost;
		}
		public int compareTo(Agency other) {
			int comp = Integer.compare(this.leastCost, other.leastCost);
			return comp == 0 ? this.id.compareTo(other.id) : comp;
		}
		public String toString() {
			return this.id + " " + this.leastCost;
		}
	}
	public static void main(String[] args) throws IOException {
		new Reduction().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int numTimes = Integer.parseInt(file.readLine());
		int caseNumber = 1;
		while(numTimes-->0) {
			String[] line = file.readLine().split(" ");
			int N = Integer.parseInt(line[0]);
			int M = Integer.parseInt(line[1]);
			int L = Integer.parseInt(line[2]);
			
			Agency[] agencies = new Agency[L];
			for(int i = 0; i < L; i++) {
				 line = file.readLine().split("[:,]");
				 agencies[i] = new Agency(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]));
				 agencies[i].getLeastCost(N, M);
			}
			
			Arrays.sort(agencies);
			out.println("Case " + (caseNumber++));
			for(int i = 0; i < L; i++) {
				out.println(agencies[i]);
			}
		}
		out.close();
	}
}
