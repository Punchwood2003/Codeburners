import java.io.IOException;
import java.io.PrintWriter;

public class Hello {
	public static void main(String[] args) throws IOException {
		new Hello().run();
	}
	
	public void run() throws IOException {
		// BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		out.println("Hello World!");
		out.close();
	}
}
