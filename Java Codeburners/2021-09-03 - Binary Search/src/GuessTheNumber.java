import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int high = 1000;
        int low = 1;
        int mid = (high+low) / 2;
        int count = 0;
        String response = "";
        while(!response.equals("correct") || count == 10) {
            System.out.println(mid);
            response = scan.nextLine();
            count++;
            
            if(response.equals("higher")) {
                low = mid+1;
                mid = (high+low) / 2;
            }
            else if(response.equals("lower")) {
                high = mid-1;
                mid = (high+low) / 2;
            }
            else {
                break;
            }
            
        }
    }
}