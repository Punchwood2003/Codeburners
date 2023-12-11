import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ModularArithmetic {
	public static void main(String[] args) throws IOException {
		new ModularArithmetic().run();
	}

	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		while(true) {
			StringTokenizer st = new StringTokenizer(file.readLine());
			long n = Long.parseLong(st.nextToken());
			long t = Long.parseLong(st.nextToken());

			if(n == 0 && t == 0) {
				break;
			}

			while(t-->0) {
				st = new StringTokenizer(file.readLine());
				long a = Long.parseLong(st.nextToken());
				char op = st.nextToken().charAt(0);
				long b = Long.parseLong(st.nextToken());

				switch(op) {
				case '+': {
					out.println(((a%n) + (b%n))%n);
					break;
				}
				case '-': {
					out.println(((a%n) - (b%n) + n)%n);
					break;
				}
				case '*': {
					out.println(((a%n) * (b%n))%n);
					break;
				}
				case '/': {
					b = modInverse(b, n);
					if(b == -1) {
						out.println(-1);
					} else {
						out.println(((a%n) * b)%n);
					}
					break;
				}
				}
			}
		}

		file.close();
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
 
    public long modInverse(long A, long M) {
        long g = gcdExtended(A, M);
        if (g != 1) {
            return -1;
        }
        else {
            // m is added to handle negative x
            return (x % M + M) % M;
        }
    }
}
