import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LinearRecurrence {
	public static void main(String[] args) throws IOException {
		new LinearRecurrence().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int N = Integer.parseInt(file.readLine());
		int[] a = new int[N+1];
		StringTokenizer st = new StringTokenizer(file.readLine());
		for(int i = 0; i <= N; i++) {
			a[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(file.readLine());
		ArrayList<Integer> x = new ArrayList<Integer>();
		for(int i = 0; i < N; i++) {
			x.add(Integer.parseInt(st.nextToken()));
		}
		int numTimes = Integer.parseInt(file.readLine());
		while(numTimes-->0) {
			st = new StringTokenizer(file.readLine());
			int T = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			x.set(T, getTthIndex(a, x, T, N, M));
			System.out.println(x.get(T));
		}
	}
	public int getTthIndex(int[] a, ArrayList<Integer> x, int T, int N, int mod) {
		int x_T = a[0] % mod;
		for(int i = 1; i < N; i++) {
			x_T += (a[i] * x.get(T-i));
		}
		return x_T;
	}
}
