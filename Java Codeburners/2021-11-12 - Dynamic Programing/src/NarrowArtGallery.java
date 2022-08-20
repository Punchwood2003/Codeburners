import java.io.IOException;
import java.util.Scanner;

public class NarrowArtGallery {
	public static void main(String[] args) throws IOException {
		new NarrowArtGallery().run();
	}
	public void run() throws IOException {
		Scanner file = new Scanner(System.in);
		while(true) {
			int N = file.nextInt();
			int K = file.nextInt();
			if(N == 0 && K == 0) {
				return;
			}
			int sum = 0;
			int[][] hall = new int[N][2];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < 2; j++) {
					hall[i][j] = file.nextInt();
					sum += hall[i][j];
				}
			}
			// row, prev, k
			int[][][] dp = new int[N+1][3][K+1]; 
			for(int i = 0; i < dp.length; i++) {
				for(int j = 0; j < dp[i].length; j++) {
					for(int k = 0; k < dp[i][j].length; k++) {
						dp[i][j][k] = Integer.MAX_VALUE / 8;
					}
				}
			}
			
			// Base cases
			dp[0][0][0] = 0;
			dp[0][1][0] = 0;
			dp[0][2][0] = 0;
			
			for(int h = 1; h < dp.length; h++) {
				for(int k = 0; k <= K; k++) {
					/*
					 * State Convention:
					 * 	0: totally free
					 * 	1: Left blocked
					 * 	2: right blocked
					 */
					
					// Case where totally free
					int candidate1 = dp[h-1][0][k];
					int candidate2 = dp[h-1][1][k];
					int candidate3 = dp[h-1][2][k];
					dp[h][0][k] = Math.min(candidate1, Math.min(candidate2, candidate3));
					
					if(k != 0) {
						// Case where the left blocked off
						candidate1 = dp[h-1][0][k-1];
						candidate2 = dp[h-1][1][k-1];
						dp[h][1][k] = Math.min(candidate1, candidate2) + hall[h-1][0];
						
						// Case where the right blocked off
						candidate1 = dp[h-1][0][k-1];
						candidate2 = dp[h-1][2][k-1];
						dp[h][2][k] = Math.min(candidate1, candidate2) + hall[h-1][1];
					}
				}
			}
			int min = Math.min(Math.min(dp[dp.length - 1][0][K], dp[dp.length - 1][1][K]), dp[dp.length - 1][2][K]);
			System.out.println(sum - min);
		}
	}
}
