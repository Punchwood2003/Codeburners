import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Guess {
	public static void main(String[] args) throws IOException {
		new Guess().run();
	}
	
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		
		int hi = 1000;
		int lo = 1;
		int mid = (hi + lo) / 2;
		while(true) {
			System.out.println(mid);
			
			String ans = file.readLine();
			switch(ans) {
			case "lower": {
				hi = mid - 1;
				break;
			}
			case "higher": {
				lo = mid + 1;
				break;
			}
			case "correct": { 
				file.close();
				return;
			}
			}
			
			mid = (hi + lo) / 2;
		}
	}
}
