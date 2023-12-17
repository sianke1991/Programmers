#include <bits/stdc++.h>

using namespace std;

//���� ����� �����ϴ� ���̺�
//matchResults[i][j]�� ���� ��� i�� j���� �¸��Ѵ�.
//matchResults[i][j]�� ���� ��� i�� j���� �й��Ѵ�.
//matchResults[i][j]�� i�� j ���� ���� ������ Ȯ���� �� ����.
//���Ǹ� ���� matchResult[i][i]�� 1�� �д�.
int matchResults[101][101];

int solution(int n, vector<vector<int>> results) {
    for (vector<int> result:results) {
        matchResults[result[0]][result[1]] = 1;
        matchResults[result[1]][result[0]] = -1;
    }

    //floyd algorithm
    //s�� t ���̿� ���� ������ Ȯ���Ǿ� ������ ������ �ʿ䰡 ����.
    //k��� �Ű� ��忡 ���� s�� k�� �̱��, k�� t�� �̱�ٸ� s�� t�� �̱�� ���̴�.
    //�ݸ鿡 s�� k�� ����, k�� t�� ���ٸ� s�� t�� ���� ���̴�.
    for (int k=1; k<=n; k++) {
        for (int s=1; s<=n; s++) {
            for (int t=1; t<=n; t++) {
                if (matchResults[s][t]!=0) continue; //���а� Ȯ���Ǿ� �ִٸ� ������ �ʿ䰡 ����.
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

    int result=0; //������ Ȯ���� ���� ���
    for (int i=1; i<=n; i++) {
        int j=1;
        for (j=1; j<=n; j++) {
            if (matchResults[i][j]==0) break;
        }
        if (j>n) result++; //���� i�� ���� ���� ������ j�� ���������� ���� i�� ������ ������ ���̴�.
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
