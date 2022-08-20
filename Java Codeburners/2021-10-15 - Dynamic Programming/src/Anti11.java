import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Anti11 {
	public static void main(String[] args) throws IOException {
		new Anti11().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int mod = (1000000007);
		int numTimes = Integer.parseInt(file.readLine());
		ArrayList<Integer> nums = new ArrayList<Integer>();
		int maxLength = Integer.MIN_VALUE;
		while(numTimes-->0) {
			int num = Integer.parseInt(file.readLine());
			nums.add(num);
			maxLength = Math.max(maxLength, num);
		}
		int[] dp = new int[maxLength+1];
		dp[0] = 1;
		dp[1] = 2;
		for(int i = 2; i < maxLength+1; i++) {
			dp[i] = (dp[i-1] + dp[i-2]) % mod; 
		}
		for(int num : nums) {
			out.println(dp[num]);
		}
		out.close();
		file.close();
	}
}
