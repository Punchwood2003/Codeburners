#include <iostream>
#include <vector>
#include <unordered_map>
#include <sstream>
#include <algorithm>
#include <limits>

class Guilds {
private:
    /**
     * This class handles two actions:
     *  - Taking the Union of two sets
     *  - Finding the set in which an element is a part of
     */
    class UnionFind {
    private:
        std::vector<int> p;

    public:
        UnionFind(int n) {
            p.resize(n, -1);
        }

        int find(int x) {
            if (p[x] < 0) {
                return x;
            }
            int px = find(p[x]);
            p[x] = px;
            return px;
        }

        bool unionSet(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) {
                return false;
            }
            p[py] += p[px];
            p[px] = py;
            return true;
        }
    };

public:
    void run() {
        std::ios_base::sync_with_stdio(false);
        std::cin.tie(nullptr);

        int numConnections;
        std::cin >> numConnections;
        UnionFind uf(numConnections * 2);
        std::unordered_map<std::string, int> strToPos;
        std::unordered_map<int, std::string> posToStr;
        std::vector<std::string> start(numConnections);
        int next = 0;

        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        for (int i = 0; i < numConnections; i++) {
            std::string line;
            std::getline(std::cin, line);
            std::istringstream iss(line);
            iss >> start[i];
            if (strToPos.find(start[i]) == strToPos.end()) {
                strToPos[start[i]] = next;
                posToStr[next++] = start[i];
            }

            std::string parent;
            iss >> parent;
            if (strToPos.find(parent) == strToPos.end()) {
                strToPos[parent] = next;
                posToStr[next++] = parent;
            }

            uf.unionSet(strToPos[start[i]], strToPos[parent]);
        }

        for (int i = 0; i < numConnections; i++) {
            std::cout << start[i] << " " << posToStr[uf.find(strToPos[start[i]])] << '\n';
        }
    }
};

int main() {
    Guilds guilds;
    guilds.run();
    return 0;
}
