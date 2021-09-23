import java.util.Arrays;
import java.util.Scanner;

public class RoomPainting {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();

        long[] sizes = new long[n];
        long[] amountsNeeded = new long[m];

        for(int i = 0; i < n; i++) {
            sizes[i] = scan.nextLong();
        }
        Arrays.sort(sizes);
        for(int i = 0; i < m; i++) {
            amountsNeeded[i] = scan.nextLong();
        }

        long waste = 0L;
        for(int i = 0; i < m; i++) {
            int L = -1; 
            int R = n;
            int M = (L + R) / 2;
            long needed = amountsNeeded[i];
            int bestIndex = -1;
            while(R - L > 1) {
                M = (L + R) / 2;
                long currSize = sizes[M];
                if(currSize == needed) {
                    bestIndex = M;
                    R = L = 0;
                    break;
                }
                else if(currSize > needed) {
                    bestIndex = M;
                    R = M;
                }
                else {
                    L = M;
                }
            }
            waste += sizes[bestIndex] - needed;
        }
        System.out.println(waste);
        scan.close();
    }
}