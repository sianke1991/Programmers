#include <bits/stdc++.h>
#define dX first
#define dY second

using namespace std;

pair<int, int> directions[] = {
    {0, -1}, //UP
    {1, -1}, //UP_RIGHT
    {1, 0}, //RIGHT
    {1, 1}, //DOWN_RIGHT
    {0, 1}, //DOWN
    {-1, 1}, //DOWN_LEFT
    {-1, 0}, //LEFT
    {-1, -1} //UP_LEFT
};



class Point {
    public:
        int x;
        int y;

        Point() {

        }

        Point(int x, int y) {
            this->x = x;
            this->y = y;
        }

        vector<Point> moveTo(int idx) {
            vector<Point> result;
            pair<int, int> direction = directions[idx];
            result.push_back({this->x+direction.dX, this->y+direction.dY});
            result.push_back({this->x+2*direction.dX, this->y+2*direction.dY});
            return result;
        }

        struct HashFunction {
            size_t operator() (const Point& obj) const {
                return obj.x*31+obj.y;
            }
        };

        bool operator==(const Point& that) const {
            return this->x == that.x &&
                    this->y == that.y;
        }

        bool operator<(const Point& that) const {
            return this->x < that.x ||
                    (this->x == that.x && this->y < that.y);
        }

        bool operator>(const Point& that) const {
            return this->x > that.x ||
                    (this->x == that.x && this->y > that.y);
        }

        static Point max(const Point p0, const Point p1) {
            return p0>p1 ? p0 : p1;
        }

        static Point min(const Point p0, const Point p1) {
            return p0<p1 ? p0 : p1;
        }
};


class Line {
    public:
        Point v0;
        Point v1;

        Line(Point v0, Point v1) {
            this->v0 = v0;
            this->v1 = v1;
        }

        Point minPoint() const {
            return Point::min(this->v0, this->v1); //정적 메서드 호출은 :: 을 사용한다.
        }

        Point maxPoint() const {
            return Point::max(this->v0, this->v1); //정적 메서드 호출은 :: 을 사용한다.
        }

        bool operator==(const Line& that) const {
            return this->minPoint() == that.minPoint() &&
                    this->maxPoint() == that.maxPoint();
        }

        struct HashFunction {
            size_t operator() (const Line obj) const {
                Point::HashFunction hf = Point::HashFunction{};
                return hf(obj.minPoint())*31 + hf(obj.maxPoint()); //Point의 인스턴스의 해시 코드를 Line 클래스 내부에서 사용할 수 있다.
            }
        };
};



int solution(vector<int> arrows) {
    unordered_set<Point, Point::HashFunction> points;
    unordered_set<Line, Line::HashFunction> lines;
    Point currentPoint{0, 0};
    Point passingPoint;
    Point nextPoint;
    vector<Point> tPoints;
    points.insert(currentPoint);

    for (int arrow:arrows) {
        tPoints = currentPoint.moveTo(arrow);
        passingPoint = tPoints[0];
        nextPoint = tPoints[1];
        points.insert(passingPoint);
        points.insert(nextPoint);
        Line lineAdded0{currentPoint, passingPoint};
        lines.insert(lineAdded0);
        Line lineAdded1{passingPoint, nextPoint};
        lines.insert(lineAdded1);
        currentPoint = nextPoint;
    }
    //오일러 정리  F = 1 - V + E
    return 1-points.size()+lines.size();
}


int main() {
    vector<int> arrows{0, 6, 4, 2, 7, 2, 5};
    cout << solution(arrows) << '\n';

    Line::HashFunction hf{};
    Point v0{0, 6};
    Point v1{1, -0};
    Line l0{v0, v1};
    Line l1{v1, v0};
    cout << (l0==l1) << '\n';
    return 0;
}
