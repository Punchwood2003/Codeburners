import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class WhereToLive {
	private class Point implements Comparable<Point> {
		public long x, y;
		
		public Point(long x, long y) {
			this.x = x;
			this.y = y;
		}
		
		public int compareTo(Point other) {
			int comp = Long.compare(this.x, other.x);
			return comp == 0 ? Long.compare(this.y, other.y) : comp;
		}
	}
	
	public static void main(String[] args) throws IOException {
		new WhereToLive().run();
	}
	
	private double distance(double x1, double y1, double x2, double y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}
	
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		double numPoints = Integer.parseInt(file.readLine());
		double totalX = 0;
		double totalY = 0;
		while(numPoints != 0) {
			for(int i = 0; i < numPoints; i++) {
				StringTokenizer st = new StringTokenizer(file.readLine());
				totalX += Long.parseLong(st.nextToken());
				totalY += Long.parseLong(st.nextToken());
			}
			
			Point[] possible = new Point[4];
			possible[0] = new Point((long) Math.floor(totalX / numPoints), (long) Math.floor(totalY / numPoints));
			possible[1] = new Point((long) Math.floor(totalX / numPoints), (long) Math.ceil(totalY / numPoints));
			possible[2] = new Point((long) Math.ceil(totalX / numPoints), (long) Math.floor(totalY / numPoints));
			possible[3] = new Point((long) Math.ceil(totalX / numPoints), (long) Math.ceil(totalY / numPoints));
			
			Point best = possible[0];
			double x1 = totalX / numPoints;
			double y1 = totalY / numPoints;
			double bestDistance = distance(x1, y1, best.x, best.y);
			for(int i = 1; i < 4; i++) {
				double distance = distance(x1, y1, possible[i].x, possible[i].y);
				if((distance < bestDistance) || (distance == bestDistance && possible[i].compareTo(best) < 0)) {
					best = possible[i];
					bestDistance = distance;
				}
			}
			
			out.printf("%d %d\n", best.x, best.y);
			
			numPoints = Integer.parseInt(file.readLine());
			totalX = 0;
			totalY = 0;
		}
		
		file.close();
		out.close();
	}
}
