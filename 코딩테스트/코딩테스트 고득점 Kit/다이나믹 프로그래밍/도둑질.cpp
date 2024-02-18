#include <string>
#include <vector>
#include <stdio.h>

using namespace std;

/**
 *  dpArray[n][0]: 인덱스 n번 집까지 고려했을 때 인덱스 0번 집과 인덱스 n번 집을 털지 않을 때 털 수 있는 금액의 최대치
 *  dpArray[n][1]: 인덱스 n번 집까지 고려했을 때 인덱스 0번 집은 털지 않고, 인덱스 n번 집을 털었을 때 털 수 있는 금액의 최대치
 *  dpArray[n][2]: 인덱스 n번 집까지 고려했을 때 인덱스 0번 집은 털고, 인덱스 n번 집은 털지 않았을 때 털 수 있는 금액의 최대치
 *  dpArray[n][3]: 인덱스 n번 집까지 고려했을 때 인덱스 0번 집과 인덱스 n번 집을 털었을 때 털 수 있는 금액의 최대치
 */
int dpArray[1000001][4];

int max(int num0, int num1) {
    return num0>num1 ? num0:num1;
}

int solution(vector<int> money) {
    //초기치: 인덱스 0번, 1번 집의 경우-
    dpArray[0][3] = money[0];
    dpArray[1][1] = money[1];
    dpArray[1][2] = money[0];
    dpArray[1][3] = money[0];

    //점화식 (마지막 집을 제외하고 적용되는 점화식)
    for (int i=2; i<money.size()-1; i++) {
        //인덱스 i번 집을 털지 않은 경우 i-1번 집은 털어도, 털지 않아도 괜찮다.
        dpArray[i][0] = max(dpArray[i-1][0], dpArray[i-1][1]);
        dpArray[i][2] = max(dpArray[i-1][2], dpArray[i-1][3]);
        //인덱스 i번 집을 털 경우 i-1번 집은 털지 않아야 한다.
        dpArray[i][1] = dpArray[i-1][0] + money[i];
        dpArray[i][3] = dpArray[i-1][2] + money[i];
    }

    //점화식(마지막 집에 적용되는 점화식)
    {
        int i = money.size()-1;
        //인덱스 i번 집을 털지 않은 경우 i-1번 집은 털어도, 털지 않아도 괜찮다.
        dpArray[i][0] = max(dpArray[i-1][0], dpArray[i-1][1]);
        dpArray[i][2] = max(dpArray[i-1][2], dpArray[i-1][3]);
        //인덱스 i번 집을 털 경우 0번 집과 i-1번 집은 털지 않아야 한다.
        dpArray[i][1] = dpArray[i-1][0] + money[i];


        return max(max(dpArray[i][0], dpArray[i][1]), dpArray[i][2]);
    }
}

int main() {
    vector<int> money = {100, 1, 1, 100, 1, 1};
    printf("%d\n", solution(money));

    for (int row=0; row<4; row++) {
        for (int i=0; i<money.size(); i++) {
            printf("%5d", dpArray[i][row]);
        }
        printf("\n");
    }
    return 0;
}
