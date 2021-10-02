import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class RunningMoM {
	
	public static void main(String[] args) throws IOException {
		new RunningMoM().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());
		int N = Integer.parseInt(st.nextToken());
		TreeMap<String, TreeSet<String>> connections = new TreeMap<String, TreeSet<String>>();
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(file.readLine());
			String city1 = st.nextToken();
			String city2 = st.nextToken();
			TreeSet<String> currConnections;
			if(connections.containsKey(city1)) {
				currConnections = connections.get(city1);
			}
			else {
				currConnections = new TreeSet<String>();
			}
			if(!connections.containsKey(city2)) {
				connections.put(city2, new TreeSet<String>());
			}
			currConnections.add(city2);
			connections.put(city1, currConnections);
		}
		TreeSet<String> safeCities = new TreeSet<String>();
		for(String currCity : connections.keySet()) {
			TreeSet<String> connectionsFromCurrCity = new TreeSet<String>();
			if(dfs(currCity, connections, connectionsFromCurrCity)) {
				safeCities.add(currCity);
			}
		}
		String test = file.readLine();
		while(test != null) {
			out.println(test + " " + (safeCities.contains(test) ? "safe" : "trapped"));
			test = file.readLine();
		}
		out.close();
		file.close();
	}
	public boolean dfs(String currCity, TreeMap<String, TreeSet<String>> connections, TreeSet<String> currConnections) {
		if(currConnections.contains(currCity)) {
			return true;
		}
		currConnections.add(currCity);
		TreeSet<String> testConnections = connections.get(currCity);
		if(testConnections.size() == 0) {
			currConnections.remove(currCity);
			return false;
		}
		for(String temp : testConnections) {
			boolean result = dfs(temp, connections, currConnections);
			if(result) {
				return true;
			}
		}
		currConnections.remove(currCity);
		return false;
	}
} 
