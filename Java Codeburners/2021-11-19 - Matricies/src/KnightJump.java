import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class KnightJump {
    private class Point {
        public int r, c;
        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    } 
    public static void main(String[] args) throws IOException {
        new KnightJump().run();
    }
    public int[][] translations = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
    public int n;
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(file.readLine());
        n = Integer.parseInt(st.nextToken());
        boolean[][] board = new boolean[n][n];
        int[][] distance = new int[n][n];
        int startR = -1, startC = -1;
        for(int i = 0; i < n; i++) {
            String line = file.readLine();
            for(int j = 0; j < n; j++) {
                char curr = line.charAt(j);
                board[i][j] = (curr != '#');
                distance[i][j] = -1;
                if(curr == 'K') {
                    startR = i;
                    startC = j;
                }
            }
        }
        distance[startR][startC] = 0;
		Point start = new Point(startR, startC);
		Queue<Point> toSearch = new LinkedList<Point>();
		toSearch.add(start);
		while(!toSearch.isEmpty()) {
			Point curr = toSearch.poll();
			int currR = curr.r;
			int currC = curr.c;
			for(int[] translate : translations) {
				int tempR = currR + translate[0];
				int tempC = currC + translate[1];
				if(inBounds(tempR, tempC) && board[tempR][tempC] && distance[tempR][tempC] == -1) {
					toSearch.add(new Point(tempR, tempC));
					distance[tempR][tempC] = distance[currR][currC] + 1;
				}
			}
		}
        out.println(distance[0][0]);
        out.close();
        file.close();
    }
    public boolean inBounds(int currR, int currC) {
        return (0 <= currR) && (currR < n) && (0 <= currC) && (currC < n);
    }
}