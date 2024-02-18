#include <string>
#include <vector>
#include <stdio.h>

using namespace std;

/**
 *  dpArray[n][0]: �ε��� n�� ������ ������� �� �ε��� 0�� ���� �ε��� n�� ���� ���� ���� �� �� �� �ִ� �ݾ��� �ִ�ġ
 *  dpArray[n][1]: �ε��� n�� ������ ������� �� �ε��� 0�� ���� ���� �ʰ�, �ε��� n�� ���� �о��� �� �� �� �ִ� �ݾ��� �ִ�ġ
 *  dpArray[n][2]: �ε��� n�� ������ ������� �� �ε��� 0�� ���� �а�, �ε��� n�� ���� ���� �ʾ��� �� �� �� �ִ� �ݾ��� �ִ�ġ
 *  dpArray[n][3]: �ε��� n�� ������ ������� �� �ε��� 0�� ���� �ε��� n�� ���� �о��� �� �� �� �ִ� �ݾ��� �ִ�ġ
 */
int dpArray[1000001][4];

int max(int num0, int num1) {
    return num0>num1 ? num0:num1;
}

int solution(vector<int> money) {
    //�ʱ�ġ: �ε��� 0��, 1�� ���� ���-
    dpArray[0][3] = money[0];
    dpArray[1][1] = money[1];
    dpArray[1][2] = money[0];
    dpArray[1][3] = money[0];

    //��ȭ�� (������ ���� �����ϰ� ����Ǵ� ��ȭ��)
    for (int i=2; i<money.size()-1; i++) {
        //�ε��� i�� ���� ���� ���� ��� i-1�� ���� �о, ���� �ʾƵ� ������.
        dpArray[i][0] = max(dpArray[i-1][0], dpArray[i-1][1]);
        dpArray[i][2] = max(dpArray[i-1][2], dpArray[i-1][3]);
        //�ε��� i�� ���� �� ��� i-1�� ���� ���� �ʾƾ� �Ѵ�.
        dpArray[i][1] = dpArray[i-1][0] + money[i];
        dpArray[i][3] = dpArray[i-1][2] + money[i];
    }

    //��ȭ��(������ ���� ����Ǵ� ��ȭ��)
    {
        int i = money.size()-1;
        //�ε��� i�� ���� ���� ���� ��� i-1�� ���� �о, ���� �ʾƵ� ������.
        dpArray[i][0] = max(dpArray[i-1][0], dpArray[i-1][1]);
        dpArray[i][2] = max(dpArray[i-1][2], dpArray[i-1][3]);
        //�ε��� i�� ���� �� ��� 0�� ���� i-1�� ���� ���� �ʾƾ� �Ѵ�.
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
