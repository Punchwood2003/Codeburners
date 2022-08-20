/*
BEGIN ANNOTATION
PROBLEM URL: open.kattis.com/problems/turbo
TAGS: Sorting
EXPLANATION: For each N steps in the sorting algorithm, 
print the number of swaps needed to complete the transformation.
END ANNOTATION
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class Turbo {
    public static void main(String[] args) throws IOException {
        new Turbo().run();
    }
    public HashMap<Integer, Integer> getIntPosition;
    public void run() throws IOException {
        // Create I/O objects
        BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        // Take in input
        int N = Integer.parseInt(file.readLine());
        getIntPosition = new HashMap<Integer, Integer>();
        int[] arr = new int[N];
        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(file.readLine());
            getIntPosition.put(arr[i], i);
        }
        
        // Close the input
        file.close();
        
        // Sort the array in the pattern described earlier
        for(int i = 0; i < N; i++) {
            // If i is even, then we take the smallest unsorted element.
            // Else, i is odd, and we take the largest unsorted element
            int intToSort = (i % 2 == 0) ? ((i / 2) + 1) : (N - (i / 2)); 
            // Get the currentPosition of the integer that we need to sort
            int pos = getIntPosition.get(intToSort);
            //System.out.println(intToSort + " : " + pos);
            // Perform the sort, and retrieve the number of swaps needed
            int numSwapsNeeded = this.sort(intToSort, pos, arr);
            //System.out.println(Arrays.toString(arr));
            // Print the number of swaps needed for this transformation
            out.println(numSwapsNeeded);
        }
        
        // Close the output
        out.close();
    }
    public int sort(int intToSort, int startPosition, int[] arr) {
        // The number of swaps needed is equal to the distance 
        // of its current position and its desired position
        int numSwaps = Math.abs(startPosition - (intToSort - 1));
        // If the current position is to the left of where it needs to be, sort upwards
        if(startPosition < (intToSort-1)) {
            //System.out.println("Sort Up");
            sortUp(intToSort, startPosition, arr);
        }
        // Else, the current position is to the right of where it needs to be so we need to sort downwards
        else if(startPosition > (intToSort-1)){
            //System.out.println("Sort Down");
            sortDown(intToSort, startPosition, arr);
        }
        // Return the number of swaps needed
        return numSwaps;
    } 
    private void sortUp(int intToSort, int startPosition, int[] arr) {
        for(int i = startPosition; i < intToSort-1; i++) {
            // Move the element to the right to the left
            arr[i] = arr[i+1];
            // Update the position of that element
            this.getIntPosition.put(arr[i], i);
        }
        // Move the item we are sorting to its correct destination
        arr[intToSort-1] = intToSort;
        // Update the position of that element
        this.getIntPosition.put(intToSort, intToSort-1);
    }
    private void sortDown(int intToSort, int startPosition, int[] arr) {
        for(int i = startPosition; i >= intToSort; i--) {
            // Move the element to the left to the right
            arr[i] = arr[i-1];
            // Update the position of that element
            this.getIntPosition.put(arr[i], i);
        }
        // Move the item we are sorting to its correct destination
        arr[intToSort-1] = intToSort;
        // Update the position of that element
        this.getIntPosition.put(intToSort, intToSort-1);
    }
}