import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Scenes {
	public static void main(String[] args) throws IOException {
		new Scenes().run();
	}
	static Long[][] dp;
	static int N, W, H;
	static long MOD = 1_000_000_007;
	
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		N = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		dp = new Long[W+1][N+1];
		out.println(solve());
		out.close();
		file.close();
	}
	public long solve() {
		int ribbonSquares = Math.min(W*H, N);
		int plains = (ribbonSquares / W) + 1;
		return (((f(1, N) - plains) + MOD) % MOD);
	}
	public long f(int w, int ribbon) {
		if(ribbon < 0) {
			return 0;
		}
		if(w > W) {
			return 1;
		}
		if(dp[w][ribbon] != null) {
			return dp[w][ribbon];
		}
		long scenes = 0L;
		for(int len = 0; len <= H; len++) {
			scenes = (scenes + f(w + 1, ribbon - len));
		}
		return dp[w][ribbon] = scenes % MOD;
	}
}
