import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class GrazedGrains {
	public static void main(String[] args) throws IOException {
        new GrazedGrains().run();
    }
	class Circle {
		int x, y, r;
		public Circle(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}
		public boolean intersetcts(Circle other) {
			double distBetweenCenters = Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
			return this.r + other.r >= distBetweenCenters;
		}
	}
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int numCircles = Integer.parseInt(file.readLine());
        Circle[] circles = new Circle[numCircles];
        for(int i = 0; i < numCircles; i++) {
        	StringTokenizer st = new StringTokenizer(file.readLine());
        	int x = Integer.parseInt(st.nextToken());
        	int y = Integer.parseInt(st.nextToken());
        	int r = Integer.parseInt(st.nextToken());
        	circles[i] = new Circle(x, y, r);
        }
        double area = 0.0;
        for(Circle c : circles) {
        	area += Math.PI * Math.pow(c.r, 2);
        }
        for(int i = 0; i < numCircles-1; i++) {
        	Circle c1 = circles[i];
        	for(int j = 0; j < numCircles; j++) {
        		Circle c2 = circles[j];
        		if(c1.intersetcts(c2)) {
        			double a = c1.r;
        			double b = c2.r;
        			double c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        			double theta1 = Math.asin(b/c);
        			double theta2 = Math.asin(a/c);
        			double area1 = (1/2) * (a*a) * (theta1 - Math.sin(theta1));
        			double area2 = (1/2) * (b*b) * (theta2 - Math.sin(theta2));
        			area -= (area1 + area2);
        		}
        	}
        }
        out.println(area);
        out.close();
        file.close();
    }
}
