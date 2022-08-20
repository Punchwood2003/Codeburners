import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Squawk {
	public static void main(String[] args) throws IOException {
		new Squawk().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(file.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int s = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());
		long[][] adj = new long[n][n];
		for(int k = 0; k < m; k++) {
			st = new StringTokenizer(file.readLine());
			int i = Integer.parseInt(st.nextToken());
			int j = Integer.parseInt(st.nextToken());
			adj[i][j] = adj[j][i] = 1;
		}
		long[][] pow = pow(adj, t);
		long sum = 0;
		for(int i = 0; i < adj.length; i++) {
			sum += pow[s][i];
		}
		System.out.println(sum);
	}
	public long[][] pow(long[][] mat, int p) {
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
				}
			}
		}
		return C;
	}
}
