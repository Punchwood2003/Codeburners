import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FlightCollision {
	class Drone {
		public double x, v;
		public int i; 
		public Drone(double x, double v, int i) {
			this.x = x;
			this.v = v; 
			this.i = i;
		}
		public void updateX(double t) {
			this.x += this.v * t;
		}
		public String toString() {
			return "\nDrone: " + i + "\nx: " + this.x + "\nv: " + this.v;
		}
	}
	class Collision {
		public Drone d1, d2; 
		public double t; 
		public boolean willCollide; 
		public Collision(Drone d1, Drone d2) {
			this.d1 = d1;
			this.d2 = d2;
			this.instantiate();
		}
		private void instantiate() {
			double x1 = d1.x;
			double x2 = d2.x;
			double v1 = d1.v;
			double v2 = d2.v;
			// If they will never collide
			boolean movingAwayFromEachOther = (x1 < x2 && v1 <= 0 && v2 >= 0);
			boolean bothAreStationary = (v1 == 0 && v2 == 0);
			boolean bothAreMovingAtTheSameVelocity = (v1 == v2);
			boolean droneInFrontIsMovingFasterThanDroneBehindInSameDirection = (v1 > 0 && v2 > 0 && v2 > v1) || (v1 < 0 && v2 < 0 && v2 < v1); 
			if(movingAwayFromEachOther || bothAreStationary || bothAreMovingAtTheSameVelocity || droneInFrontIsMovingFasterThanDroneBehindInSameDirection) {
				t = -1;
				willCollide = false;
			}
			// They will collide
			else {
				t = (x1-x2) / (v2-v1); 
				willCollide = true;
			}
		}
		public String toString() {
			if(willCollide) {
				return "\nDrone " + this.d1.i + " collides with Drone " + this.d2.i + " at t=" + this.t;
			}
			return "\nDrone " + this.d1.i + " will never collide with Drone " + this.d2.i;
		}
	}
	public static void main(String[] args) throws IOException {
		new FlightCollision().run();
	}
	public void run() throws IOException {
		BufferedReader file = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st;
		int numDrones = Integer.parseInt(file.readLine());
		Drone[] drones = new Drone[numDrones];
		for(int i = 0; i < numDrones; i++) {
			st = new StringTokenizer(file.readLine());
			drones[i] = new Drone(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()), i);
		}
		int numDronesRemaining = numDrones;
		double[] dronesToRemove = getDronesToRemove(drones);
		int drone1 = (int) dronesToRemove[0];
		int drone2 = (int) dronesToRemove[1];
		while(drone1 != -1 && drone2 != -1 && numDronesRemaining != 1) {
			//System.out.prsintln("Removing Drones " + drone1 + " and " + drone2 + " at t = " + dronesToRemove[2]);
			drones[drone1] = null;
			drones[drone2] = null;
			numDronesRemaining -= 2;
			drones = updateDronePosition(drones, dronesToRemove[2]);
			//System.out.println("New Drones Array: " + Arrays.toString(drones));
			dronesToRemove = getDronesToRemove(drones);
			drone1 = (int) dronesToRemove[0];
			drone2 = (int) dronesToRemove[1];
		}
		out.println(numDronesRemaining);
		for(int i = 0; i < numDrones; i++) {
			if(drones[i] != null) {
				out.print((i+1) + " ");
			}
		}
		out.close();
		file.close();
	}
	public double[] getDronesToRemove(Drone[] drones) {
		double[] output;
		int i = 0, j = 1;
		double minT = Double.MAX_VALUE;
		int minI = -1, minJ = -1;
		while(i < drones.length-1 && j < drones.length) {
			if(drones[i] == null) {
				i++;
				continue;
			}
			else if(drones[j] == null) {
				j++;
				continue;
			}
			else {
				Drone d1 = drones[i];
				Drone d2 = drones[j];
				Collision c = new Collision(d1, d2);
				if(c.willCollide && c.t < minT) {
					minT = c.t;
					minI = i;
					minJ = j;
				}
				i = j;
				j++;
			}
		}
		output = new double[] {minI, minJ, minT};
		return output;
	}
	public Drone[] updateDronePosition(Drone[] drones, double t) {
		for(int i = 0; i < drones.length; i++) {
			if(drones[i] != null) {
				drones[i].updateX(t);
			}
		}
		return drones;
	}
}
