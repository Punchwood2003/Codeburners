import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class TrainAddiction {
	public static void main(String[] args) throws IOException {
		new TrainAddiction().run();
	}
	public long MOD = 1_000_000_007;
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int numTimes = Integer.parseInt(file.readLine());
		
		while(numTimes-->0) {
			StringTokenizer st = new StringTokenizer(file.readLine());
			
			int[] occ = new int[51];
			long N = Long.parseLong(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			for(int i = 0; i < M; i++) {
				occ[Integer.parseInt(st.nextToken())]++;
			}
			
			long[][] mat = new long[50][50];
			for(int i = 1; i < occ.length;  i++) {
				mat[mat.length - i][mat.length - 1] = occ[i];
			}
			for(int i = 1; i < mat.length; i++) {
				mat[i][i-1] = 1;
			}
			
			long[] dp = new long[50];
			dp[0] = 1;
			for(int i = 1; i < dp.length; i++) {
				for(int j = 0; j <= i; j++) {
					dp[i] += (dp[i-j] * occ[j]) % MOD;
					dp[i] %= MOD;
				}
			}
			long[][] init = new long[][] {dp};
			long[][] pow = pow(mat, N);
			long[][] ans = mult(init, pow);
			long x = ans[0][0];
			if(x == 0) {
				out.println("IMPOSSIBLE");
			}
			else {
				out.println(x);
			}
		}
		out.close();
	}
	public long[][] pow(long[][] mat, long p) {
		long[][] ans = new long[mat.length][mat.length];
		for(int i = 0; i < ans.length; i++) {
			ans[i][i] = 1;
		}
		// Quick way to get a copy of the matrix
		long[][] curr = mult(ans, mat);
		// Fast expo 
		while(p != 0) {
			if(p % 2 == 1) {
				ans = mult(curr, ans);
			}
			curr = mult(curr, curr);
			p /= 2;
		}
		return ans;
	}
	public long[][] mult(long[][] A, long[][] B) {
		long[][] C = new long[A.length][B[0].length];
		for(int i = 0; i < C.length; i++) {
			for(int j = 0; j < C[0].length; j++) {
				for(int k = 0; k < A[i].length; k++) {
					C[i][j] += A[i][k] * B[k][j];
					C[i][j] %= MOD;
				}
			}
		}
		return C;
	}
}
