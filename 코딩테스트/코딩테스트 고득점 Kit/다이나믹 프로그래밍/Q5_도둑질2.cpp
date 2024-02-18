#include <string>
#include <vector>
#include <stdio.h>

using namespace std;

/**
 *  dpArray[n][0]: 인덱스 n번 집까지 고려했을 때 인덱스 0번 집을 털지 않고 털 수 있는 금액의 최대치
 *  dpArray[n][1]: 인덱스 n번 집까지 고려했을 때 인덱스 0번 집을 털고 털 수 있는 금액의 최대치
 */
int dpArray[1000001][2];


int max(int num0, int num1) {
    return num0>num1 ? num0:num1;
}

int solution(vector<int> money) {
    //초기치: 인덱스 0번, 1번 집의 경우-
    dpArray[0][1] = money[0];
    dpArray[1][0] = money[1];
    dpArray[1][1] = money[0];

    /*
        만약 마을이 선형으로 이루어져 있다면
        k 번 집 까지 고려했을 때의 최대치는
           a. [k-1 번 집 까지 고려했을 때 최대치] 또는
           b. [k-2 번 집 까지 고려했을 때 최대치] + [k 번 집을 털었을 때 얻는 값]
        으로 구할 수 있다.
        하지만 이번 문제는 마을이 원형으로 이루어져 있으므로
        a. 0번 집을 털지 않은 경우
        b. 0번 집을 턴 경우
        로 나누어서 생각해야 한다. (0번 집을 턴 경우 마지막 집은 털 수 없고 이로 인해 수식이 약간 달라지게 된다.)
    */
    //점화식 (마지막 집을 제외하고 적용되는 점화식)
    for (int i=2; i<money.size()-1; i++) {
        dpArray[i][0] = max(dpArray[i-1][0], dpArray[i-2][0]+money[i]);
        dpArray[i][1] = max(dpArray[i-1][1], dpArray[i-2][1]+money[i]);
    } //i loop

    //점화식(마지막 집에 적용되는 점화식)
    {
        int i= money.size()-1;
        dpArray[i][0] = max(dpArray[i-1][0], dpArray[i-2][0]+money[i]);
        dpArray[i][1] = max(dpArray[i-1][1], dpArray[i-2][1]);
        return max(dpArray[i][0], dpArray[i][1]);
    }
}

int main() {
    vector<int> money = {197, 153, 103, 238, 225};
    printf("%d\n", solution(money));

    return 0;
}
