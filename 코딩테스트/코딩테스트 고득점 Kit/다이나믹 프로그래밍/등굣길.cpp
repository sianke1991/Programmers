#include <string>
#include <vector>
#include <set>
#include <iostream>
#include <stdio.h>

using namespace std;
long long profile[102][102];

int solution(int m, int n, vector<vector<int>> puddles) {
    //물 웅덩이 정보를 set으로 관리한다.
    set<pair<int, int>> puddleSet;
    for (vector<int> puddle:puddles) {
        puddleSet.insert({puddle[1], puddle[0]});
    }
    //집에 갈 수 있는 경우의 수는 1로 설정한다.
    profile[0][1] = 1LL;
    //profile[1][0] = 1LL;
    for (int row=1; row<=n; row++) {
        for (int col=1; col<=m; col++) {
            if (puddleSet.find({row, col}) != puddleSet.end()) {
                //cout << "puddles at row: " << row << ", col: " << col << '\n';
                profile[row][col] = 0;
            } else {
                profile[row][col] = (profile[row-1][col]+profile[row][col-1])%1000000007;
                //cout << "profile at row: " << row << ", col: " << col << "is " << profile[row][col] << '\n';
            }
        } //col loop
    } //row loop

    /*
    for (int row=1; row<=n; row++) {
        for (int col=1; col<=m; col++) {
            printf("%3lld ", profile[row][col]);
        }
        printf("\n");
    }
    */
    return (int)(profile[n][m]);
}

int main() {
        int m = 4;
        int n = 3;
        vector<vector<int>> puddles;
        puddles.push_back({2, 2});
        cout << solution(m, n, puddles);
        return 0;
}
