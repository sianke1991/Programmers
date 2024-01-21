#include <string>
#include <vector>
#include <iostream>

using namespace std;

/**
 * 두 벡터에서 하나씩 꺼내어 비교했을 때
 * 첫번째 벡터에서 꺼낸 값이 더 클 경우의 수를 반환한다.
 */
long long winningProbability(vector<int> currentASums, vector<int> currentBSums) {
    long long result = 0LL;
    for (int currentASum:currentASums) {
        for (int currentBSum:currentBSums) {
            if (currentASum>currentBSum)
                result++;
        } //currentBSum
    } //currentASum
    return result;
}

string to_string(vector<int> v) {
    string result = "[";
    for (int i=0; i<v.size()-1; i++) {
        result += to_string(v[i]);
        result += ", ";
    }
    result += to_string(v[v.size()-1]);
    result += "]";
    return result;
}

vector<int> solution2(vector<vector<int>> dice) {
    long long optWinningProbability = 0LL;
    vector<int> optCase = {0};
    const int numDice = 2;
    for (int a0=0; a0<numDice; a0++) {
        for (int b0=0; b0<numDice; b0++) {
            if (b0==a0) continue;
            vector<int> currentASums;
            vector<int> currentBSums;
            for (int a0Face:dice[a0]) {
                currentASums.push_back(a0Face);
            } //a0Face loop
            for (int b0Face:dice[b0]) {
                currentBSums.push_back(b0Face);
            } //b0Face loop
            long long currentWinningProbability = winningProbability(currentASums, currentBSums);
            if (currentWinningProbability>optWinningProbability) {
                optWinningProbability = currentWinningProbability;
                optCase = {a0+1};
            }
        } //b0 loop (플레이어 b가 선택한 주사위 인덱스)
    } //a0 loop (플레이어 a가 선택한 주사위 인덱스)
    return optCase;
}


vector<int> solution4(vector<vector<int>> dice) {
    long long optWinningProbability = 0LL;
    vector<int> optCase = {0, 0};
    const int numDice = 4;
    for (int a0=0; a0<numDice; a0++) {
        for (int a1=a0+1; a1<numDice; a1++) {
            for (int b0=0; b0<numDice; b0++) {
                if (b0==a0 || b0==a1) continue;
                for (int b1=b0+1; b1<numDice; b1++) {
                    if (b1==a0 || b1==a1) continue;
                    vector<int> currentASums;
                    vector<int> currentBSums;
                    for (int a0Face:dice[a0]) {
                        for (int a1Face:dice[a1]) {
                            currentASums.push_back(a0Face+a1Face);
                        } //a1Face loop
                    } //a0Face loop
                    for (int b0Face:dice[b0]) {
                        for (int b1Face:dice[b1]) {
                            currentBSums.push_back(b0Face+b1Face);
                        } //b1Face loop
                    } //b0Face loop
                    long long currentWinningProbability = winningProbability(currentASums, currentBSums);
                    if (currentWinningProbability>optWinningProbability) {
                        optWinningProbability = currentWinningProbability;
                        optCase = {a0+1, a1+1};
                    }
                } //b1 loop (플레이어 b가 선택한 주사위 1 인덱스)
            } //b0 loop (플레이어 b가 선택한 주사위0 인덱스)
        } //a1 loop (플레이어 a가 선택한 주사위1 인덱스)
    } //a0 loop (플레이어 a가 선택한 주사위0 인덱스)
    return optCase;
}


vector<int> solution6(vector<vector<int>> dice) {
    long long optWinningProbability = 0LL;
    vector<int> optCase = {0, 0, 0};
    const int numDice = 6;
    for (int a0=0; a0<numDice; a0++) {
        for (int a1=a0+1; a1<numDice; a1++) {
            for (int a2=a1+1; a2<numDice; a2++) {
                for (int b0=0; b0<numDice; b0++) {
                    if (b0==a0 || b0==a1 || b0==a2) continue;
                    for (int b1=b0+1; b1<numDice; b1++) {
                        if (b1==a0 || b1==a1 || b1==a2) continue;
                        for (int b2=b1+1; b2<numDice; b2++) {
                            if (b2==a0 || b2==a1 || b2==a2) continue;
                            vector<int> currentASums;
                            vector<int> currentBSums;
                            for (int a0Face:dice[a0]) {
                                for (int a1Face:dice[a1]) {
                                    for (int a2Face:dice[a2]) {
                                        currentASums.push_back(a0Face+a1Face+a2Face);
                                    } //a2Face loop
                                } //a1Face loop
                            } //a0Face loop
                            for (int b0Face:dice[b0]) {
                                for (int b1Face:dice[b1]) {
                                    for (int b2Face:dice[b2]) {
                                        currentBSums.push_back(b0Face+b1Face+b2Face);
                                    } //b2Face loop
                                } //b1Face loop
                            } //b0Face loop
                            long long currentWinningProbability = winningProbability(currentASums, currentBSums);
                            if (currentWinningProbability>optWinningProbability) {
                                optWinningProbability = currentWinningProbability;
                                optCase = {a0+1, a1+1, a2+1};
                            }
                        } //b2 loop (플레이어 b가 선택한 주사위2 인엑스)
                    } //b1 loop (플레이어 b가 선택한 주사위1 인덱스)
                } //b0 loop (플레이어 b가 선택한 주사위0 인덱스)
            } //a2 loop (플레이어 a가 선택한 주사위 2 인덱스)
        } //a1 loop (플레이어 a가 선택한 주사위1 인덱스)
    } //a0 loop (플레이어 a가 선택한 주사위0 인덱스)
    return optCase;
}

vector<int> solution8(vector<vector<int>> dice) {
    long long optWinningProbability = 0LL;
    vector<int> optCase = {0, 0, 0, 0};
    const int numDice = 8;
    for (int a0=0; a0<numDice; a0++) {
        for (int a1=a0+1; a1<numDice; a1++) {
            for (int a2=a1+1; a2<numDice; a2++) {
                for (int a3=a2+1; a3<numDice; a3++) {
                    for (int b0=0; b0<numDice; b0++) {
                        if (b0==a0 || b0==a1 || b0==a2 || b0==a3) continue;
                        for (int b1=b0+1; b1<numDice; b1++) {
                            if (b1==a0 || b1==a1 || b1==a2 || b1==a3) continue;
                            for (int b2=b1+1; b2<numDice; b2++) {
                                if (b2==a0 || b2==a1 || b2==a2 || b2==a3) continue;
                                for (int b3=b2+1; b3<numDice; b3++) {
                                    if (b3==a0 || b3==a1 || b3==a2 || b3==a3) continue;
                                    vector<int> currentASums;
                                    vector<int> currentBSums;
                                    for (int a0Face:dice[a0]) {
                                        for (int a1Face:dice[a1]) {
                                            for (int a2Face:dice[a2]) {
                                                for (int a3Face:dice[a3]) {
                                                    currentASums.push_back(a0Face+a1Face+a2Face+a3Face);
                                                } //a3Face loop
                                            } //a2Face loop
                                        } //a1Face loop
                                    } //a0Face loop
                                    for (int b0Face:dice[b0]) {
                                        for (int b1Face:dice[b1]) {
                                            for (int b2Face:dice[b2]) {
                                                for (int b3Face:dice[b3]) {
                                                    currentBSums.push_back(b0Face+b1Face+b2Face+b3Face);
                                                } //b3Face loop
                                            } //b2Face loop
                                        } //b1Face loop
                                    } //b0Face loop
                                    long long currentWinningProbability = winningProbability(currentASums, currentBSums);
                                    if (currentWinningProbability>optWinningProbability) {
                                        optWinningProbability = currentWinningProbability;
                                        optCase = {a0+1, a1+1, a2+1, a3+1};
                                    }
                                } //b3 loop
                            } //b2 loop
                        } //b1 loop
                    } //b0 loop
                } //a3 loop
            } //a2 loop
        } //a1 loop
    } //a0 loop
    return optCase;
}

vector<int> solution10(vector<vector<int>> dice) {
    long long optWinningProbability = 0LL;
    vector<int> optCase = {0, 0, 0, 0, 0};
    const int numDice = 10;
    for (int a0=0; a0<numDice; a0++) {
        for (int a1=a0+1; a1<numDice; a1++) {
            for (int a2=a1+1; a2<numDice; a2++) {
                for (int a3=a2+1; a3<numDice; a3++) {
                    for (int a4=a3+1; a4<numDice; a4++) {
                        for (int b0=0; b0<numDice; b0++) {
                            if (b0==a0 || b0==a1 || b0==a2 || b0==a3 || b0==a4) continue;
                            for (int b1=b0+1; b1<numDice; b1++) {
                                if (b1==a0 || b1==a1 || b1==a2 || b1==a3 || b1==a4) continue;
                                for (int b2=b1+1; b2<numDice; b2++) {
                                    if (b2==a0 || b2==a1 || b2==a2 || b2==a3 || b2==a4) continue;
                                    for (int b3=b2+1; b3<numDice; b3++) {
                                        if (b3==a0 || b3==a1 || b3==a2 || b3==a3 || b3==a4) continue;
                                        for (int b4=b3+1; b4<numDice; b4++) {
                                            if (b4==a0 || b4==a1 || b4==a2 || b4==a3 || b4==a4) continue;
                                            vector<int> currentASums;
                                            vector<int> currentBSums;
                                            for (int a0Face:dice[a0]) {
                                                for (int a1Face:dice[a1]) {
                                                    for (int a2Face:dice[a2]) {
                                                        for (int a3Face:dice[a3]) {
                                                            for (int a4Face:dice[a4]) {
                                                                currentASums.push_back(a0Face+a1Face+a2Face+a3Face+a4Face);
                                                            } //a4Face loop
                                                        } //a3Face loop
                                                    } //a2Face loop
                                                } //a1Face loop
                                            } //a0Face loop
                                            for (int b0Face:dice[b0]) {
                                                for (int b1Face:dice[b1]) {
                                                    for (int b2Face:dice[b2]) {
                                                        for (int b3Face:dice[b3]) {
                                                            for (int b4Face:dice[b4]) {
                                                                currentBSums.push_back(b0Face+b1Face+b2Face+b3Face+b4Face);
                                                            } //b4Face loop
                                                        } //b3Face loop
                                                    } //b2Face loop
                                                } //b1Face loop
                                            } //b0Face loop
                                            long long currentWinningProbability = winningProbability(currentASums, currentBSums);
                                            if (currentWinningProbability>optWinningProbability) {
                                                optWinningProbability = currentWinningProbability;
                                                optCase = {a0+1, a1+1, a2+1, a3+1, a4+1};
                                            }
                                        } //b4 loop
                                    } //b3 loop
                                } //b2 loop
                            } //b1 loop
                        } //b0 loop
                    } //a4 loop
                } //a3 loop
            } //a2 loop
        } //a1 loop
    } //a0 loop
    return optCase;
}


vector<int> solution(vector<vector<int>> dice) {
    int numDice = dice.size();
    switch (numDice) {
        case 2:
            return solution2(dice);
        case 4:
            return solution4(dice);
        case 6:
            return solution6(dice);
        case 8:
            return solution8(dice);
        case 10:
            return solution10(dice);
        default: {
            vector<int> answer;
            return answer;
        }
    } //switch
}

int main() {
    vector<vector<int>> dice = {{1, 2, 3, 4, 5, 6}, {1, 1, 4, 4, 5, 5}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}};

    cout << to_string(solution(dice)) << '\n';

    return 0;
}
