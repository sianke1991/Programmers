#include <string>
#include <vector>
#include <algorithm>

using namespace std;

/*
 * 코딩테스트 > 해시의 문제이지만 정렬로 푼 경우
 */
bool solution(vector<string> phone_book) {
    sort(phone_book.begin(), phone_book.end()); //전화번호를 사전식으로 정렬하면, 바로 직전의 전화번호가 바로 직후의 전화번호의 접두사가 되는지 여부를 체크하면 된다.
    for (int i=1; i<phone_book.size(); i++) {
        if (phone_book[i].rfind(phone_book[i-1], 0) == 0) //phone_book[i]가 phone_book[i-1]로 시작한다면-
            return false;
    }
    return true;
}
