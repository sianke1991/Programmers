#include <bits/stdc++.h>
//sianke1991

using namespace std;

//인접리스트: adjList[i]는 노드 i에서 간선 한 번으로 갈 수 있는 노드 목록
vector<int> adjList[1000002];
//역방향 인접리스트: backAdjList[i] 는 간선 한 번에 노드 i로 갈 수 있는 노드 목록
vector<int> backAdjList[1000002];
//생성 노드
int excludeNode = 0;
//방문 처리
bool visited[1000002];

vector<int> makeEdge(int st, int ed) {
    vector<int> result;
    result.push_back(st);
    result.push_back(ed);
    return result;
}

/**
 * 입력받은 값을 시작점으로 하여 bfs를 수행하여,
 * 1 + 간선 개수 - 노드 개수의 결과를 반환한다.
 */
int bfs(int startNode) {
    //bfs를 수행하면서 만난 노드들
    vector<int> bfsNodes;
    //bfs를 수행하면서 만난 간선들
    vector<pair<int, int>> bfsEdges;
    queue<int> q;

    visited[startNode] = true;
    q.push(startNode);
    bfsNodes.push_back(startNode);

    while (!q.empty()) {
        int currentNode = q.front();
        q.pop();
        for (int nextNode:adjList[currentNode]) {
            bfsEdges.push_back({currentNode, nextNode});
            if (!visited[nextNode]) {
                visited[nextNode] = true;
                q.push(nextNode);
                bfsNodes.push_back(nextNode);
            }
        }
    } //while loop
    return 1 + bfsEdges.size() - bfsNodes.size();
}


vector<int> solution(vector<vector<int>> edges) {
    //인접리스트 구성
    for (vector<int> edge:edges) {
        adjList[edge[0]].push_back(edge[1]);
        backAdjList[edge[1]].push_back(edge[0]);
    } //edge loop

    //도넛 그래프, 막대 그래프, 팔자 그래프 합이 2개 이상이므로,
    //생성 노드는 [들어오는 간선은 없고, 나가는 간선은 2개 이상] 이라는 특징을 가진 유일한 노드가 된다.
    //도넛 그래프, 팔자 그래프를 구성하는 모든 노드는 반드시 들어오는 간선을 가지고 있다.
    //막대 그래프를 구성하는 노드 중 최대 하나는 들어오는 간선을 가지지 않을 수도 있으나, 해당 노드는 나가는 노드가 1개 있다.
    //따라서 특정 노드가 [들어오는 간선이 없고 나가는 간선이 2개 이상] 이라는 특징을 가진다면 해당 노드는 생성 노드가 된다.
    for (excludeNode=1; excludeNode<=1000000; excludeNode++) {
        if (adjList[excludeNode].size()>=2 && backAdjList[excludeNode].size()==0)
            break;
    } //excludeNode loop (생성 노드 루프)

    int numDounuts = 0;
    int numBars = 0;
    int numEights = 0;
    for (int startNode:adjList[excludeNode]) {
        //adjSet[excludeNode].erase(startNode);
        //backAdjSet[startNode].erase(excludeNode);
        int bfsResult = bfs(startNode);
        switch (bfsResult) {
            case 0:
                numBars++;
                break;
            case 1:
                numDounuts++;
                break;
            case 2:
                numEights++;
                break;
        }
    } //startNode loop (생성 노드와 연결된 노드 루프)
    vector<int> answer;
    answer.push_back(excludeNode);
    answer.push_back(numDounuts);
    answer.push_back(numBars);
    answer.push_back(numEights);
    return answer;
}




int main() {
    vector<vector<int>> edges;
    edges.push_back(makeEdge(4, 11));
    edges.push_back(makeEdge(1, 12));
    edges.push_back(makeEdge(8, 3));
    edges.push_back(makeEdge(12, 7));
    edges.push_back(makeEdge(4, 2));
    edges.push_back(makeEdge(7, 11));
    edges.push_back(makeEdge(4, 8));
    edges.push_back(makeEdge(9, 6));
    edges.push_back(makeEdge(10, 11));
    edges.push_back(makeEdge(6, 10));
    edges.push_back(makeEdge(3, 5));
    edges.push_back(makeEdge(11, 1));
    edges.push_back(makeEdge(5, 3));
    edges.push_back(makeEdge(11, 9));
    edges.push_back(makeEdge(3, 8));
    return 0;
}
