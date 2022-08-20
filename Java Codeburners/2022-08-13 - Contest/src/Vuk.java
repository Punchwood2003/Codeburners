import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

public class Vuk {
    class Pair implements Comparable<Pair> {
        public int r, c;
        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }
        public int compareTo(Pair other) {
            int comp = Integer.compare(this.r, other.r);
            return comp == 0 ? Integer.compare(this.c, other.c) : comp;
        }
    }
    public static void main(String[] args) throws IOException {
        new Vuk().run();
    }
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String[] line = file.readLine().split(" ");
        int R = Integer.parseInt(line[0]);
        int C = Integer.parseInt(line[1]);
        
        String[][] map = new String[R][C];
        TreeSet<Pair> trees = new TreeSet<Pair>();
        int rV = -1, cV = -1, rJ = -1, cJ = -1;
        for(int r = 0; r < R; r++) {
            line = file.readLine().split("");
            map[r] = line;
            for(int c = 0; c < C; c++) {
                if(line[c].equals("+")) {
                    trees.add(new Pair(r, c));
                } else if(line[c].equals("J")) {
                    rJ = r;
                    cJ = c;
                } else if(line[c].equals("V")) {
                    rV = r;
                    cV = c;
                }
            }
        }
        
        int solution = 0;
        while(bfs(map, rV, cV, rJ, cJ, R, C)) {
            solution++;
            TreeSet<Pair> newTrees = new TreeSet<Pair>();
            for(Pair pair : trees) {
                int r = pair.r;
                int c = pair.c;
                if(r-1 >= 0) {
                    newTrees.add(new Pair(r-1, c));
                }
                if(r+1 < R) {
                    newTrees.add(new Pair(r+1, c));
                }
                if(c-1 >= 0) {
                    newTrees.add(new Pair(r, c-1));
                }
                if(c+1 < C) {
                    newTrees.add(new Pair(r, c+1));
                }
            }
            trees.addAll(newTrees);
            for(Pair pair : newTrees) {
                map[pair.r][pair.c] = "+";
            }
        }
        out.println(solution);
        out.close();
    }
    public int[][] translations = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public boolean bfs(String[][] map, int startR, int startC, int destR, int destC, int R, int C) {
        boolean[][] visited = new boolean[R][C];
        Queue<int[]> toSearch = new LinkedList<int[]>();
        if(map[startR][startC].equals("V")) {
            toSearch.offer(new int[] {startR, startC});
        }
        while(!toSearch.isEmpty()) {
            int[] pos = toSearch.poll();
            int r = pos[0];
            int c = pos[1];
            visited[r][c] = true;
            
            if(map[r][c].equals("J")) {
                return true;
            }
            for(int[] translation : translations) {
                int dR = translation[0];
                int dC = translation[1];
                if(r + dR >= 0 && r + dR < R && c + dC >= 0 && c + dC < C && !map[r + dR][c + dC].equals("+") && !visited[r + dR][c + dC]) {
                    toSearch.offer(new int[] {r + translation[0], c + translation[1]});
                }
            }
        }
        
        return false;
    }
}
