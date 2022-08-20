import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

public class Knapsack {
    public static void main(String[] args) throws IOException {
        new Knapsack().run();
    }
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        StringTokenizer st;
        String line = file.readLine();
        while(line != null) {
            st = new StringTokenizer(line);
            int capacity = Integer.parseInt(st.nextToken());
            int numItems = Integer.parseInt(st.nextToken());
            int[] weights = new int[numItems+1];
            int[] values = new int[numItems+1];
            for(int i = 1; i <= numItems; i++) {
                st = new StringTokenizer(file.readLine());
                values[i] = Integer.parseInt(st.nextToken());
                weights[i] = Integer.parseInt(st.nextToken());
            }
            // The +1's come from the idea of having 0 capacity and the empty set as a row and collumn
            int[][] dp = new int[numItems+1][capacity+1];
            for(int i = 0; i <= numItems; i++) {
                for(int j = 0; j <= capacity; j++) {
                	// Initialize the base case
                	if(i == 0 || j == 0) {
                		dp[i][j] = 0;
                	}
                	else if(j - weights[i] >= 0) {
                        dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weights[i]] + values[i]);
                    }
                    else {
                        dp[i][j] = dp[i-1][j];
                    }
                }
            }
            Stack<Integer> trace = new Stack<Integer>();
            int maxValue = dp[numItems][capacity];
            int i = numItems;
            int j = capacity;
            while(maxValue != 0) {
                if(dp[i][j] != dp[i-1][j]) {
                    maxValue -= values[i];
                    trace.add(i-1);
                    j -= weights[i];
                }
                i--;
            }
            out.println(trace.size());
            while(!trace.isEmpty()) {
                out.print(trace.pop() + " ");
            }
            out.println();
            line = file.readLine();
        }
        out.close();
        file.close();
    }
}