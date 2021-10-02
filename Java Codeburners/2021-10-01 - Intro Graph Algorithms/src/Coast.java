import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Coast {
	public static void main(String[] args) throws IOException {
		new Coast().run();
	}
	public int count;
	public int N, M;
	public int[][] transformations = {{-1, 0},{1, 0},{0, -1},{0, 1}};
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		boolean[][] land = new boolean[N+2][M+2];
		for(int i = 1; i < N+1; i++) {
			String line = file.readLine();
			for(int j = 1; j < M+1; j++) {
				if(line.charAt(j-1) == '1') {
					land[i][j] = true;
				}
			}
		}
		boolean[][] visited = new boolean[N+2][M+2];
		bfs(land, visited, 0, 0);
		out.println(count);
		out.close();
		file.close();
	}
	public void bfs(boolean[][] land, boolean[][] visited, int currR, int currC) {
		if(land[currR][currC]) {
			count++;
			return;
		}
		visited[currR][currC] = true;
		for(int[] transform : transformations) {
			int tempR = currR + transform[0];
			int tempC = currC + transform[1];
			if(inBounds(tempR, tempC) && !visited[tempR][tempC]) {
				bfs(land, visited, tempR, tempC);
			}
		}
	}
	public boolean inBounds(int currR, int currC) {
		return (0 <= currR) && (currR < N+2) && (0 <= currC) && (currC < M+2);
	}
}
