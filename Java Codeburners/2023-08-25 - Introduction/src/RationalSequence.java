import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class RationalSequence {
	public static void main(String[] args) throws IOException {
		new RationalSequence().run();
	}
	
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int numTimes = Integer.parseInt(file.readLine());
		
		while(numTimes-->0) {
			StringTokenizer st = new StringTokenizer(file.readLine());
			int k = Integer.parseInt(st.nextToken());
			String[] pq = st.nextToken().split("/");
			int p = Integer.parseInt(pq[0]);
			int q = Integer.parseInt(pq[1]);
			
			int depth = Math.floorDiv(p, q);
			p -= depth * q;
			q -= p;
			p += q;
			q += depth * p;
			
			out.printf("%d %d/%d\n", k, p, q);
		}
		
		file.close();
		out.close();
	}
}
