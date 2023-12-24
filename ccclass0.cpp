/**
Ŭ����
**/

#include <bits/stdc++.h>

using namespace std;


/**
 * 2���� ����
 */
class Vector {
    public: //���� ������
        int x, y;

        //������ constructor  (�����ڸ� ������ �������� ������ ����Ʈ �����ڰ� �����ȴ�.)
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
            Vector result = {this->x+that.x, this->y+that.y}; //Vector(int x, int y) �����ڸ� ȣ���Ѵ�.
            return result;
        }

        string toString() {
            return "[" + to_string(this->x)+", "+to_string(this->y)+"]";

        }

        //�Ҹ��� destructor (�������� ����ų� delete ������ �� �� ȣ��Ǵ� �޼���)
        ~Vector() {
            //cout << "deleted\n";
            //delete this->x; //�Ҵ�� �޸� ���� (������ ������ ����)
            //delete this->y; //�Ҵ�� �޸� ���� (������ ������ ����)
        }

};

using VectorHashSet = std::unordered_set<Vector, Vector::HashFunction>;
using VectorTreeSet = std::set<Vector>;

int main() {
    Vector v0; //������ ȣ��
    Vector* v1 = new Vector(); //������ ȣ�� (���� �Ҵ�)
    v0.x = 5;
    v0.y = 4;
    v1->x = 5;
    v1->y = 4;


    cout << "v0==v1: " << (v0 == (*v1)) << '\n';
    cout << "v0+v1: " << (v0+(*v1)).toString() << '\n';

    delete v1; //�Ҹ��� ȣ�� (���� �Ҵ� ����)



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
    return 0; //�� �������� v0�� ���� �Ҹ��ڰ� ȣ��ȴ�.
}
