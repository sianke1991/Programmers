#include <bits/stdc++.h>

using namespace std;

//승패 결과를 정리하는 테이블
//matchResults[i][j]가 양일 경우 i는 j에게 승리한다.
//matchResults[i][j]가 음일 경우 i는 j에게 패배한다.
//matchResults[i][j]가 i와 j 간에 승패 정보를 확정할 수 없다.
//편의를 위해 matchResult[i][i]는 1로 둔다.
int matchResults[101][101];

int solution(int n, vector<vector<int>> results) {
    for (vector<int> result:results) {
        matchResults[result[0]][result[1]] = 1;
        matchResults[result[1]][result[0]] = -1;
    }

    //floyd algorithm
    //s와 t 사이에 승패 정보가 확정되어 있으면 갱신할 필요가 없다.
    //k라는 매개 노드에 대해 s가 k를 이기고, k가 t를 이긴다면 s는 t를 이기는 것이다.
    //반면에 s가 k에 지고, k가 t에 진다면 s는 t에 지는 것이다.
    for (int k=1; k<=n; k++) {
        for (int s=1; s<=n; s++) {
            for (int t=1; t<=n; t++) {
                if (matchResults[s][t]!=0) continue; //승패가 확정되어 있다면 갱신의 필요가 없다.
                if (matchResults[s][k]>0 && matchResults[k][t]>0) {
                    matchResults[s][t] = 1;
                    matchResults[t][s] = -1;
                }
                else if (matchResults[s][k]<0 && matchResults[k][t]<0) {
                    matchResults[s][t] = -1;
                    matchResults[t][s] = 1;
                }
            } //t loop
        } //s loop
    } //k loop

    for (int i=1; i<=n; i++) {
        matchResults[i][i] = 1;
    }

    int result=0; //순위가 확정된 선수 명수
    for (int i=1; i<=n; i++) {
        int j=1;
        for (j=1; j<=n; j++) {
            if (matchResults[i][j]==0) break;
        }
        if (j>n) result++; //선수 i에 대해 승패 정보가 j개 밝혀졌으면 선수 i의 순위가 결정된 것이다.
    }

    return result;
}

vector<int> makeVector(int a, int b) {
    vector<int> result;
    result.push_back(a);
    result.push_back(b);
    return result;
}

int main() {
    int n = 5;
    vector<vector<int>> results;
    results.push_back(makeVector(4, 3));
    results.push_back(makeVector(4, 2));
    results.push_back(makeVector(3, 2));
    results.push_back(makeVector(1, 2));
    results.push_back(makeVector(2, 5));

    cout << solution(n, results) << '\n';

    return 0;
}
