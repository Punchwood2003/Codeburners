import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TreeSet;

public class KingdomOfAnts {
	class Pair implements Comparable<Pair> {
		public int x, y;
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int compareTo(Pair other) {
			if(this.x < other.x) {
				return -1;
			} else if(this.x > other.x) {
				return 1;
			} else {
				if(this.y < other.y) {
					return -1;
				} else if(this.y > other.y) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new KingdomOfAnts().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		int numShapes = Integer.parseInt(file.readLine());
		TreeSet<Pair> visited = new TreeSet<Pair>();
		int count = 0;
		for(int i = 0; i < numShapes; i++) {
			String[] line = file.readLine().split(" ");
			int x1 = Integer.parseInt(line[0]);
			int y1 = Integer.parseInt(line[1]);
			int x2 = Integer.parseInt(line[2]);
			int y2 = Integer.parseInt(line[3]);
			int deltaX = x2 - x1;
			int deltaY = y2 - y1;
			if(deltaX < 0 || deltaY < 0) {
				int temp = x2;
				x2 = x1;
				x1 = temp;
				temp = y2;
				y2 = y1;
				y1 = y2;
			}
			
			for(int x = x1; x <= x2; x++) {
				for(int y = y1; y <= y2; y++) {
					Pair tempPair = new Pair(x, y);
					if(!visited.contains(tempPair)) {
						count++;
						visited.add(tempPair);
					}
				}
			}
		}
		out.println(count);
		out.close();
	}
}
