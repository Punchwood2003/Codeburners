import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;

public class Crypto {
    public static void main(String[] args) throws IOException {
        new Crypto().run();
    }
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        BigInteger N = new BigInteger(file.readLine());
        BigInteger K = new BigInteger("2");
        HashMap<BigInteger, BigInteger> baseToZeros = new HashMap<BigInteger, BigInteger>();
        while(K.compareTo(N.sqrt()) <= 0) {
        	BigInteger temp = BigInteger.ZERO; 
            while(N.mod(K).compareTo(BigInteger.ZERO) <= 0) {
            	temp.add(BigInteger.ONE);
            	N = N.divide(K);
            }
            if(temp.compareTo(BigInteger.ZERO) > 0) {
            	baseToZeros.put(K, temp);
            }
            K = K.add(BigInteger.ONE);
        }
        BigInteger bestK = BigInteger.TWO;
        BigInteger bestNumZeros = BigInteger.ZERO;
        for(BigInteger key : baseToZeros.keySet()) {
        	BigInteger currNumZeros = baseToZeros.get(key);
        	if(currNumZeros.compareTo(bestNumZeros) > 0) {
        		bestNumZeros = currNumZeros;
        		bestK = key;
        	}
        }
        out.println(bestK);
        out.close();
        file.close();
    }
}