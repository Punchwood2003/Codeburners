import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

public class Spock {
	public static void main(String[] args) throws IOException {
		new Spock().run();
	}

	public int[][][] states = new int[127][127][127];
	HashSet<Integer> continueSearching = new HashSet<Integer>();

	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		
		initStates();

		int numTimes = Integer.parseInt(file.readLine());
		while(numTimes-->0) {
			// Choose a random choice
			List<Integer> tempList = continueSearching.stream().toList();
			int index = tempList.get((int) (Math.random() * tempList.size()));
			int[] pos = getPosFromIndex(index);
			int predictedComputerChoice = states[pos[0]][pos[1]][pos[2]] % 5;
			System.out.println(beat(predictedComputerChoice));
			
			String actualComputerChoice = file.readLine();
			if(continueSearching.size() != 1) {
				HashSet<Integer> toRemove = new HashSet<Integer>();
				for(Integer currIndex : continueSearching) {
					int[] currPos = getPosFromIndex(currIndex);
					String predictedComputerMove = getMoveFromState(states[currPos[0]][currPos[1]][currPos[2]]);
					if(!predictedComputerMove.equals(actualComputerChoice)) {
						toRemove.add(currIndex);
					}
				}
				continueSearching.removeAll(toRemove);
			}
			
			updateStates();
		}
		
		file.close();
	}
	
	public String beat(int state) {
		switch(state % 5) {
		case 0: return "paper";
		case 1: return "scissors";
		case 2: return "Spock";
		case 3: return "rock";
		default: return "lizard";
		}
	}

	public String getMoveFromState(int state) {
		switch(state % 5) {
		case 0: return "rock";
		case 1: return "paper";
		case 2: return "scissors";
		case 3: return "lizard";
		default: return "Spock";
		}
	}
	
	public int[] getPosFromIndex(int index) {
		int x = index / (127 * 127);
		index -= x * 127 * 127;
		int a = index / (127);
		index -= a * 127;
		int b = index;
		return new int[] {x, a, b};
	}
	
	public int getIndex(int x, int a, int b) {
		return (127 * 127 * x) + (127 * a) + b;
	}

	public void initStates() {
		for(int x = 0; x < 127; x++) {
			for(int a = 0; a < 127; a++) {
				for(int b = 0; b < 127; b++) {
					states[x][a][b] = (a * x + b) % 127;
					continueSearching.add(getIndex(x, a, b));
				}
			}
		}
	}

	public void updateStates() {
		for(Integer index : continueSearching) {
			int[] res = getPosFromIndex(index);
			int x = res[0];
			int a = res[1];
			int b = res[2];
			states[x][a][b] = (a * states[x][a][b] + b) % 127;
		}
	}
}
