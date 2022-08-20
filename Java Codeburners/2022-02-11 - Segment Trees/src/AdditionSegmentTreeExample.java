import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class AdditionSegmentTreeExample {
	public AdditionSegmentTree segmentTree;
	public static void main(String[] args) throws IOException {
		new AdditionSegmentTreeExample().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		String[] line = file.readLine().split(" ");
		this.segmentTree = new AdditionSegmentTree(line.length, 0);
		int i = 0;
		for(ArrayList<Integer> temp : this.segmentTree.data) {
			out.println(temp);
		}
		for(String temp : line) {
			this.segmentTree.set(i++, Integer.parseInt(temp));
		}
		for(ArrayList<Integer> temp : this.segmentTree.data) {
			out.println(temp);
		}
		out.close();
		file.close();
	}
}
class AdditionSegmentTree {
	public int n, m;
	public ArrayList<ArrayList<Integer>> data;
	public Integer identityElement; 
	public AdditionSegmentTree(int n, Integer id) {
		this.identityElement = id;
		this.data = new ArrayList<ArrayList<Integer>>();
		n--;
		for(int i = 0; i < 5; i++) {
			n |= n >> (1 << i);
		}
		n++;
		System.out.println(n);
		this.n = n;
		for(int k = this.n; k > 0; k /= 2) {
			ArrayList<Integer> tempList = new ArrayList<Integer>(k);
			for(int i = 0; i < k; i++) {
				tempList.add(this.identityElement);
			}
			this.data.add(0, tempList);
		}
	}
	public void set(int i, Integer x) {
		this.data.get(data.size()-1).set(i, x);
		for(int l = this.m-2, j = i/2; l >= 0; l--, j /= 2) {
			// This is where you would change the operation to be whatever you are actually doing
			Integer result = this.data.get(l+1).get(j*2) + this.data.get(l+1).get(j*2 + 1);
			this.data.get(l).set(i, result);
		}
	}
	private Integer get(int lo, int hi, int nlo, int nhi, int l, int i) {
		lo = Math.max(lo, nlo);
		hi = Math.min(hi, nhi);
		if(hi < lo) {
			return this.identityElement;
		}
		if(lo == nlo && hi == nhi) {
			return this.data.get(l).get(i);
		}
		int nmid = (nlo + nhi) / 2;
		Integer left = this.get(lo, hi, nlo, nmid, l+1, i*2);
		Integer right = this.get(lo, hi, nmid+1, nhi, l+1, i*2 + 1);
		// This is where you would change the operation to be whatever you are actually doing
		return left + right; 
	}
	public Integer get(int lo, int hi) {
		return this.get(lo, hi, 0, this.n-1, 0, 0);
	}
}
