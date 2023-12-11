import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FizzBuzz {
	public static void main(String[] args) throws IOException {
		new FizzBuzz().run();
	}
	
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		
		for(int i = 1; i <= n; i++) {
			boolean flag1 = i % x == 0;
			boolean flag2 = i % y == 0;
			if(flag1 && flag2) {
				out.println("FizzBuzz");
			} else if(flag1) {
				out.println("Fizz");
			} else if(flag2) {
				out.println("Buzz");
			} else {
				out.println(i);
			}
		}
		
		out.close();
	}
}
