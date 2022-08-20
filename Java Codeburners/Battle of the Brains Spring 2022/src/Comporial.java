import java.io.*;
import java.util.*;

public class Comporial {
    public static ArrayList<Integer> primes, composites;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numTimes = Integer.parseInt(scan.nextLine());
        sieve(414977);
        while(numTimes-->0) {
            
        }
        System.out.println(primes);
        System.out.println(composites);
    }
    public static void sieve(int max) {
        ArrayList<Integer> primes = new ArrayList<Integer>();
        ArrayList<Integer> composites = new ArrayList<Integer>();
        Queue<Integer> numbers = new LinkedList<Integer>();
        for(int i = 2; i <= max; i++) {
            numbers.add(i);
        }
        while(!numbers.isEmpty()) {
            int P = numbers.poll();
            primes.add(P);
            Queue<Integer> temp = new LinkedList<Integer>();
            while(!numbers.isEmpty()) {
                Integer curr = numbers.poll();
                boolean trash = curr % P == 0 ? composites.add(curr) : temp.add(curr);
            }
            numbers = temp;
        }
        System.out.println("out");
        Collections.sort(composites);
        System.out.println(composites.size() + " " + primes.size());
    } 
}