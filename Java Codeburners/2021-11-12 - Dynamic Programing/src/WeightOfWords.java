import java.util.Scanner;

public class WeightOfWords {
	public static void main(String[] args) {
		new WeightOfWords().run();
	}
	public void run() {
		Scanner scan = new Scanner(System.in);
		int L = scan.nextInt();
		int W = scan.nextInt();
		String[][] dp = new String[L+1][W+1];
		dp[0][0] = "";
		for(int i = 0; i < L+1; i++) {
			for(int j = 0; j < W+1; j++) {
				if(dp[i][j] != null) {
					for(int letter = 1; letter <= 26; letter++) {
						int newRow = i+1;
						int newCol = j+letter;
						String newStr = dp[i][j] + (char) ('a' + letter - 1);
						if(newRow < dp.length && newCol < dp[newRow].length) {
							dp[newRow][newCol] = newStr;
						}
					}
				}
			}
		}
		String ans = dp[L][W];
		if(ans == null) {
			System.out.println("impossible");
		}
		else {
			System.out.println(ans);
		}
	}
}
