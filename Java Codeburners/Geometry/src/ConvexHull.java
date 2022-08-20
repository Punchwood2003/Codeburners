import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ConvexHull {
	class CartesianCoordinate implements Comparable<CartesianCoordinate> {
		public double x, y;
		public CartesianCoordinate(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public int compareTo(CartesianCoordinate other) {
			int comp = Double.compare(this.y, other.y);
			return comp == 0 ? Double.compare(this.x, other.x) : comp;
		}
		public PolarCoordinate getPolarCoordinate() {
			return new PolarCoordinate(this.x, this.y);
		}
		public void translate(double dx, double dy) {
			this.x -= dx;
			this.y -= dy;
		}
	}
	class PolarCoordinate implements Comparable<PolarCoordinate> {
		public double r, theta; 
		public PolarCoordinate(double x, double y) {
			this.r = Math.sqrt(x * x + y * y);
			this.theta = Math.atan2(y, x);
		}
		public PolarCoordinate(CartesianCoordinate p) {
			this.r = Math.sqrt(p.x * p.x + p.y * p.y);
			this.theta = Math.atan2(p.y, p.x);
		}
		public int compareTo(PolarCoordinate other) {
			int comp = Double.compare(this.theta, other.theta);
			return comp == 0 ? Double.compare(this.r, other.r) : comp;
		}
		public CartesianCoordinate getCartesianCoordinate() {
			return new CartesianCoordinate(this.r * Math.cos(this.theta), this.r * Math.sin(this.theta));
		}
	}
	public static void main(String[] args) throws IOException {
		new ConvexHull().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in)); 
		PrintWriter out = new PrintWriter(System.out);
		int n = file.read
	}
	public Stack<PolarCoordinate> basicGrahamScan(ArrayList<CartesianCoordinate> cartesianCoordinates) {
		Collections.sort(cartesianCoordinates);
		CartesianCoordinate p0 = cartesianCoordinates.get(0);
		double dx = p0.x;
		double dy = p0.y;
		translate(cartesianCoordinates, dx, dy);
		
	}
	public void translate(ArrayList<CartesianCoordinate> coordinates, double dx, double dy) {
		for(int i = 0; i < coordinates.size(); i++) {
			coordinates.get(i).translate(dx, dy);
		}
	}
	public ArrayList<PolarCoordinate> convertToPolarFromCartesian(List<CartesianCoordinate> cartesianCoordinates) {
		ArrayList<PolarCoordinate> polarCoordinates = new ArrayList<PolarCoordinate>();
		for(int i = 0; i < cartesianCoordinates.size(); i++) {
			polarCoordinates.add()
		}
		return polarCoordinates; 
	}
}

