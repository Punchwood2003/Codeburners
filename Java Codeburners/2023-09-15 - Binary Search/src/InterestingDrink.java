import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class InterestingDrink {
	public static void main(String[] args) throws IOException {
		new InterestingDrink().run();
	}
	
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		int n = Integer.parseInt(file.readLine());
		StringTokenizer st = new StringTokenizer(file.readLine());
		int[] nums = new int[n];
		for(int i = 0; i < n; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(nums);
		
		int q = Integer.parseInt(file.readLine());
		StringBuilder sb = new StringBuilder();
		while(q-->0) {
			int m = Integer.parseInt(file.readLine());
			
			if(m < nums[0]) {
				sb.append("0\n");
				continue;
			} else if(m >= nums[n-1]) {
				sb.append(String.format("%d\n", n));
				continue;
			}
			
			int low = 0, high = n;
			while(low < high) {
				int mid = (low + high) / 2;
				int curr = nums[mid];
				if(curr <= m) {
					low = mid + 1;
				} else {
					high = mid;
				}
			}
			
			sb.append(String.format("%d\n", low));
		}
		
		file.close();
		out.println(sb);
		out.close();
	}
}
