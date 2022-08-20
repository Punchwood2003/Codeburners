import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

public class KnotKnowledge {
	public static void main(String[] args) throws IOException {
		new KnotKnowledge().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int numKnots = Integer.parseInt(file.readLine());
		StringTokenizer st = new StringTokenizer(file.readLine());
		HashSet<Integer> knots = new HashSet<Integer>();
		for(int i = 0; i < numKnots; i++) {
			knots.add(Integer.parseInt(st.nextToken()));
		}
		st = new StringTokenizer(file.readLine());
		for(int i = 0; i < numKnots - 1; i++) {
			knots.remove(Integer.parseInt(st.nextToken()));
		}
		for(Integer temp : knots) {
			out.println(temp);
		}
		out.close();
		file.close();
	}
}
