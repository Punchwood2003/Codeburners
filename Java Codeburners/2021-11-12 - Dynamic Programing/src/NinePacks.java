import java.util.Arrays;
import java.util.Scanner;

public class NinePacks {
	public static void main(String[] args) {
		new NinePacks().run();
	}
	public void run() {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int[] setA = new int[N];
		for(int i = 0; i < N; i++) {
			setA[i] = scan.nextInt();
		}
		int M = scan.nextInt();
		int[] setB = new int[M];
		for(int i = 0; i < M; i++) {
			setB[i] = scan.nextInt();
		}
		
		int[] dp1 = new int[100001];
		int[] dp2 = new int[100001];
		Arrays.fill(dp1, Integer.MAX_VALUE / 4);
		Arrays.fill(dp2, Integer.MAX_VALUE / 4);
		
		dp1[0] = 0;
		dp2[0] = 0;
		for(int a : setA) {
			for(int i = dp1.length - 1; i >= a; i--) {
				dp1[i] = Math.min(dp1[i], dp1[i-a] + 1);
			}
		}
		
		for(int b : setB) {
			for(int i = dp2.length - 1; i >= b; i--) {
				dp2[i] = Math.min(dp2[i], dp2[i-b] + 1);
			}
		}
		
		int min = Integer.MAX_VALUE;
		for(int i = 1; i < dp1.length; i++) {
			int candidate = dp1[i] + dp2[i];
			min = Math.min(min, candidate);
		}
		if(min < Integer.MAX_VALUE / 4) {
			System.out.println(min);
		}
		else {
			System.out.print("impossible");
		}
	}
}
