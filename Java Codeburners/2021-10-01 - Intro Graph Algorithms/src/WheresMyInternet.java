import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

class UnionFind {
	// p[i] is the node that element i points at
    // if element i is a set representative, p[i] = -size instead
    public int[] p;
   
    /**
     * Creates a UnionFind object with size n
     * @param n	The maximum number of elements that we are working with
     */
    public UnionFind(int n) {
        p = new int[n];
        // initially, every node is the representative of it's own set
        Arrays.fill(p, -1);
    }
    
    /**
     * Find the set that the integer x is a part of 
     * and return the set representative of that set
     * @param x	The element that we are searching for
     * @return	The set representative of the element we are searching for
     */
    public int find(int x) {
        if(p[x] < 0) { // in this case, x is the set representative and we can return
            return x;
        }
        int px = find(p[x]); // get set representative from parent
        p[x] = px; // path compression
        return px;
    }
    
    /**
     * Take the union of two sets
     * Find the sets that both x and y are a part of 
     * Find the set representatives of both of those sets
     * If x and y are pointing at different values...
     * 	-Take the union of the two sets
     * 	-Make the smaller set representative point at the larger set representative
     * If x and y are pointing at the same element...
     * 	-They are a part of the same set
     * 	-Do not perform the union
     * @param x	The first element that we are taking the union of
     * @param y	The second element that we are taking the union of
     * @return	True if we performed the union or false if we didn't
     */
    public boolean union(int x, int y) {
        int px = find(x); // get set representative of x
        int py = find(y); // get set representative of y
        if(px == py) { // if x and y are in the same set, stop
            return false;
        }
        if(p[px] < p[py]) { // if px has more elements than py (remember that the sizes are negative here)
            int save = px;
            px = py;
            py = save;
        }
        p[py] += p[px]; // increase y set size by x set size; 
        p[px] = py; // make x set representative point to y set representative
        return true;
    }
} 
public class WheresMyInternet {
	public static void main(String[] args) throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		UnionFind uF = new UnionFind(N+1);
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(file.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			uF.union(a, b);
		}
		TreeSet<Integer> unConnected = new TreeSet<Integer>();
		int sR = uF.find(1);
		for(int i = 2; i <= N; i++) {
			int find = uF.find(i);
			if(find != sR) {
				unConnected.add(i);
			}
		}
		if(unConnected.size() == 0) {
			out.println("Connected");
		}
		else {
			for(Integer temp : unConnected) {
				out.println(temp);
			}
		}
		out.close();
		file.close();
	}
}
