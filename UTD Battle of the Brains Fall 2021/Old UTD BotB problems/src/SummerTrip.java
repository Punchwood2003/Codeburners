import java.util.Arrays;
import java.util.Scanner;

public class SummerTrip {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String line = scan.next();
		long numCombinations = 0;
		String[] compress = compress(line);
		System.out.println(Arrays.toString(compress));
		for(int i = 0; i < compress.length-2; i += 2) {
			String currI = compress[i];
			int numI = Integer.parseInt(compress[i+1]);
			System.out.println(currI + " : " + numI);
			for(int j = i+2; j < compress.length; j+= 2) {
				String currJ = compress[j];
				int numJ = Integer.parseInt(compress[j+1]);
				System.out.println("\t" + currJ + " : " + numJ);
			}
		}
	}
	public static String[] compress(String original) {
		StringBuilder sb = new StringBuilder();
		char curr = original.charAt(0);
		int i = 0;
		int j;
		for(j = i+1; j < original.length(); j++) {
			if(curr != original.charAt(j)) {
				sb.append(curr);
				sb.append(' ');
				sb.append((j-i));
				sb.append(' ');
				i = j;
				curr = original.charAt(j);
			}
		}
		sb.append(curr);
		sb.append(' ');
		sb.append(j-i);
		return sb.toString().split(" ");
	}
}
