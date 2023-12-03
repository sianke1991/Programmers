#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

int solution(vector<vector<string>> clothes) {
    /*�� �ǻ� ������ ���� ������ �����ϴ� map*/
    unordered_map<string, int> m;
    for (vector<string> cloth:clothes) {
        m[cloth[1]]++;
    }

    int product = 1;
    // (�� �ǻ��� ����+1)�� ���� ���Ѵ�.
    //�� �ǻ��� ���� �� �ϳ��� ���� �� �ְ�, �ش� �ǻ��� �������� �ʴ� �������� �ִ�.
    //�� ���� ����� 1�� �� �ش�.
    //(�ּ� �ϳ��� �ǻ��� �����ؾ� �ϹǷ� ��� �ǻ��� �������� �ʴ´ٴ� ���� �� ������ ���ܵǾ�� �Ѵ�.)
    for (pair<string, int> entry:m) {
        product *= (entry.second+1);
    }
    return product-1;

}
