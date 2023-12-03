#include <bits/stdc++.h>
using namespace std;

int min(int num0, int num1) {
    return num0<num1 ? num0 : num1;
}

int solution(vector<int> nums)
{
    int num폰켓몬 = nums.size();
    unordered_set<int> s;
    for (int elem:nums) {
        s.insert(elem);
    }
    int numDistinct폰켓몬 = s.size();
    return min(num폰켓몬/2, numDistinct폰켓몬); // (num폰켓몬/2)이 크더라도 그 종류가 다양하지 못하다면
                                            // 가져올 수 있는 폰켓몬의 종류 개수는 작게 된다.
}
