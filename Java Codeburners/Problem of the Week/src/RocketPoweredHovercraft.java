import java.io.IOException;
import java.util.Scanner;

public class RocketPoweredHovercraft {
	public static void main(String[] args) throws IOException {
		new RocketPoweredHovercraft().run();
	}
	public void run() throws IOException {
		Scanner scan = new Scanner(System.in);

		double destinationX = scan.nextInt();
		/* Note: because we can turn both clockwise and counter-clockwise, 
		 * we can just take the |y| and just assume that we are always 
		 * moving in a counter-clockwise position
		 */
		double destinationY = Math.abs(scan.nextInt());

		// Take in v, w, and calculate r
		double v = scan.nextDouble();
		double w = scan.nextDouble();
		double r = w / v;

		/*
		 * This needs more explaining
		 * Note: this is used for scaling with the circle that we are
		 * rotating on
		 */
		destinationX *= r;
		destinationY *= r;

		/*
		 * Now we will search for the angle that we initially rotate 
		 * before we start moving. This can be done with a binary search
		 * with some epsilon value. Seeing how the precision of this problem
		 * is 1e-3, we will use 1e-5 to ensure that we meet that precision
		 * We will then do a binary search a finite number of times, say 200 
		 * times to ensure that we hit roughly our target precision. 
		 */
		double low = 0;
		double high = Math.atan2(destinationY, destinationX);
		double epsilon = Math.pow(10, -5);
		while (high - low > epsilon) {
			double mid1 = low + (high - low) / 3;
			double mid2 = mid1 + (high - low) / 3;
			// Determine the total distance traveled with each decision
			double distance1 = getTotalDistance(mid1, destinationX, destinationY);
			double distance2 = getTotalDistance(mid2, destinationX, destinationY);
			// Determine which one is more optimal
			if(distance1 < distance2) {
				high = mid2;
			}
			else {
				low = mid1; 
			}
		}
		// Recalculate the initial amount turned
		double mid = (high + low) / 2; 
		/* Calculate the time needed to turn some amount, 
		 * start moving in a circular arc, and then finish 
		 * by moving straight at the destination point.
		 */
		double t = getTotalDistance(mid, destinationX, destinationY) / w;
		System.out.printf("%.8f", t);
	}
	double getTotalDistance(double startingAngle, double x, double y) {
		// Determine the transformation to apply to x and y
		double theta_x = Math.cos(startingAngle);
		double theta_y = -Math.sin(startingAngle);
		// Apply the transformation to x and y
		double newX = (x * theta_x) - (y * theta_y);
		double newY = (x * theta_y) + (y * theta_x);
		// Return the total distance traveled
		return startingAngle + getArcDistance(newX, newY);
	}
	double getArcDistance(double x, double y) {
		double distanceGoingStraightSquared = (x * x) + (y * (y - 2));
		if(distanceGoingStraightSquared < 0) {
			return Double.POSITIVE_INFINITY;
		}
		double distanceGoingStraight = Math.sqrt(distanceGoingStraightSquared);
		double distanceOnArc = 2 * Math.atan2(y, distanceGoingStraight + x);
		return distanceGoingStraight + distanceOnArc;
	}
}
