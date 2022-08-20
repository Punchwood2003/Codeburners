import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HouseRobber {
	public static void main(String[] args) throws IOException {
		new HouseRobber().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(st.hasMoreTokens()) {
			list.add(Integer.parseInt(st.nextToken()));
		}
		Integer[] nums = new Integer[list.size()];
		nums = list.toArray(nums);
		out.println(rob(nums));
		out.close();
		file.close();
	}
	public int rob(Integer[] nums) {
		int ans = 0;
		int[] dp = new int[nums.length];
		dp[0] = nums[0];
		if(nums.length > 1) {
			dp[1] = Math.max(nums[0], nums[1]);
			if(nums.length > 2) {
				for(int i = 2; i < nums.length; i++) {
					dp[i] = Math.max(dp[i-2] + nums[i], dp[i-1]);
				}
				return dp[nums.length-1];
			}
			return dp[1];
		}
		return nums[0];
	}
}
