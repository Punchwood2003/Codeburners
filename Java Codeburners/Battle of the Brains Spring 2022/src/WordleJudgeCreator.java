import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class WordleJudgeCreator {
	public static void main(String[] args) {
		try {
			new WordleJudgeCreator().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void run() throws IOException {
		this.createJudgeInFile("judge1", 10);
		this.createJudgeOutFile("judge1");
		this.createJudgeInFile("judge2", 100);
		this.createJudgeOutFile("judge2");
		this.createJudgeInFile("judge3", 1000);
		this.createJudgeOutFile("judge3");
	}
	private BufferedReader file;
	private PrintWriter out;
	private void createJudgeInFile(String fileName, int n) throws IOException {
		out = new PrintWriter(new File(fileName + "In.txt"));
		out.println(n);
		while(n-->0) {
			int willSolve = (int) (Math.random() * 2);
			int guessSolvedOn = -1; 
			if(willSolve == 1) {
				guessSolvedOn = (int) (Math.random() * 6);
			} 
			// Generate the secret word
			String W = this.generateWord();
			out.println(W);
			for(int i = 0; i < 6; i++) {
				if(i == guessSolvedOn) {
					out.println(W);
				}
				else {
					out.println(this.generateWord());
				}
			}
		}
		
		out.close();
	}
	private String generateWord() {
		String W = "";
		for(int i = 0; i < 5; i++) {
			W += (char) (((double) Math.random() * 26) + 'A');
 		}
		return W;
	}
	private void createJudgeOutFile(String fileName) throws IOException {
		file = new BufferedReader(new FileReader(fileName + "In.txt"));
		out = new PrintWriter(new File(fileName + "Out.txt"));
		// Determine the number of test cases
		int N = Integer.parseInt(file.readLine());
		for(int i = 1; i <= N; i++) {
			/**
			 * Reading in Data
			 */
			// Read in the secret word
			String secretWord = file.readLine();
			char[] W = secretWord.toCharArray();
			// Determine the number of each letter in W
			HashMap<Character, Integer> numEachLetterInW = new HashMap<Character, Integer>();
			for(char c : W) {
				int numEachLetter = 1;
				if(numEachLetterInW.containsKey(c)) {
					numEachLetter += numEachLetterInW.get(c);
				}
				numEachLetterInW.put(c, numEachLetter);
			}
			// Read in the guesses
			char[][] G = new char[6][5];
			for(int j = 0; j < 6; j++) {
				G[j] = file.readLine().toCharArray();
			}
			
			/**
			 * Processing Data
			 */
			// Determine the state of the board and the score
			char[][] board = new char[6][5];
			int numGuessesUsed = -1;
			// Iterate through the guesses
			for(int j = 0; j < 6; j++) {
				char[] Gn = G[j];
				HashMap<Character, Integer> tempELIW = new HashMap<Character, Integer>(numEachLetterInW);
				// This loop will populate the G's 
				for(int k = 0; k < 5; k++) {
					char ln = Gn[k];
					// If the guessed letter ln is in the correct spot
					if(ln == W[k]) {
						// Update the board
						board[j][k] = 'G';
						// Update the number of letters available 
						tempELIW.put(ln, tempELIW.get(ln)-1);
					}
				}
				// This loop will populate the Y's and X's
				for(int k = 0; k < 5; k++) {
					char ln = Gn[k];
					// If the guessed letter ln is in the word W and we have letters available
					if(numEachLetterInW.keySet().contains(ln) && tempELIW.get(ln) >= 1 && board[j][k] != 'G') {
						// Update the board
						board[j][k] = 'Y';
						// Update the number of letters available
						tempELIW.put(ln, tempELIW.get(ln)-1);
					}
					// The guessed letter ln is not in the word W or W does not contain any more of ln
					else {
						// Update the board
						if(board[j][k] != 'G') {
							board[j][k] = 'X';
						}
					}
				}
				// Determine if the word has been guessed yet
				boolean guessedCorrectly = true;
				for(int k = 0; k < 5; k++) {
					if(Gn[k] != W[k]) {
						guessedCorrectly = false;
						break;
					}
				}
				// If the word has been guessed, end the game
				if(guessedCorrectly) {
					numGuessesUsed = j+1;
					// If the board hasn't been completely filled yet
					if(j != 5) {
						for(int k = j+1; k < 6; k++) {
							for(int l = 0; l < 5; l++) {
								board[k][l] = 'W';
							}
						}
					}
					break;
				}
			}
			
			/**
			 * Printing Output
			 */
			getOutput(board, secretWord, numGuessesUsed, i);
		}
		out.close();
	}
	/**
	 * Prints out the game number, board state, the number of guesses used, any additional comments, and the points earned
	 * @param board				The board state represented as a 2D array of characters
	 * @param secretWord		The secret word W
	 * @param numGuessesUsed	The number of guesses used to correctly guess W
	 * @param i					The current game number
	 */
	private void getOutput(char[][] board, String secretWord, int numGuessesUsed, int i) {
		// Begin output
		out.println("Game #" + i + ":");
		for(int j = 0; j < 6; j++) {
			for(int k = 0; k < 5; k++) {
				out.print(board[j][k]);
			}
			out.println();
		}
		if(numGuessesUsed != -1) {
			out.println(secretWord + " was correctly guessed in " + numGuessesUsed + " guesses. " + getTag(numGuessesUsed) + "\n");
		}
		else {
			out.println(secretWord + " was not correctly guessed. Better luck next time.\n");
		}
	}
	/**
	 * Determines what additional comment is added to the output
	 * @param n	The number of guesses used to correctly guess the secret word W
	 * @return	The additional comment that is added to the output
	 */
	private String getTag(int n) {
		String tag = "";
		switch(n) {
			case 1: {tag = "Wow! Either you got incredibly lucky or you are cheating!"; break;}
			case 2: {tag = "Impressive! That didn't take you very long."; break;}
			case 3: {tag = "Nice! Not bad. Not bad at all."; break;}
			case 4: {tag = "Pretty decent."; break;}
			case 5: {tag = "Cutting it a little close there, but we like to see it."; break;}
			case 6: {tag = "Phew! That was a close one!"; break;}
		}
		return tag;
	}
}
