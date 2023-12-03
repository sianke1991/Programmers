#include <string>
#include <vector>
#include <algorithm>

using namespace std;

/*
 * �ڵ��׽�Ʈ > �ؽ��� ���������� ���ķ� Ǭ ���
 */
bool solution(vector<string> phone_book) {
    sort(phone_book.begin(), phone_book.end()); //��ȭ��ȣ�� ���������� �����ϸ�, �ٷ� ������ ��ȭ��ȣ�� �ٷ� ������ ��ȭ��ȣ�� ���λ簡 �Ǵ��� ���θ� üũ�ϸ� �ȴ�.
    for (int i=1; i<phone_book.size(); i++) {
        if (phone_book[i].rfind(phone_book[i-1], 0) == 0) //phone_book[i]�� phone_book[i-1]�� �����Ѵٸ�-
            return false;
    }
    return true;
}
