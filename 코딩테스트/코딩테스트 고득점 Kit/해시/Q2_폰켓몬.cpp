#include <bits/stdc++.h>
using namespace std;

int min(int num0, int num1) {
    return num0<num1 ? num0 : num1;
}

int solution(vector<int> nums)
{
    int num���ϸ� = nums.size();
    unordered_set<int> s;
    for (int elem:nums) {
        s.insert(elem);
    }
    int numDistinct���ϸ� = s.size();
    return min(num���ϸ�/2, numDistinct���ϸ�); // (num���ϸ�/2)�� ũ���� �� ������ �پ����� ���ϴٸ�
                                            // ������ �� �ִ� ���ϸ��� ���� ������ �۰� �ȴ�.
}
