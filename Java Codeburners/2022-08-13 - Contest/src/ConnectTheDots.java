import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ConnectTheDots {
	public static void main(String[] args) throws IOException {
		new ConnectTheDots().run();
	}
	public void run() throws IOException {
		Scanner file = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		boolean done = false;
		while(!done) {
			String line = file.nextLine();
			ArrayList<String[]> map = new ArrayList<String[]>();
			while(line.length() > 0) {
				map.add(line.split(""));
				if(file.hasNextLine()) {
					line = file.nextLine();
				} else {
					done = true;
					break;
				}
			}
			
			HashMap<String, int[]> charToPosition = new HashMap<String, int[]>();
			int R = map.size();
			int C = map.get(0).length;
			for(int i = 0; i < R; i++) {
				String[] arr = map.get(i);
				for(int j = 0; j < C; j++) {
					if(!arr[j].equals(".")) {
						charToPosition.put(arr[j], new int[] {i, j});
					}
				}
			}
			
			String symbol = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			for(int i = 0; i < symbol.length()-1; i++) {
				String char1 = "" + symbol.charAt(i);
				String char2 = "" + symbol.charAt(i+1);
				if(charToPosition.containsKey(char2)) {
					int[] pos1 = charToPosition.get(char1);
					int[] pos2 = charToPosition.get(char2); 
					
					int r1 = pos1[0];
					int r2 = pos2[0];
					int c1 = pos1[1];
					int c2 = pos2[1];
					
					int deltaR = r2-r1;
					int deltaC = c2-c1;
					if(deltaR > 0) {
						for(int r = r1+1; r < r2; r++) {
							String[] arr = map.get(r);
							if(arr[c1].equals(".")) {
								arr[c1] = "|";
							} else if(arr[c1].equals("-")) {
								arr[c1] = "+";
							}
							map.set(r, arr);
						}
					} else if(deltaR < 0) {
						for(int r = r2+1; r < r1; r++) {
							String[] arr = map.get(r);
							if(arr[c1].equals(".")) {
								arr[c1] = "|";
							} else if(arr[c1].equals("-")) {
								arr[c1] = "+";
							}
							map.set(r, arr);
						}
					} else if(deltaC > 0) {
						String[] arr = map.get(r1);
						for(int c = c1+1; c < c2; c++) {
							if(arr[c].equals(".")) {
								arr[c] = "-";
							} else if(arr[c].equals("|")) {
								arr[c] = "+";
							}
						}
						map.set(r1, arr);
					} else if(deltaC < 0) {
						String[] arr = map.get(r1);
						for(int c = c2+1; c < c1; c++) {
							if(arr[c].equals(".")) {
								arr[c] = "-";
							} else if(arr[c].equals("|")) {
								arr[c] = "+";
							}
						}
						map.set(r1, arr);
					}
				}
				else {
					break;
				}
			}
			
			for(int i = 0; i < R; i++) {
				String[] solution = map.get(i);
				for(int j = 0; j < C; j++) {
					out.print(solution[j]);
				}
				out.println();
			}
			out.println();
		}
		out.close();
	}
}
