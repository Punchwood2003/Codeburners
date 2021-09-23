import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SmallestMultiple {
    public static void main(String[] args) throws IOException {
        new SmallestMultiple().run(); 
    }
    
    public void run() throws IOException {
        // Create objects for efficient system in and system out
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st;
        
        String in = file.readLine();
        while(in != null) {
            st = new StringTokenizer(in);
            ArrayList<BigInteger> numbers = new ArrayList<BigInteger>();
            while(st.hasMoreTokens()) {
                numbers.add(new BigInteger(st.nextToken()));
            }
            
            // For each set of BigIntegers in the numbers list (i,i+1), 
            // calculate the lcm of the two numbers and set the list at i+1
            // to be equal to the value of lcm(numbers[i],numbers[i-1])
            for(int i = 0; i < numbers.size()-1; i++) {
                numbers.set(i+1, lcm(numbers.get(i), numbers.get(i+1)));
            }
            // Print the lcm of the whole set of integers
            out.println(numbers.get(numbers.size()-1));
            in = file.readLine();
        }
        out.close();
        file.close();
    }
    
    /**
     * Calculate the lcm of two numbers calculated by (a*b)/(gcd(a,b))
     * @param a	The first number
     * @param b	The second number
     * @return	The lcm(a,b)
     */
    public BigInteger lcm(BigInteger a, BigInteger b) {
    	return a.multiply(b).divide(gcd(a,b));
    }
    
    /**
     * Calculate the gcd of two numbers calculated by 
     * gcd(b,a%b) until b == 0
     * @param a	The first number
     * @param b	The second number
     * @return	The gcm(a,b)
     */
    public BigInteger gcd(BigInteger a, BigInteger b) {
        if(!b.equals(BigInteger.ZERO)) {
            return gcd(b, a.mod(b));
        }
        else {
            return a;
        }
    }
}
