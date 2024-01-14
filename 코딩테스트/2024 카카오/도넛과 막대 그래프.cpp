#include <bits/stdc++.h>
//sianke1991

using namespace std;

//��������Ʈ: adjList[i]�� ��� i���� ���� �� ������ �� �� �ִ� ��� ���
vector<int> adjList[1000002];
//������ ��������Ʈ: backAdjList[i] �� ���� �� ���� ��� i�� �� �� �ִ� ��� ���
vector<int> backAdjList[1000002];
//���� ���
int excludeNode = 0;
//�湮 ó��
bool visited[1000002];

vector<int> makeEdge(int st, int ed) {
    vector<int> result;
    result.push_back(st);
    result.push_back(ed);
    return result;
}

/**
 * �Է¹��� ���� ���������� �Ͽ� bfs�� �����Ͽ�,
 * 1 + ���� ���� - ��� ������ ����� ��ȯ�Ѵ�.
 */
int bfs(int startNode) {
    //bfs�� �����ϸ鼭 ���� ����
    vector<int> bfsNodes;
    //bfs�� �����ϸ鼭 ���� ������
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
    //��������Ʈ ����
    for (vector<int> edge:edges) {
        adjList[edge[0]].push_back(edge[1]);
        backAdjList[edge[1]].push_back(edge[0]);
    } //edge loop

    //���� �׷���, ���� �׷���, ���� �׷��� ���� 2�� �̻��̹Ƿ�,
    //���� ���� [������ ������ ����, ������ ������ 2�� �̻�] �̶�� Ư¡�� ���� ������ ��尡 �ȴ�.
    //���� �׷���, ���� �׷����� �����ϴ� ��� ���� �ݵ�� ������ ������ ������ �ִ�.
    //���� �׷����� �����ϴ� ��� �� �ִ� �ϳ��� ������ ������ ������ ���� ���� ������, �ش� ���� ������ ��尡 1�� �ִ�.
    //���� Ư�� ��尡 [������ ������ ���� ������ ������ 2�� �̻�] �̶�� Ư¡�� �����ٸ� �ش� ���� ���� ��尡 �ȴ�.
    for (excludeNode=1; excludeNode<=1000000; excludeNode++) {
        if (adjList[excludeNode].size()>=2 && backAdjList[excludeNode].size()==0)
            break;
    } //excludeNode loop (���� ��� ����)

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
    } //startNode loop (���� ���� ����� ��� ����)
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
