import java.util.Scanner;

public class JewelryBox {
     public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
         int T = scan.nextInt();
         while(T-->0) {
             double X = scan.nextInt();
             double Y = scan.nextInt();
             
             double L = 0;
             double R = Math.min(X/2.0, Y/2.0);;
             double M = (L+R) / 2.0;
             
             for(int i = 0; i < 200; i++) {
                 M = (L+R) / 2.0;
                 double A1 = X - (2 * M);
                 double B1 = Y - (2 * M);
                 double V1 = A1 * B1 * M;
                 
                 double M2 = M + Math.pow(10, -7);
                 double A2 = X - (2 * M2);
                 double B2 = Y - (2 * M2);
                 double V2 = A2 * B2 * M2;
                 
                 if(V1 < V2) {
                     L = M;
                 }
                 else {
                     R = M2;
                 }
             }
             M = (L+R) / 2.0;
             double A = X - (2 * M);
             double B = Y - (2 * M);
             System.out.printf("%.11f%n", A * B * M);
         }
     }
}