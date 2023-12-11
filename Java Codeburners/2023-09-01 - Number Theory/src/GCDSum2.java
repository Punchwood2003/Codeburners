import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class GCDSum2 {
	public static void main(String[] args) throws IOException {
		new GCDSum2().run();
	}

	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		long n = Long.parseLong(file.readLine());
		file.close();
		
		BigInteger sum = BigInteger.ZERO;
		for(long i = 1; i <= n; i++) {
			for(long j = i+1; j <= n; j++) {
				sum = sum.add(BigInteger.valueOf(gcdExtended(i,j)));
			}
		}
		
		out.println(sum);
		out.close();
	}
	
	private long x, y;
	private long gcdExtended(long a, long b) {
 
        // Base Case
        if (a == 0) {
            x = 0;
            y = 1;
            return b;
        }
 
        // To store results of recursive call
        long gcd = gcdExtended(b % a, a);
        long x1 = x;
        long y1 = y;
 
        // Update x and y using results of recursive
        // call
        long tmp = b / a;
        x = y1 - tmp * x1;
        y = x1;
 
        return gcd;
    }
}
