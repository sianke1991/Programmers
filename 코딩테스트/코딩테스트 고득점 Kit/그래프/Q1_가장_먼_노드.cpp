#include <bits/stdc++.h>

using namespace std;

struct Node {
    int number;
    int distance;
};

vector<int> adjList[20002];
bool hasVisited[20002];
//각 거리에 따른 노드의 개수
int distanceArray[20002];
int maxDistance = 0;

Node makeNode(int number, int distance) {
    Node result;
    result.number = number;
    result.distance = distance;
    return result;
}

int solution(int n, vector<vector<int>> edges) {
    for (vector<int> edge:edges) {
        adjList[edge[0]].push_back(edge[1]);
        adjList[edge[1]].push_back(edge[0]);
    }


    queue<Node> q;
    q.push(makeNode(1, 0));
    hasVisited[1] = true;
    distanceArray[0]++;
    while (!q.empty()) {
        Node currentNode = q.front();
        // printf("current node: %d\n", currentNode);
        q.pop();
        for (int nextNodeNumber:adjList[currentNode.number]) {
            if (!hasVisited[nextNodeNumber]) {
                q.push(makeNode(nextNodeNumber, currentNode.distance+1));
                hasVisited[nextNodeNumber] = true;
                distanceArray[currentNode.distance+1]++;
                maxDistance = currentNode.distance+1;
            }
        }
    } //while loop

    return distanceArray[maxDistance];
}

vector<int> makeVector(int a, int b) {
    vector<int> result;
    result.push_back(a); result.push_back(b);
    return result;
}

int main() {
    int n = 6;
    vector<vector<int>> edges;
    edges.push_back(makeVector(3, 6));
    edges.push_back(makeVector(4, 3));
    edges.push_back(makeVector(3, 2));
    edges.push_back(makeVector(1, 3));
    edges.push_back(makeVector(1, 2));
    edges.push_back(makeVector(2, 4));
    edges.push_back(makeVector(5, 2));

    printf("%d\n", solution(n, edges));
    return 0;
}
