import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Anti11 {
	
    public static void main(String[] args) throws IOException {
        new Anti11().run();
    }
    public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(file.readLine());
        for (int j = 0; j < n; j++) {
        	int curr = Integer.parseInt(file.readLine());
            char[] bitstring = Integer.toBinaryString(curr).toCharArray(); 
            long[][] Apow = {
              {1, 1},
              {1, 0}
            };
            long[][] Afin = {
              {1, 0},
              {0, 1}
            };
            for (int i = bitstring.length - 1; i >= 0; i--) {
                if (bitstring[i] == '1') {
                    Afin = multiply(Afin, Apow);
                }               
                Apow = multiply(Apow, Apow);
            }
            out.println((Afin[0][0] + Afin[0][1])%(1000000007));
        }
        out.close();
        file.close();
	}
    public static long[][] multiply(long[][] a, long[][] b) {
        long[][] ret = new long[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                ret[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
                ret[i][j] %= 1000000007;
            }
        }
        return ret;
    }   
}