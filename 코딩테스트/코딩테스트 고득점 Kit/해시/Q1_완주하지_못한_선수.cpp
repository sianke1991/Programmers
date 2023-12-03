#include <bits/stdc++.h>

using namespace std;

string solution(vector<string> participant, vector<string> completion) {
    unordered_multiset<string> s;
    for (string elem:participant) {
        s.insert(elem);
    }

    for (string elem:completion) {
        s.erase(s.find(elem));
    }

    return *(s.begin());
}
