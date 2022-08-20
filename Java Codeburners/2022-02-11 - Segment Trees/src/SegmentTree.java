//import java.util.ArrayList;
//import java.util.Collections;
//
//public class SegmentTree<T> {
//	public int n, m;
//	public ArrayList<ArrayList<T>> data;
//	public T identityElement; 
//	public SegmentTree(int n, T id) {
//		this.identityElement = id;
//		n--;
//		for(int i = 0; i < 5; i++) {
//			n |= n >> (1 << i);
//		}
//		n++;
//		this.n = n;
//		for(int k = this.n; k >= 0; k /= 2) {
//			ArrayList<T> tempList = new ArrayList<T>();
//			Collections.fill(tempList, this.identityElement);
//			this.data.add(0, tempList);
//		}
//	}
//	public void set(int i, T x) {
//		this.data.get(data.size()-1).set(i, x);
//		for(int l = n-2, j = i/2; l >= 0; l--, j /= 2) {
//			// This is where you would change the operation to be whatever you are actually doing
//			T result = this.data.get(l+1).get(j*2) + this.data.get(l+1).get(j*2 + 1);
//			this.data.get(l).set(i, result);
//		}
//	}
//	private T get(int lo, int hi, int nlo, int nhi, int l, int i) {
//		lo = Math.max(lo, nlo);
//		hi = Math.min(hi, nhi);
//		if(hi < lo) {
//			return this.identityElement;
//		}
//		if(lo == nlo && hi == nhi) {
//			return this.data.get(l).get(i);
//		}
//		int nmid = (nlo + nhi) / 2;
//		T left = this.get(lo, hi, nlo, nmid, l+1, i*2);
//		T right = this.get(lo, hi, nmid+1, nhi, l+1, i*2 + 1);
//		// This is where you would change the operation to be whatever you are actually doing
//		return left + right; 
//	}
//	public T get(int lo, int hi) {
//		return this.get(lo, hi, 0, this.n-1, 0, 0);
//	}
//}
