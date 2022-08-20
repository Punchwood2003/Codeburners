import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class AwfulCipher {
	private HashMap<String, Character> morseToEnglish;
	private HashMap<Integer, String> decimalToRoman; 
	private int[] units = {50, 40, 10, 9, 5, 4, 1};
	
	public static void main(String[] args) throws IOException {
		new AwfulCipher().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		this.morseToEnglish = this.getMorseTranslate();
		this.decimalToRoman = this.getRomanTranslate();
		
		String[] line = file.readLine().split(" ");
		int K = Integer.parseInt(line[0]);
		String KRoman = this.getRoman(K);
		char[] chars = this.morse(line);
		for(int i = 0; i < 26; i++) {
			String toString = this.toString(chars);
			if(toString.contains(KRoman)) {
				out.println(toString);
				break;
			}
			chars = this.shift(chars);
		}
		out.close();
		file.close();
	} 
	private String toString(char[] chars) {
		String output = "";
		for(char curr : chars) {
			output += curr;
		}
		return output;
	}
	private char[] shift(char[] chars) {
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] + 1 > 'Z') {
				chars[i] = 'A';
			}
			else {
				chars[i]++;
			}
		}
		return chars;
	}
	private String getRoman(int K) {
		String roman = "";
		for(int curr : units) {
			int remainder = K % curr;
			int divides = K / curr;
			K = remainder;
			if(divides > 0) {
				while(divides-->0) {
					roman += this.decimalToRoman.get(curr);
				}
			}
		}
		return roman;
	}
	private char[] morse(String[] arr) {
		char[] chars = new char[arr.length-1];
		for(int i = 0; i < chars.length; i++) {
			chars[i] = this.morseToEnglish.get(arr[i+1]);
		}
		return chars; 
	}
	private HashMap<String, Character> getMorseTranslate() {
		HashMap<String, Character> morseToEnglish = new HashMap<String, Character>();
		morseToEnglish.put(".-", 'A');
		morseToEnglish.put("-...", 'B');
		morseToEnglish.put("-.-.", 'C');
		morseToEnglish.put("-..", 'D');
		morseToEnglish.put(".", 'E');
		morseToEnglish.put("..-.", 'F');
		morseToEnglish.put("--.", 'G');
		morseToEnglish.put("....", 'H');
		morseToEnglish.put("..", 'I');
		morseToEnglish.put(".---", 'J');
		morseToEnglish.put("-.-", 'K');
		morseToEnglish.put(".-..", 'L');
		morseToEnglish.put("--", 'M');
		morseToEnglish.put("-.", 'N');
		morseToEnglish.put("---", 'O');
		morseToEnglish.put(".--.", 'P');
		morseToEnglish.put("--.-", 'Q');
		morseToEnglish.put(".-.", 'R');
		morseToEnglish.put("...", 'S');
		morseToEnglish.put("-", 'T');
		morseToEnglish.put("..-", 'U');
		morseToEnglish.put("...-", 'V');
		morseToEnglish.put(".--", 'W');
		morseToEnglish.put("-..-", 'X');
		morseToEnglish.put("-.--", 'Y');
		morseToEnglish.put("--..", 'Z');
		return morseToEnglish;
	}
	private HashMap<Integer, String> getRomanTranslate() {
		HashMap<Integer, String> decimalToRoman = new HashMap<Integer, String>();
		decimalToRoman.put(1, "I");
		decimalToRoman.put(4, "IV");
		decimalToRoman.put(5, "V");
		decimalToRoman.put(9, "IX");
		decimalToRoman.put(10, "X");
		decimalToRoman.put(40, "XL");
		decimalToRoman.put(50, "L");
		return decimalToRoman;
	}
}