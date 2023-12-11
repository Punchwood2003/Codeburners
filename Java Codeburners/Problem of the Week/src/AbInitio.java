import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AbInitio {
	private class Graph {
		private int V;
		private ArrayList<Boolean> listTemplate;
		private ArrayList<ArrayList<Boolean>> graph;

		public Graph(int V) {
			this.V = V;
			graph = new ArrayList<ArrayList<Boolean>>();

			listTemplate = new ArrayList<Boolean>(V);
			for(int i = 0; i < V; i++) {
				listTemplate.add(false);
			}

			graph = new ArrayList<ArrayList<Boolean>>();
			for(int i = 0; i < V; i++) {
				graph.add(new ArrayList<Boolean>(listTemplate));
			}
		}

		public void performOperation(int i, StringTokenizer st) {
			switch(i) {
			case 1: {
				addVertex();
				return;
			}
			case 2: {
				addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
				return;
			}
			case 3: {
				removeAllEdgesToAndFromV(Integer.parseInt(st.nextToken()));
				return;
			}
			case 4: {
				removeEdgeFromAToB(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
				return;
			}
			case 5: {
				transpose();
				return;
			}
			case 6: {
				complement();
				return;
			}
			}
		}

		private void addVertex() {
			for(int i = 0; i < V; i++) {
				ArrayList<Boolean> adjList = graph.get(i);
				adjList.add(false);
				graph.set(i, adjList);
			}

			listTemplate.add(false);
			graph.add(new ArrayList<Boolean>(listTemplate));

			V++;
		}

		private void addEdge(int a, int b) {
			ArrayList<Boolean> adjListA = graph.get(a);
			adjListA.set(b, true);
			graph.set(a, adjListA);
		}

		private void removeAllEdgesToAndFromV(int v1) {
			ArrayList<Boolean> adjListV1 = graph.get(v1);
			for(int i = 0; i < V; i++) {
				adjListV1.set(i, false);

				ArrayList<Boolean> adjListVi = graph.get(i);
				adjListVi.set(v1, false);
				graph.set(i, adjListVi);
			}
			graph.set(v1, adjListV1);
		}

		private void removeEdgeFromAToB(int a, int b) {
			ArrayList<Boolean> adjListA = graph.get(a);
			adjListA.set(b, false);
			graph.set(a, adjListA);
		}

		private void transpose() {
			for(int a = 0; a < V; a++) {
				ArrayList<Boolean> adjListA = graph.get(a);
				for(int b = a+1; b < V; b++) {
					ArrayList<Boolean> adjListB = graph.get(b);

					boolean temp = adjListA.get(b);
					adjListA.set(b, adjListB.get(a));
					adjListB.set(a, temp);

					graph.set(b, adjListB);
				}
				graph.set(a, adjListA);
			}
		}

		private void complement() {
			for(int a = 0; a < V; a++) {
				ArrayList<Boolean> adjListA = graph.get(a);
				for(int b = 0; b < V; b++) {
					if(a == b) {
						continue;
					}

					adjListA.set(b, !adjListA.get(b));
				}
				graph.set(a, adjListA);
			}
		}

		public void print(PrintWriter out) {
			out.println(V);

			BigInteger mod = new BigInteger("1000000007");
			BigInteger seven = new BigInteger(String.format("%d", 7));
			for(int a = 0; a < V; a++) {
				BigInteger sum = BigInteger.ZERO;
				BigInteger i = BigInteger.ZERO;
				for(int b = 0; b < V; b++) {
					BigInteger bbi = new BigInteger(String.format("%d", b));
					if(graph.get(a).get(b)) {
						sum = sum.add(fastModExpo(seven, i, mod).multiply(bbi.mod(mod))).mod(mod);
						i = i.add(BigInteger.ONE);
					}
				}
				
				out.println(String.format("%d %d", i, sum));
			}
		}

		private BigInteger fastModExpo(BigInteger a, BigInteger b, BigInteger mod) {
			BigInteger res = BigInteger.ONE; 
			a = a.mod(mod); 

			if (a.equals(BigInteger.ZERO)) {
				return BigInteger.ZERO;
			}

			while (b.signum() > 0) {
				if (!b.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
					res = res.multiply(a).mod(mod);
				}
				b = b.shiftRight(1);
				a = a.multiply(a).mod(mod);
			}

			return res;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[\n");
			for(ArrayList<Boolean> a : graph) {
				sb.append("\t");
				sb.append(a.toString().replaceAll("true", "1").replaceAll("false", "0"));
				sb.append("\n");
			}
			sb.append("]\n");

			return sb.toString();
		}
	}

	public static void main(String[] args) throws IOException {
		new AbInitio().run();
	}

	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(file.readLine());

		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());

		Graph graph = new Graph(V);

		while(E-->0) {
			graph.performOperation(2, new StringTokenizer(file.readLine()));
		}

		while(Q-->0) {
			st = new StringTokenizer(file.readLine());
			graph.performOperation(Integer.parseInt(st.nextToken()), st);
		}

		file.close();

		graph.print(out);

		out.close();
	}
}
