/*
BEGIN ANNOTATION
PROBLEM URL: open.kattis.com/problems/rocketpoweredhovercraft
TAGS: Geometry, Math, Ternary Search, Trigonometry
EXPLANATION:
The first important observation to make is that the correct solution always consists of first turning some angle, then
moving in a circular arc, the moving in a straight line. Intuitively, this is because it is better to turn earlier, as
it has a greater impact on the final location than turning later. Call the angle turned counterclockwise before we start
moving forwards A.

Next, let us reduce the problem to a simpler case. Notice that we can set y to |y| without changing the answer, since
this simply changes the direction of any turns. Also, we can divide x, y, and v by the radius of the circular arc
formed by moving forward and turning at the same time. This radius is v / w, so x and y are multiplied by w / v and v
is set to w. Finally, now that v is equal to w, we can simply compute the minimum sum of A and the path length. The
answer is this sum times w. Finally, we can reduce the problem to one where A = 0 by rotating (x, y) clockwise by A.

A can be anywhere from 0 (where the hovercraft immediately starts moving forwards) to atan2(y, x) (where the hovercraft
turns to face the destination and then moves to it in a straight line). Assume we have some function to compute the sum of
A and the path length given A, x, and y, or infinity if no such path exists for the given value of A. This function is
unimodal when A is in this range, and it is guaranteed to have a finite answer for A = atan2(y, x). Thus, we can find the
minimum value using ternary search.

All we have left to do is come up with such a function. Let C be the angle turned in a circular arc (which is also the
length of that arc, since the radius is 1) and let L be the length of the line segment traversed after the circular arc.
Let O be the origin, M be (0, 1), Q be (x, y), and P be the point at which we stop moving in a circular arc and start moving
in a straight line. The circular arc is part of a circle centered at M with radius 1, which line PQ is tangent to. Consider
triangle MPQ. Angle P is a right angle, dist(M, P) is 1, and by the Pythagorean Theorem, dist(M, Q) is
sqrt(x^2 + (y - 1)^2). Thus, the other leg of the right triangle, PQ, has length L = sqrt(dist(M, Q)^2 - dist(M, P)^2)
= sqrt(x^2 + (y - 1)^2 - 1) = sqrt(x^2 + y^2 - 2y + 1 - 1) = sqrt(x^2 + y(y - 2)). If x^2 + y(y - 2) < 0, L is imaginary,
which means no such path exists; this corresponds to the case where Q is inside the circle, and the function should
return infinity. Otherwise, consider the angle from the positive x-axis to the line OP; using circle angle identities,
this can be shown to be C / 2. Now extend the segment OQ beyond Q until its y-coordinate is y. The triangle of this point,
O, and the point projected onto the x-axis is a right triangle with one leg of length y and the other of length x plus
the extra distance in the x-direction the segment was extended. By reflecting the segment PQ over the hypotenuse, we get
a segment of length L parallel with the x-axis that extends from Q to the leg connecting the extended point with its
x-axis projection. Thus, the other leg has length x + L. This means C/2 = atan2(y, x + L), or C = 2 * atan2(y, x + L).
Simply return A + C + L. (The values of x and y in this function assume A = 0, as discussed at the end of the first
paragraph.)
END ANNOTATION
*/

#include <iomanip>
#include <iostream>
#include <math.h>

using namespace std;

const double PI = 4 * atan(1);

double f(double x, double y)
{
	double L2 = x * x + y * (y - 2);
	if (L2 < 0)
		return INFINITY;
	double L = sqrt(L2);
	double C = 2 * atan2(y, L + x);
	return C + L;
}

double f(double A, double x, double y)
{
	double c = cos(A), s = -sin(A);
	double x1 = x * c - y * s;
	double y1 = x * s + y * c;
	return A + f(x1, y1);
}

int main()
{
	double x, y, v, w;
	cin >> x >> y >> v >> w;
	y = abs(y);
	double curvature = w / v; // 1 / radius
	x *= curvature;
	y *= curvature;
	double lo = 0, hi = atan2(y, x);
	cout << hi << endl;
	while (hi - lo > 1e-5)
	{
		double m1 = lo + (hi - lo) / 3;
		double m2 = m1 + (hi - lo) / 3;
		double f1 = f(m1, x, y);
		double f2 = f(m2, x, y);
		if (f1 < f2)
			hi = m2;
		else
			lo = m1;
	}
	cout << fixed << setprecision(4) << f((hi + lo) / 2, x, y) / w << endl;
}
