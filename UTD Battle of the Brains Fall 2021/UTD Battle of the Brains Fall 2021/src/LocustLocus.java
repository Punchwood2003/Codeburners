import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class LocustLocus {
	public static void main(String[] args) throws IOException {
		new LocustLocus().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int numPairs = Integer.parseInt(file.readLine());
		int ans = Integer.MAX_VALUE;
		for(int i = 0; i < numPairs; i++) {
			StringTokenizer st = new StringTokenizer(file.readLine());
			int baseYear = Integer.parseInt(st.nextToken());
			int c1 = Integer.parseInt(st.nextToken());
			int c2 = Integer.parseInt(st.nextToken());
			ans = Math.min(ans, baseYear + lcm(c1, c2));
		}
		out.println(ans);
		out.close();
		file.close();
	}
	public int lcm(int num1, int num2) {
	    if (num1 == 0 || num2 == 0) {
	        return 0;
	    }
	    int absHigherNumber = Math.max(num1, num2);
	    int absLowerNumber = Math.min(num1, num2);
	    int lcm = absHigherNumber;
	    while (lcm % absLowerNumber != 0) {
	        lcm += absHigherNumber;
	    }
	    return lcm;
	}
}
