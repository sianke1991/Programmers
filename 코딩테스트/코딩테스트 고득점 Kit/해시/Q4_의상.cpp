#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

int solution(vector<vector<string>> clothes) {
    /*각 의상 종류에 따른 개수를 보관하는 map*/
    unordered_map<string, int> m;
    for (vector<string> cloth:clothes) {
        m[cloth[1]]++;
    }

    int product = 1;
    // (각 의상의 개수+1)의 곱을 구한다.
    //각 의상의 개수 중 하나를 택할 수 있고, 해당 의상을 선택하지 않는 선택지도 있다.
    //그 곱의 결과에 1을 빼 준다.
    //(최소 하나의 의상은 선택해야 하므로 모든 의상을 선택하지 않는다는 선택 한 가지는 제외되어야 한다.)
    for (pair<string, int> entry:m) {
        product *= (entry.second+1);
    }
    return product-1;

}
