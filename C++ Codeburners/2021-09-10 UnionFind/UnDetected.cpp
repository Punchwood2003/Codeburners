#include <vector>
#include <iostream>
#include <cmath>
#include <iomanip>
#include <string>
#include <float.h>

#define EPSILON 0.000001

using namespace std;

class UnionFind {
    public: 
    vector<int> p;
    UnionFind(int n) {
        p.resize(n);
        fill(p.begin(), p.end(), -1);
    }
    int Find(int x) {
        if(p.at(x) < 0) {
            return x;
        }
        int px = Find(p.at(x));
        p.at(x) = px;
        return px;
    }
    bool Union(int x, int y) {
        int px = Find(x);
        int py = Find(y);
        if(px == py) {
            return false;
        }
        if(p.at(px) < p.at(py)) {
            int save = px;
            px = py;
            py = save;
        }
        p.at(py) += p.at(px);
        p.at(px) = py;
        return true;
    }
};

class Shape {
    public: 
    bool isCircle; 
    double x, y, r, w, dr;
    Shape() {
        x = 0;
        y = 0;
        r = 0;
        w = 0;
        dr = 0;
        isCircle = true;
    }
    Shape(double x0, double y0, double r0, double w0, double dr0) {
        x = x0;
        y = y0;
        r = r0;
        w = w0;
        dr = dr0;
        isCircle = true;
    }
    Shape(double x0, double w0, double dr0) {
        x = x0;
        w = w0;
        dr = dr0; 
        isCircle = false;
    }
    bool Intersect(Shape& other) {
        if(isCircle && other.isCircle) {
            double d = sqrt(pow(x - other.x, 2) + pow(y - other.y, 2));
            return r + other.r >= d;
        }
        else if(isCircle) {
            if(Equals(other.x, dr)) {
                return x - r <= other.dr;
            }
            return x + r >= other.w - other.dr;
        }
        else if(other.isCircle) {
            if(Equals(x, dr)) {
                return other.x - other.r <= dr;
            }
            return other.x + other.r >= w - dr;
        }
        return false;
    }
    bool Equals(double a, double b) {
        return fabs(a - b) < EPSILON;
    }
};

bool PathExists(Shape circles[], int length) {
    UnionFind unionFind = UnionFind(length);
    vector<int> p = unionFind.p;

    for(int i = 0; i < length - 1; i++) {
        Shape node1 = circles[i];
        for(int j = i + 1; j < length; j++) {
            Shape node2 = circles[j];
            if(node1.Intersect(node2)) {
                unionFind.Union(i, j);
            }
        }
    }
    int find1 = unionFind.Find(0);
    int find2 = unionFind.Find(length-1);
    return find1 != find2;
}

double BinarySearch(Shape original[], double w, int length) {
    double high = w / 2.0;
    double low = 0.0;
    double mid = (high + low) / 2.0;

    Shape leftBoundary = Shape(0, w, mid);
    Shape rightBounday = Shape(w, w, mid);
    
    double bestDeltaR = DBL_MIN;

    for(int i = 0; i < 31; i++) {
        mid = (high + low) / 2.0;
        
        Shape testShape[length];
        testShape[0] = Shape(leftBoundary.x + mid, w, mid);
        testShape[length - 1] = Shape(rightBounday.x - mid, w, mid);
        
        for(int j = 1; j < length-1; j++) {
            Shape originalShape = original[j];
            testShape[j] = Shape(originalShape.x, originalShape.y, originalShape.r + mid, w, mid);
        }

        bool testPass = PathExists(testShape, length);
        if(testPass) {
            bestDeltaR = max(bestDeltaR, mid);
            low = mid;
        }
        else {
            high = mid;
        }
    }

    return bestDeltaR;
}

void GetFormattedOutput(double d) {
    char buffer[100008];
    int numCharacters = sprintf(buffer, "%.6f", d);
    string output = "";
    int i;
    for(i = numCharacters-1; i >= 0; i--) {
        if(buffer[i] != '0') {
            break;
        }
    }
    for(int k = 0; k <= i; k++) {
        cout << buffer[k];
    }
    cout << endl;
}

int main () {
    int numTimes; 
    cin >> numTimes;
    while(numTimes-->0) {
        double w;
        int numCircles; 
        cin >> w >> numCircles;
        Shape original[numCircles+2];
        original[0] = Shape(0, w, 0);
        original[numCircles + 1] = Shape(w, w, 0);

        for(int i = 1; i <= numCircles; i++) {
            int x, y, r;
            cin >> x >> y >> r;
            original[i] = Shape(x, y, r, 0, 0);
        }
        if(!PathExists(original, numCircles + 2)) {
            cout << 0 << endl;
        }
        else {
            GetFormattedOutput(BinarySearch(original, w, numCircles + 2));
        }
    }
}