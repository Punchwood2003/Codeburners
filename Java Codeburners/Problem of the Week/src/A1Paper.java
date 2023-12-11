/*
BEGIN ANNOTATION
PROBLEM URL: https://open.kattis.com/problems/a1paper
TAGS: 2D Arrays, Floating Point Precision, Greedy, Ad Hoc
EXPLANATION:
 - Compute the size of all relevant paper prior to checking if a solution exists
 - We know that at the very least, we need 2 pieces of A2 paper.
 - Worst case scenario, if we don't have any of the current paper type, we always need 2 times the amount of paper that was previously needed
 - Better case, if we do actually have some paper of the current type, we can subtract that amount from the running total
 - We know that a solution exists if we have enough of the current type of paper to "fill in the gaps"
 - We can calculate the total amount of tape needed by "being optimistic" about what lies in the future. If we 
   simply multiply the long side of the current paper size, by the current amount of paper needed divided by 2, 
   then we know exactly how much tape will be needed at some point, even if we don't yet have that piece of paper made
END ANNOTATION
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class A1Paper {

	public static void main(String[] args) throws IOException {
		new A1Paper().run();
	}

	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		int min = Integer.parseInt(file.readLine()) - 1;
		long[] numPapers = Arrays.asList(file.readLine().split(" ")).stream().map(str -> Long.parseLong(str)).mapToLong(Long::longValue).toArray();
		file.close();

		double[][] paperDimensions = new double[min][2];
		paperDimensions[0][1] = Math.pow(2, -5.0/4.0);
		paperDimensions[0][0] = Math.pow(2, -3.0/4.0);
		for(int i = 1; i < min; i++) {
			paperDimensions[i][0] = paperDimensions[i-1][1];
			paperDimensions[i][1] = paperDimensions[i-1][0] / 2.0;
		}

		boolean possible = false;
		long numPapersOfCurrentSizeNeeded = 2;
		double totalTapeUsed = 0;
		for(int i = 0; i < min; i++) {
			totalTapeUsed += paperDimensions[i][0] * (numPapersOfCurrentSizeNeeded >> 1);
			if(numPapers[i] >= numPapersOfCurrentSizeNeeded) {
				possible = true;
				break;
			}
			numPapersOfCurrentSizeNeeded -= numPapers[i];
			numPapersOfCurrentSizeNeeded <<= 1;
		}

		out.println(possible ? String.format("%.11f", totalTapeUsed) : "impossible");
		out.close();
	}
}
