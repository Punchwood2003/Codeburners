import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Puddles {
	public static void main(String[] args) throws IOException {
		new Puddles().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		Scanner scan = new Scanner(file.readLine());
		int N = Integer.parseInt(scan.next());
		int Q = Integer.parseInt(scan.next());
		
		Puddle[] puddles = new Puddle[N];
		for(int i = 0; i < N; i++) {
			scan = new Scanner(file.readLine());
			puddles[i] = new Puddle(scan.nextInt(), scan.nextInt());
		}
		
		int[][] queries = new int[Q][2];
		for(int i = 0; i < Q; i++) {
			scan = new Scanner(file.readLine());
			queries[i][0] = scan.nextInt();
			queries[i][1] = scan.nextInt();
		}
		
		scan.close();
		
		UnionFind[] uf = new UnionFind[N-1];
		double[] t = new double[N-1];
		for(int i = 0; i < N-1; i++) {
			/* We start from scratch */
			if(i == 0) {
				/* We need to search for the very first intersection */
				for(int j = 0; j < N-2; j++) {
					for(int k = j+1; k < N-1; k++) {
						
					}
				}
			} else {
				
			}
		}
	}
}
class Puddle implements Comparable<Puddle> {
	public double x, A;
	public double r, dr;
	public Puddle(int x, int A) {
		this.x = x;
		this.A = A;
		this.r = Math.sqrt(A / (Math.PI));
		this.dr = 1 / (2 * Math.PI * this.r);
	}
	public int compareTo(Puddle other) {
		int comp = Double.compare(this.x, other.x);
		if(comp == 0) {
			comp = Double.compare(this.A, other.A);
		}
		return comp;
	}
	public double getTime(Puddle other) {
		return (this.x - other.x) / (other.dr - this.dr);
	}
}
class UnionFind {
	
}