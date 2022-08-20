import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Canonical {
    static int[] dp;
    static int[] coins;
    
    public static void main(String[] agrs) throws IOException {
    	new Canonical().run();
    }

    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        int numCoins = Integer.parseInt(file.readLine());
        StringTokenizer st = new StringTokenizer(file.readLine());
        coins = new int[numCoins];
        for(int i = 0; i < numCoins; i++) {
        	coins[i] = Integer.parseInt(st.nextToken());
        }

        int smallestCounterexample = coins[numCoins-1] + coins[numCoins-2] + 1;
        dp = new int[smallestCounterexample];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[0] = 0;
        for(int i = 0; i < dp.length; i++) {
            for(int c : coins) {
                if(i + c < dp.length) {
                	dp[i + c] = Math.min(dp[i + c], dp[i] + 1);
                }
            }
        }

        boolean canonical = true;
        for(int i = smallestCounterexample - 1; i > 0; i--){
            if(dp[i] != greedy(i)){
                canonical = false;
                break;
            }
        }
        System.out.println(canonical ? "canonical" : "non-canonical");
    }

    static int greedy(int x){
        int greedy = 0;
        int i = coins.length - 1;
        while (x != 0) {
            if (x < coins[i]) { 
            	i--;
            }
            else {
                greedy += x / coins[i];
                x %= coins[i];
            }
        }
        return greedy;
    }
}