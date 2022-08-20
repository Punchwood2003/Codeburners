import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class More10 {
    /**
     * This class handles two actions:
     *  -Taking the Union of two sets
     *  -Finding the set in which an element is a part of
     */
    private class UnionFind {
        // p[i] is the node that element i points at
        // if element i is a set representative, p[i] = -size instead
        public int[] p;

        /**
         * Creates a UnionFind object with size n
         * @param n The maximum number of elements that we are working with
         */
        public UnionFind(int n) {
            p = new int[n];
            // initially, every node is the representative of it's own set
            Arrays.fill(p, -1);
        }

        /**
         * Find the set that the integer x is a part of 
         * and return the set representative of that set
         * @param x The element that we are searching for
         * @return  The set representative of the element we are searching for
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
         *  -Take the union of the two sets
         *  -Make the smaller set representative point at the larger set representative
         * If x and y are pointing at the same element...
         *  -They are a part of the same set
         *  -Do not perform the union
         * @param x The first element that we are taking the union of
         * @param y The second element that we are taking the union of
         * @return  True if we performed the union or false if we didn't
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
    private class DoubleString {
        public String str1, str2;
        public DoubleString(String s1, String s2) {
            this.str1 = s1;
            this.str2 = s2;
        }
    }
    public static void main(String[] args) throws IOException {
        new More10().run();
    }
    public UnionFind uf;
    public String[] wordsArr; 
    public boolean[] usedWordsArr;
    public HashMap<String, Integer> getIndex;
    public HashMap<String, HashSet<String>> suffixToWord;
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st;
        ArrayList<DoubleString> notConnections = new ArrayList<DoubleString>();
        int numConnections = Integer.parseInt(file.readLine());
        uf = new UnionFind(numConnections * 2);
        wordsArr = new String[numConnections * 2];
        usedWordsArr = new boolean[numConnections * 2];
        getIndex = new HashMap<String, Integer>();
        int nextIndex = 0;
        while(numConnections-->0) {
            st = new StringTokenizer(file.readLine());
            String node1 = st.nextToken();
            String connection = st.nextToken();
            String node2 = st.nextToken();

            if(!getIndex.containsKey(node1)) {
                int index = nextIndex++; 
                wordsArr[index] = node1;
                getIndex.put(node1, index);
            }
            if(!getIndex.containsKey(node2)) {
                int index = nextIndex++;
                wordsArr[index] = node2;
                getIndex.put(node2, index);
            }

            int index1 = getIndex.get(node1);
            int index2 = getIndex.get(node2);
            if(connection.equals("is")) {
                uf.union(index1, index2);
            }
            else {
                notConnections.add(new DoubleString(node1, node2));
            }
        }

        marten(nextIndex); 

        boolean yes = true;
        for(DoubleString temp : notConnections) {
            String node1 = temp.str1;
            String node2 = temp.str2;
            int index1 = getIndex.get(node1);
            int index2 = getIndex.get(node2);
            boolean connected = uf.find(index1) == uf.find(index2);
            if(connected) {
                out.println("wait what?");
                yes = false;
                break;
            }
        }
        if(yes) {
            out.println("yes");
        }
        out.close();
        file.close();

    }
    public void marten(int len) {
        for(int i = 0; i < len-1; i++) {
            if(!usedWordsArr[i]) {
                String node1 = wordsArr[i];
                int length = node1.length();
                int index1 = getIndex.get(node1);
                for(int j = i+1; j < len; j++) {
                    String node2 = wordsArr[j];
                    int min = Math.min(3, Math.min(node2.length(), length));
                    String sub1 = node1.substring(length-min);
                    String sub2 = node2.substring(node2.length()-min);
                    if(sub1.compareTo(sub2) == 0) {
                        usedWordsArr[j] = true;
                        int index2 = getIndex.get(node2);
                        uf.union(index1, index2);
                    }
                }
            }
        }
    }
    
}
public static <E> print(E[] arr) {
	
}