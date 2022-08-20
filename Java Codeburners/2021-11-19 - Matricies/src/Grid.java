import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Grid {
	private class Point {
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	} 
	public static void main(String[] args) throws IOException {
		new Grid().run();
	}
	public int[][] translations = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	public int n, m;
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		int[][] moves = new int[n][m];
		int[][] distance = new int[n][m];
		for(int i = 0; i < n; i++) {
			String line = file.readLine();
			for(int j = 0; j < m; j++) {
				moves[i][j] = line.charAt(j)-48;
				distance[i][j] = -1;
			}
		}
		distance[0][0] = 0;
		Point start = new Point(0, 0);
		Queue<Point> toSearch = new LinkedList<Point>();
		toSearch.add(start);
		while(!toSearch.isEmpty()) {
			Point curr = toSearch.poll();
			int currR = curr.r;
			int currC = curr.c;
			int multiplier = moves[currR][currC];
			for(int[] translate : translations) {
				int tempR = currR + (translate[0] * multiplier);
				int tempC = currC + (translate[1] * multiplier);
				if(inBounds(tempR, tempC) && distance[tempR][tempC] == -1) {
					toSearch.add(new Point(tempR, tempC));
					distance[tempR][tempC] = distance[currR][currC] + 1;
				}
			}
		}
		out.println(distance[n-1][m-1]);
		out.close();
		file.close();
	}
	public boolean inBounds(int currR, int currC) {
		return (0 <= currR) && (currR < n) && (0 <= currC) && (currC < m);
	}
}