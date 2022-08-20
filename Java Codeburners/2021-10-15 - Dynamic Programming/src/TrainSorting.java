import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TrainSorting {
	public static void main(String[] args) throws IOException {
		new TrainSorting().run();
	}
	
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		int numCars = Integer.parseInt(file.readLine());
		int[] cars = new int[numCars];
		for(int i = 0; i < numCars; i++) {
			cars[i] = Integer.parseInt(file.readLine());
		}
		int[] dp = new int[numCars];
		Arrays.fill(dp, 1);
		for(int i = 0; i < numCars; i++) {
			for(int j = 0; j < numCars; j++) {
				if(i > j && cars[i] > cars[j])
				dp[i] = Math.max(dp[i], dp[j] + 1);
			}
		}
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < numCars; i++) {
			max = Math.max(max, dp[i]);
		}
		System.out.println(max);
	}
}
