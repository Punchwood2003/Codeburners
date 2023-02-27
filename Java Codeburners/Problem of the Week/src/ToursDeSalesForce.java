import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ToursDeSalesForce {
	public static void main(String[] args) throws IOException {
		new ToursDeSalesForce().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st;
		
		// Part 1: Take in Input
		int numDistricts = Integer.parseInt(file.readLine());
		District[] firedDistricts = new District[numDistricts/2];
		District[] safeDistricts = new District[numDistricts/2];
		for(int i = 0; i < numDistricts; i++) {
			st = new StringTokenizer(file.readLine());
			int numPoints = Integer.parseInt(st.nextToken());
			
			// If the district is one of the first half of the districts, then it is to be removed
			if(i < numDistricts/2) {
				firedDistricts[i] = new District(numPoints);
				while(numPoints-->0) {
					firedDistricts[i].addPoint(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
				}
				firedDistricts[i].calculateEdges();
			} else {
				safeDistricts[i % (numDistricts/2)] = new District(numPoints);
				while(numPoints-->0) {
					safeDistricts[i % (numDistricts/2)].addPoint(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
				}
				safeDistricts[i % (numDistricts/2)].calculateEdges();
			}
		}
		file.close();
		
		// Part 2: Calculate All costs possible
		// Fired District -> Edge -> Safe District -> Edge -> Distance
		HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>> masterMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>>();
		// For every district that is fired
		for(int i = 0; i < numDistricts/2; i++) {
			District firedDistrict = firedDistricts[i];
			Edge[] firedEdges = firedDistrict.getEdges();
			HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> map1 = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
			// For every edge within that district
			for(int j = 0; j < firedEdges.length; j++) {
				Edge firedEdge = firedEdges[j];
				HashMap<Integer, HashMap<Integer, Double>> map2 = new HashMap<Integer, HashMap<Integer, Double>>();
				// For every district that is safe
				for(int k = 0; k < numDistricts/2; k++) {
					District safeDistrict = safeDistricts[k];
					Edge[] safeEdges = safeDistrict.getEdges();
					HashMap<Integer, Double> map3 = new HashMap<Integer, Double>();
					// For every edge within that district
					for(int l = 0; l < safeEdges.length; l++) {
						Edge safeEdge = safeEdges[l];
						double minDistance = this.getMinDistanceBetweenEdges(firedEdge, safeEdge);
						map3.put(l, minDistance);
						map2.put(k, map3);
						map1.put(j, map2);
						masterMap.put(i, map1);
					}
				}
			}
		}
		
		out.println(this.makeMapReadable(masterMap));
		out.close();
	}
	
	public double getMinDistanceBetweenEdges(Edge edge1, Edge edge2) {
		return Math.min(Edge.getDistanceOfEdge(edge1.point1, edge2.point1) + Edge.getDistanceOfEdge(edge1.point2, edge2.point2), Edge.getDistanceOfEdge(edge1.point1, edge2.point2) + Edge.getDistanceOfEdge(edge1.point2, edge2.point1));
	}
	
	public String makeMapReadable(HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>> masterMap) {
		StringBuilder sb = new StringBuilder();
		
		for(Integer firedDistrictNumber : masterMap.keySet()) {
			sb.append("Fired District ");
			sb.append(firedDistrictNumber);
			sb.append("\n");
			HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> map1 = masterMap.get(firedDistrictNumber);
			for(Integer firedEdgeNumber : map1.keySet()) {
				sb.append("\tFired Edge ");
				sb.append(firedEdgeNumber);
				sb.append("\n");
				HashMap<Integer, HashMap<Integer, Double>> map2 = map1.get(firedEdgeNumber);
				for(Integer safeDistrictNumber : map2.keySet()) {
					sb.append("\t\tSafe District ");
					sb.append(safeDistrictNumber);
					sb.append("\n");
					HashMap<Integer, Double> map3 = map2.get(safeDistrictNumber);
					for(Integer safeEdgeNumber : map3.keySet()) {
						sb.append("\t\t\tSafe Edge ");
						sb.append(safeEdgeNumber);
						sb.append("\n");
						sb.append("\t\t\t\tCost: ");
						sb.append(map3.get(safeEdgeNumber));
						sb.append("\n");
					}
				}
			}
		}
		
		return sb.toString();
	}
}


class District {
	private Point[] points;
	private Edge[] edges;
	private int nextIndex;
	private int numPoints;
	
	public District(int numPoints) {
		this.numPoints = numPoints;
		this.points = new Point[numPoints];
		this.edges = new Edge[numPoints];
		this.nextIndex = 0;
	}
	
	public void addPoint(double x, double y) {
		points[nextIndex++] = new Point(x, y);
	}
	
	public void calculateEdges() {
		for(int i = 0; i < numPoints-1; i++) {
			edges[i] = new Edge(points[i], points[i+1]);
		}
		edges[numPoints-1] = new Edge(points[numPoints-1], points[0]);
	}
	
	public Point[] getPoints() {
		return points;
	}
	
	public Edge[] getEdges() {
		return edges;
	}
}

class Edge {
	public Point point1, point2;
	
	public Edge(Point p1, Point p2) {
		this.point1 = p1;
		this.point2 = p2;
	}
	
	public static double getDistanceOfEdge(Point p1, Point p2) {
		return Math.sqrt(Point.getDistanceSquaredTo(p1, p2));
	}
	
	public String toString() {
		return String.format("\tPoint 1: %s\n\tPoint2: %s\n\tDistance: %.6f", this.point1, this.point2, this.getDistanceOfEdge());
	}
}

class Point implements Comparable<Point> {
	private double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static double getDistanceSquaredTo(Point p1, Point p2) {
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
	}
	
	public int compareTo(Point other) {
		int comp = Double.compare(this.x, other.x);
		return comp == 0 ? Double.compare(this.y, other.y) : comp;
	}
	
	public String toString() {
		return String.format("(%f, %f)", this.x, this.y);
	}
}
