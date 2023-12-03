#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

/**
 * ��ȭ��ȣ set�� �����,
 * ��ȭ��ȣ list���� ������ ��� substring�� ����� �ش� substring�� ��ȭ��ȣ set�� ����ִ��� ���θ� üũ�Ѵ�.
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
