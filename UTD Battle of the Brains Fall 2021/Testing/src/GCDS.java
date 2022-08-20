public class GCDS {
	public static void main(String[] args) {
		for(int i = 0; i <= 20; i++) {
			for(int j = 0; j <= 15; j++) {
				int gcd = gcdByEuclidsAlgorithm(i, j);
				if(gcd != 0) {
					System.out.print("lcm(" + i + ", " + j + ") is: " + ((i * j) / gcd) + "\n");
				}
			}
		}
	}
	public static int gcdByEuclidsAlgorithm(int n1, int n2) {
	    if (n2 == 0) {
	        return n1;
	    }
	    return gcdByEuclidsAlgorithm(n2, n1 % n2);
	}
}
