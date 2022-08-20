import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Porpises {
	public static void main(String[] args) throws IOException {
		new Porpises().run();
	}
	public long MOD = 1000000000;
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int numTimes = Integer.parseInt(file.readLine());
		
		long[][] fib = new long[][] {{1,1},{1,0}};
		
		while(numTimes-->0) {
			StringTokenizer st = new StringTokenizer(file.readLine());
			int K = Integer.parseInt(st.nextToken());
			long P = Long.parseLong(st.nextToken());
			long[][] pow = pow(fib, P-1);
			out.println(K + " " + pow[0][0]);
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
