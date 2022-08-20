import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Amanda {
	public static void main(String[] args) throws IOException {
		new Amanda().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		// Read in the number of locations, n, and the number of conditions, m
		String[] line = file.readLine().split(" ");
		int n = Integer.parseInt(line[0]), m = Integer.parseInt(line[1]);
		
		// Split the input into the three types of conditions
		byte[] conditions = new byte[n];
		boolean possible = true;
		Arrays.fill(conditions, (byte) -1);
		ArrayList<int[]> toSearch = new ArrayList<int[]>();

		/*
		 * The values of conditions will be either 0, 1, or -1, 
		 * where the value -1 indicates a city that has not been conditioned yet,
		 * 1 indicates a city that must have a lounge, and 0 indicates a city
		 * that cannot have a lounge.
		 */
		
		// Iterate through m conditions
		while(m-->0) {
			line = file.readLine().split(" ");
			int a = Integer.parseInt(line[0]) - 1, b = Integer.parseInt(line[1]) - 1, c = Integer.parseInt(line[2]);
			if(c == 0) {
				if(conditions[a] == 1 || conditions[b] == 1) {
					possible = false; 
					break;
				} else {
					conditions[a] = conditions[b] = (byte) 0;
				}
			} else if(c == 2) {
				if(conditions[a] == 0 || conditions[b] == 0) {
					possible = false;
					break;
				} else {
					conditions[a] = conditions[b] = (byte) 1;
				}
			} else {
				if(conditions[a] != -1) {
					conditions[b] = (byte) ((conditions[a] + 1) % 2);
				} else if(conditions[b] != -1) {
					conditions[a] = (byte) ((conditions[b] + 1) % 2);
				} else {
					boolean less = a < b;
					toSearch.add(new int[] {(less ? a : b), (less ? b : a)});
				}
			}
		}
		file.close();
		
		// If we have already determined that it is impossible to complete, then pass over this
		all: if(possible) {
			// Use the previous information of c=2s and c=0s to prematurely determine some of the c=1s
			// We can dramatically decrease the amount of graph bipartite searches that are needed by
			// iterating this algorithm multiple times until we have the simplest remaining connected components
			int previousNumberOfUnsolvedConditions = Integer.MAX_VALUE;
			ArrayList<int[]> toSearch = new ArrayList<int[]>();
			while(eitherAB.size() != previousNumberOfUnsolvedConditions) {
				for(int[] pair : eitherAB) {
					if(conditions[pair[0]] == (byte) 1 && conditions[pair[1]] == (byte) -1) {
						// If the first city has already been assigned a lounge, then unassign a lounge from the second city
						conditions[pair[1]] = (byte) 0;
					} else if(conditions[pair[1]] == (byte) 1 && conditions[pair[0]] == (byte) -1) {
						// If the second city has already been assigned a lounge, then unassign a lounge from the first city
						conditions[pair[0]] = (byte) 0;
					} else if(conditions[pair[0]] == (byte) 0 && conditions[pair[1]] == (byte) -1) {
						// If the first city has already been unassigned a lounge, then assign a lounge to the second city
						conditions[pair[1]] = (byte) 1;
					} else if(conditions[pair[1]] == (byte) 0 && conditions[pair[0]] == (byte) -1) {
						// If the second city has already been unassigned a lounge, then assign a lounge to the first city
						conditions[pair[0]] = (byte) 1;
					} else if (conditions[pair[0]] == conditions[pair[1]] && conditions[pair[0]] != (byte) -1) {
						// If both cities are equal to each other and they have been assigned already, then we have a conflict
						possible = false;
						break all;
					} else if(conditions[pair[0]] == conditions[pair[1]] && conditions[pair[0]] == (byte) -1) {
						// If there isn't anything that we can do with this at the moment, then possibly query again
						toSearch.add(pair);
					}
				}
				// Update the number of unsolved conditions after this past iteration
				previousNumberOfUnsolvedConditions = eitherAB.size();
				// Update the list of conditions that still need to be solved
				eitherAB = toSearch;
				// Reset the temporary list
				toSearch = new ArrayList<int[]>();
			}
			
			// If toSearch is empty, then all outstanding conflicts and uncertainty surrounding
			// the "either" test cases have been resolved which means a solution is known
			// Otherwise, then we have obtained the simplest set of connected components, 
			// and we can now run graph bipartite testing to determine if it is possible and 
			// what the optimal number of lounges is for each remaining connected component
			if(eitherAB.size() == 0) {
				possible = true;
			}
			else {
				// Initialize a graph of the remaining nodes to be searched
				HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
				for(int[] pair : eitherAB) {
					HashSet<Integer> set1, set2;
					if(graph.containsKey(pair[0])) {
						set1 = graph.get(pair[0]);
					}
					else {
						set1 = new HashSet<Integer>();
					}
					set1.add(pair[1]);
					graph.put(pair[0], set1);
					if(graph.containsKey(pair[1])) {
						set2 = graph.get(pair[1]);
					}
					else {
						set2 = new HashSet<Integer>();
					}
					set2.add(pair[0]);
					graph.put(pair[1], set2);
				}
				
				// Until no more pairs are remaining...
				while(!eitherAB.isEmpty()) {
					// Choose the first remaining element in the list, and use that as the start of a graph bipartite check
					int start = eitherAB.get(0)[0];
					// Perform the bipartite check on that connected component
					BFS bfs = new BFS(start, conditions, graph);
					// If a graph bipartite was determined to be possible...
					if(bfs.possible) {
						// update the conditions to be the optimal solution
						conditions = bfs.solution();
						// retrieve which conflicts were resolved
						HashSet<String> visited = bfs.elementsSearched();
						toSearch = new ArrayList<int[]>();
						// add unresolved conflicts to the toSearch list
						for(int i = 0; i < eitherAB.size(); i++) {
							int[] pair = eitherAB.get(i);
							boolean less = pair[0] < pair[1];
							String temp = (less ? pair[0] : pair[1]) + " " + (less ? pair[1] : pair[0]);
							if(!visited.contains(temp)) {
								toSearch.add(pair);
							}
						}
						// update eitherAB to have the resolved conflicts removed from the list
						eitherAB = toSearch;
					}
					else {
						// If a graph bipartite was determined to be impossible, then there is no solution
						possible = false;
						break all;
					}
				}
			}
		}
		
		// Determine the output
		if(possible) {
			int minLounges = 0;
			for(int condition : conditions) {
				minLounges += condition == 1 ? 1 : 0;
			}
			out.println(minLounges);
		}
		else {
			out.println("impossible");
		}
		out.close();
	}
}

class BFS {
	// Handles an individual city being searched
	class Query {
		public int id;
		public boolean previous; 
		public Query(int id, boolean previous) {
			this.id = id;
			this.previous = previous;
		}
	}
	public int total1, total2;
	public Queue<Query> toSearch;
	public HashSet<String> searched;
	public HashMap<Integer, HashSet<Integer>> graph;
	public byte[] values1, values2;
	public boolean possible;
	public BFS(int start, byte[] values, HashMap<Integer, HashSet<Integer>> graph) {
		this.values1 = Arrays.copyOf(values, values.length);
		this.values2 = Arrays.copyOf(values, values.length);
		this.graph = graph;
		this.toSearch = new LinkedList<Query>();
		this.searched = new HashSet<String>();
		this.possible = true;
		this.solve(start);
	}
	private void solve(int start) {
		// create a Query object for the start of the search
		Query query = new Query(start, true);
		// Add it to the BFS toSearch Queue
		toSearch.add(query);
		// Until all 
		while(!toSearch.isEmpty()) {
			Query currQ = toSearch.poll();
			int curr = currQ.id;
			boolean previous = currQ.previous;
			if(values1[curr] == (byte) -1) {
				values1[curr] = previous ? (byte) 0 : (byte) 1;
				total1 += previous ? 0 : 1;
			}
			else if((values1[curr] == (byte) 1 && previous) || (values1[curr] == (byte) 0 && !previous)) {
				this.possible = false;
				break;
			}
			if(values2[curr] == (byte) -1) {
				values2[curr] = previous ? (byte) 1 : (byte) 0;
				total2 += previous ? 1 : 0;
			}
			else if((values2[curr] == (byte) 1 && !previous) || (values2[curr] == (byte) 0 && previous)) {
				this.possible = false;
				break;
			}
			
			for(int next : this.graph.get(curr)) {
				boolean less = curr < next;
				String temp = (less ? curr : next) + " " + (less ? next : curr);
				if(!this.searched.contains(temp)) {
					this.toSearch.add(new Query(next, !previous));
					this.searched.add(temp);
				}
			}
		}
	}
	public boolean possible() {
		return this.possible;
	}
	public byte[] solution() {
		return this.total1 < this.total2 ? this.values1 : this.values2;
	}
	public HashSet<String> elementsSearched() {
		return this.searched;
	}
}