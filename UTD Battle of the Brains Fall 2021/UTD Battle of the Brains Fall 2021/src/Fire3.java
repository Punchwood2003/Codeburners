import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Fire3 {
	class Joe {
		int r, c;
		TreeSet<Fire> fireSpaces;
		public Joe(int i, int j, TreeSet<Fire> f) {
			this.r = i;
			this.c = j;
			this.fireSpaces = f;
		}
	} 
	class Fire implements Comparable<Fire> {
		int r, c;
		public Fire(int r, int c) {
			this.r = r;
			this.c = c;
		}
		public int compareTo(Fire other) {
			int comp = Integer.compare(this.r, other.r);
			if(comp == 0) {
				comp = Integer.compare(this.c, other.c); 
			}
			return comp;
		}
	}
	public enum Space {
		FIRE, WALL, SPACE;
	}
	public static void main(String[] args) throws IOException {
		new Fire3().run();
	}
	public int[] dy = new int[] {-1, 0, 1, 0};
	public int[] dx = new int[] {0, -1, 0, 1};
	public Space[][] maze;
	public int[][] distance;
	public int r, c;
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		maze = new Space[r][c];
		distance = new int[r][c];
		TreeSet<Fire> fire = new TreeSet<Fire>();
		int rJ = 0, cJ = 0;
		for(int i = 0; i < r; i++) {
			String line = file.readLine();
			for(int j = 0; j < c; j++) {
				Space curr = Space.SPACE;
				if(line.charAt(j) == '#') {
					curr = Space.WALL;
				}
				else if(line.charAt(j) == 'F') {
					curr = Space.FIRE;
					fire.add(new Fire(i, j));
				}
				else if(line.charAt(j) == 'J') {
					rJ = i;
					cJ = j;
				}
				maze[i][j] = curr;
				distance[i][j] = -1;
			}
		}
		distance[rJ][cJ] = 0;
		int[] dy = new int[] {-1, 0, 1, 0};
		int[] dx = new int[] {0, -1, 0, 1};
		Queue<Joe> toSearch = new LinkedList<Joe>();
		toSearch.add(new Joe(rJ, cJ, fire));
		boolean exit = false;
		while(!toSearch.isEmpty()) {
			Joe curr = toSearch.poll();
			int currR = curr.r;
			int currC = curr.c;
			TreeSet<Fire> currFire = curr.fireSpaces;
			// out.println(currR + ", " + currC + " : " + currDist + " - " + currFire);
			Fire tempFire = new Fire(currR, currC);
			if(isBoundary(currR, currC) && !currFire.contains(tempFire) && maze[currR][currC] == Space.SPACE) {
				out.println(distance[currR][currC] + 1);
				exit = true;
				break;
			}
			else {
				TreeSet<Fire> newFireSet = generateNewFireSpaces(currFire);
				for(int i = 0; i < 4; i++) {
					int newR = currR + dx[i];
					int newC = currC + dy[i];
					tempFire = new Fire(newR, newC);
					if(isValid(newR, newC) && distance[newR][newC] == -1 && !newFireSet.contains(tempFire) && maze[newR][newC] == Space.SPACE) {
						distance[newR][newC] = distance[currR][currC] + 1; 
						toSearch.add(new Joe(newR, newC, newFireSet));
					}
				}
			}
		}
		if(!exit) {
			out.println("IMPOSSIBLE");
		}
		out.close();
		file.close();
	}
	public TreeSet<Fire> generateNewFireSpaces(TreeSet<Fire> original) {
		TreeSet<Fire> newFireSpaces = new TreeSet<Fire>();
		newFireSpaces.addAll(original);
		for(Fire currFireSpace : original) {
			for(int i = 0; i < 4; i++) {
				int newR = currFireSpace.r + dx[i];
				int newC = currFireSpace.c + dy[i];
				if(isValid(newR, newC) && (maze[newR][newC] == Space.SPACE)) {
					newFireSpaces.add(new Fire(newR, newC));
				}
			}
		}
		return newFireSpaces;
	}
	public boolean isValid(int currR, int currC) {
		return (0 <= currR && currR < r && 0 <= currC && currC < c);
	}
	public boolean isBoundary(int currR, int currC) {
		return (currR == 0 || currR == r-1 || currC == 0 || currC == c-1);
	}
}
