import java.util.Arrays;
import java.util.Scanner;

public class OutOfSorts {
     public static void main(String[] args) {
            Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();
            long m = scan.nextLong();
            long a = scan.nextLong();
            long c = scan.nextLong();
            long x0 = scan.nextLong();
            long[] sequence = generateSequence(n, m, a, c, x0);
            long[] sortedSequence = generateSequence(n, m, a, c, x0);
            Arrays.sort(sortedSequence);
            
            int sum = 0;
            for(int i = 1; i < n+1; i++) {
                sum += binarySearch(sequence, sortedSequence[i]) ? 1 : 0;
            }
            System.out.println(sum);
     }
     public static long[] generateSequence(int n, long m, long a, long c, long x0) {
         long[] sequence = new long[n+1];
         sequence[0] = x0;
         for(int i = 1; i < n+1; i++) {
             sequence[i] = (a * sequence[i-1] + c) % m;
         }
         sequence[0] = -1;
         return sequence;
     }
     public static boolean binarySearch(long[] sequence, long value) {
         int L = 0;
         int H = sequence.length;
         int M = (L + H) / 2;
         while((H-L) > 1) {
             M = (L + H) / 2;
             if(sequence[M] == value) {
                 return true;
             }
             else if(sequence[M] < value) {
                 L = M;
             }
             else {
                 H = M;
             }
         }
         return false;
     }
}
