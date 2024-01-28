#include <bits/stdc++.h>

using namespace std;

map<string, int> selectedGems;

void add(string gem) {
    selectedGems[gem]++;
}

/**
 * 1개 선택한 보석을 제거할 때는 selectedGems에서 erase 해 주어야 [선택한 보석의 종류 개수]를 올바르게 파악할 수 있다.
 */
void remove(string gem) {
    if (selectedGems[gem]<=1)
        selectedGems.erase(gem);
    else
        selectedGems[gem]--;
}

int size() {
    return selectedGems.size();
}

int max(int num0, int num1) {
    return num0>num1 ? num0 : num1;
}

vector<int> solution(vector<string> gems) {
    //part a: 입력받은 보석의 종류 개수를 구한다.
    set<string> gemNames;
    for (string gem:gems)
        gemNames.insert(gem);
    int numGemTypes = gemNames.size();

    //part b: 투 포인터를 적용하여 모든 보석을 포함하는 구간을 탐색한다.
    int optLeftIndex = 0;
    int optRightIndex = gems.size()-1;
    int rightIndex = 0;
    for (int leftIndex=0; leftIndex<gems.size(); leftIndex++) {
        for (rightIndex = max(rightIndex, leftIndex);rightIndex<gems.size(); rightIndex++) {
            add(gems[rightIndex]);
            if (size()==numGemTypes) {
                if (rightIndex-leftIndex<optRightIndex-optLeftIndex) {
                    optLeftIndex = leftIndex;
                    optRightIndex = rightIndex;
                }
                remove(gems[rightIndex]);
                break;
            }
        } //rightIndex loop
        remove(gems[leftIndex]);
    } //leftIndex loop
    return {optLeftIndex+1, optRightIndex+1};
}
