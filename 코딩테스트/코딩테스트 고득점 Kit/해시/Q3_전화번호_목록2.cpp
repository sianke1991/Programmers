#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

/**
 * 전화번호 set를 만들고,
 * 전화번호 list에서 가능한 모든 substring을 만들어 해당 substring이 전화번호 set에 들어있는지 여부를 체크한다.
 */
bool solution(vector<string> phone_book) {
    unordered_set<string> s;
    for (string elem:phone_book)
        s.insert(elem);

    for (string elem:phone_book) {
        for (int len=0; len<elem.length(); len++) {
            if (s.find(elem.substr(0, len)) != s.end())
                return false;
        }
    }
    return true;
}
