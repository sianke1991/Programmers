/**
클래스
**/

#include <bits/stdc++.h>

using namespace std;


/**
 * 2차원 벡터
 */
class Vector {
    public: //접근 제한자
        int x, y;

        //생성자 constructor  (생성자를 별도로 정의하지 않으면 디폴트 생성자가 생성된다.)
        Vector() {
            this->x = 0;
            this->y = 0;
        }

        Vector(int x, int y) {
            this->x = x;
            this->y = y;
        }

        struct HashFunction {
            size_t operator() (const Vector& obj) const {
                return std::hash<int>()(obj.x);
            }
        };
        bool operator==(const Vector& that) const {
            return this->x == that.x &&
                    this->y == that.y;
        }

        bool operator<(const Vector& that) const {
            return this->x < that.x ||
                    (this->x == that.x && this->y < that.y);
        }

        bool operator<=(const Vector& that) const {
            return (*this)==that || (*this)<that;
        }

        bool operator>(const Vector& that) const {
            return this->x > that.x ||
                    (this->x == that.x && this->y > that.y);
        }


        Vector operator+(const Vector& that) const {
            Vector result = {this->x+that.x, this->y+that.y}; //Vector(int x, int y) 생성자를 호출한다.
            return result;
        }

        string toString() {
            return "[" + to_string(this->x)+", "+to_string(this->y)+"]";

        }

        //소멸자 destructor (스코프를 벗어나거나 delete 연산을 할 때 호출되는 메서드)
        ~Vector() {
            //cout << "deleted\n";
            //delete this->x; //할당된 메모리 해제 (포인터 변수만 가능)
            //delete this->y; //할당된 메모리 해제 (포인터 변수만 가능)
        }

};

using VectorHashSet = std::unordered_set<Vector, Vector::HashFunction>;
using VectorTreeSet = std::set<Vector>;

int main() {
    Vector v0; //생성자 호출
    Vector* v1 = new Vector(); //생성자 호출 (동적 할당)
    v0.x = 5;
    v0.y = 4;
    v1->x = 5;
    v1->y = 4;


    cout << "v0==v1: " << (v0 == (*v1)) << '\n';
    cout << "v0+v1: " << (v0+(*v1)).toString() << '\n';

    delete v1; //소멸자 호출 (동적 할당 해제)



    VectorHashSet s;
    s.insert({5, 4});
    s.insert({1, 2});
    s.insert({5, 4});
    s.insert({2, 1});
    s.insert({3, 6});
    cout << s.size() << '\n';
    for (Vector v:s) {
        cout << v.toString() << ' ';
    }
    cout << '\n';

    VectorTreeSet ts;
    ts.insert({5, 4});
    ts.insert({1, 2});
    ts.insert({5, 4});
    ts.insert({2, 1});
    ts.insert({3, 6});
    cout << ts.size() << '\n';
    for (Vector v:ts) {
        cout << v.toString() << ' ';
    }
    cout << '\n';


    Vector v2{3, 4};
    Vector v3{3, 5};
    cout << (v2 <= v3) << '\n';




    Vector vs[] = {{1, 2}, {-1, 3}, {5, 6}, {3, 4}, {-2, 3}};
    for (Vector v:vs) {
        cout << v.toString() << ' ';
    }
    cout << '\n';
    sort(vs, vs+5);
    for (Vector v:vs) {
        cout << v.toString() << ' ';
    }
    cout << '\n';
    return 0; //이 시점에서 v0에 대한 소멸자가 호출된다.
}
