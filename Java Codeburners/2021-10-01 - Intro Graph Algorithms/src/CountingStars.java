import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CountingStars {
	public static void main(String[] args) throws IOException {
		new CountingStars().run();
	}
	public boolean[][] visited; 
	public boolean[][] stars;
	public int[][] transformations = {{-1, 0},{1, 0},{0, -1},{0, 1}};
	public int N, M;
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		String line = file.readLine();
		int numCase = 1;
		while(line != null) {
			StringTokenizer st = new StringTokenizer(line);
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			stars = new boolean[N][M];
			visited = new boolean[N][M];
			
			for(int i = 0; i < N; i++) {
				String temp = file.readLine();
				for(int j = 0; j < M; j++) {
					if(temp.charAt(j) == '-') {
						stars[i][j] = true;
					}
				}
			}
			
			int count = 0;
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < M; j++) {
					if(stars[i][j] && !visited[i][j]) {
						count++;
						dfs(i, j);
					}
				}
			}
			out.println("Case " + numCase + ": " + count);
			
			numCase++;
			line = file.readLine();
		}
		
		out.close();
		file.close();
	} 
	public void dfs(int currR, int currC) {
		visited[currR][currC] = true;
		for(int[] transform : transformations) {
			int tempR = currR + transform[0];
			int tempC = currC + transform[1];
			if(inBounds(tempR, tempC) && stars[tempR][tempC] && !visited[tempR][tempC]) {
				dfs(tempR, tempC);
			}
		}
	}
	public boolean inBounds(int currR, int currC) {
		return (0 <= currR) && (currR < N) && (0 <= currC) && (currC < M);
	}
}
