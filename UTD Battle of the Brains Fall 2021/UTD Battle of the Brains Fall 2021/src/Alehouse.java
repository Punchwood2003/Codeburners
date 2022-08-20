import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Alehouse {
    public class Time implements Comparable<Time> {
        public BigInteger time;
        public boolean isStart;
        public Time(BigInteger t, boolean i) {
            this.time = t;
            this.isStart = i;
        }
        public int compareTo(Time other) {
            int comp = this.time.compareTo(other.time);
            if(comp == 0) {
                if(this.isStart == other.isStart) {
                    comp = 0;
                }
                else if(this.isStart) {
                    comp = -1;
                }
                else {
                    comp = 1;
                }
            }
            return comp;
        }
        public String toString() {
            return "" + this.time + this.isStart;
        }
    }
    public static void main(String[] args) throws IOException {
        new Alehouse().run();
    }
    public void run() throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(file.readLine());
        int numPeople = Integer.parseInt(st.nextToken());
        BigInteger k = new BigInteger(st.nextToken());
        Time[] times = new Time[numPeople*2];
        int i = 0;
        while(numPeople-->0) {
            st = new StringTokenizer(file.readLine()); 
            BigInteger start = new BigInteger(st.nextToken());
            BigInteger end = new BigInteger(st.nextToken());
            end = end.add(k);
            
            Time time1 = new Time(start, true);
            Time time2 = new Time(end, false);
            times[i++] = time1;
            times[i++] = time2;
        }
        Arrays.sort(times);
        int currNumPeople = 0;
        long solution = Long.MIN_VALUE;
        for(i = 0; i < times.length; i++) {
            Time curr = times[i];
            if(curr.isStart) {
                currNumPeople++;
            }
            else {
                solution = Math.max(solution, currNumPeople);
                currNumPeople--;
            }
        }
        out.println(solution);
        out.close();
        file.close();
    }
}
