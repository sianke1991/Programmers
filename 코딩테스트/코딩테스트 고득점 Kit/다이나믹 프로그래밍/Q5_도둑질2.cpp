#include <string>
#include <vector>
#include <stdio.h>

using namespace std;

/**
 *  dpArray[n][0]: �ε��� n�� ������ ������� �� �ε��� 0�� ���� ���� �ʰ� �� �� �ִ� �ݾ��� �ִ�ġ
 *  dpArray[n][1]: �ε��� n�� ������ ������� �� �ε��� 0�� ���� �а� �� �� �ִ� �ݾ��� �ִ�ġ
 */
int dpArray[1000001][2];


int max(int num0, int num1) {
    return num0>num1 ? num0:num1;
}

int solution(vector<int> money) {
    //�ʱ�ġ: �ε��� 0��, 1�� ���� ���-
    dpArray[0][1] = money[0];
    dpArray[1][0] = money[1];
    dpArray[1][1] = money[0];

    /*
        ���� ������ �������� �̷���� �ִٸ�
        k �� �� ���� ������� ���� �ִ�ġ��
           a. [k-1 �� �� ���� ������� �� �ִ�ġ] �Ǵ�
           b. [k-2 �� �� ���� ������� �� �ִ�ġ] + [k �� ���� �о��� �� ��� ��]
        ���� ���� �� �ִ�.
        ������ �̹� ������ ������ �������� �̷���� �����Ƿ�
        a. 0�� ���� ���� ���� ���
        b. 0�� ���� �� ���
        �� ����� �����ؾ� �Ѵ�. (0�� ���� �� ��� ������ ���� �� �� ���� �̷� ���� ������ �ణ �޶����� �ȴ�.)
    */
    //��ȭ�� (������ ���� �����ϰ� ����Ǵ� ��ȭ��)
    for (int i=2; i<money.size()-1; i++) {
        dpArray[i][0] = max(dpArray[i-1][0], dpArray[i-2][0]+money[i]);
        dpArray[i][1] = max(dpArray[i-1][1], dpArray[i-2][1]+money[i]);
    } //i loop

    //��ȭ��(������ ���� ����Ǵ� ��ȭ��)
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
