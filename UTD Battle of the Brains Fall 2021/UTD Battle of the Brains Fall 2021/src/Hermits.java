import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Hermits {
    public static void main(String[] args) throws IOException {
        new Hermits().run();
    }
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int numStreets = Integer.parseInt(file.readLine());
        long[] people = new long[numStreets+1];
        long[] adjusted = new long[numStreets+1];
        StringTokenizer st = new StringTokenizer(file.readLine());
        for(int i = 1; i <= numStreets; i++) {
            people[i] = Integer.parseInt(st.nextToken());
            adjusted[i] = people[i];
        }
        int numUnions = Integer.parseInt(file.readLine());
        for(int i = 0; i < numUnions; i++) {
            st = new StringTokenizer(file.readLine());
            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken()); 
            adjusted[num1] += people[num2];
            adjusted[num2] += people[num1];
        }
        int minIndex = -1; 
        long minSize = Long.MAX_VALUE;
        for(int i = 1; i <= numStreets; i++) {
            long size = adjusted[i];
            if(size < minSize) {
                minSize = size;
                minIndex = i;
            }
        }
        out.println(minIndex);
        out.close();
        file.close();
    }
}