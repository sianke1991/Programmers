package problem방의_개수;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Q3_방의_개수 {
	
	/**
	 * 방향 열거체<br/>
	 * 정수를 방향으로 변환하려면 Direction.values()[index]를 사용한다.
	 * @author sianke1991
	 *
	 */
	static enum Direction {
		UP(0, -1),
		UP_RIGHT(1, -1),
		RIGHT(1, 0),
		DOWN_RIGHT(1, 1),
		DOWN(0, 1),
		DOWN_LEFT(-1, 1),
		LEFT(-1, 0),
		UP_LEFT(-1, -1);
		
		public final int dX;
		public final int dY;
		
		private Direction(int dX, int dY) {
			this.dX = dX;
			this.dY = dY;
		}
	}
	
	/**
	 * 점을 나타내는 객체<br/>
	 * 한 번 객체를 생성하면 내부의 값을 변경할 수 없다.
	 * @author sianke1991
	 */
	static class Point implements Comparable<Point> {
		public final int x;
		public final int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * 해당 점에서 입력받은 방향으로 한 번 이동했을 때 지나가는 점 및 도착하는 점 목록을 반환한다.
		 * @param direction
		 * @return
		 * @implNote 0642725와 같이 대각선이 서로 교차하는 문제를 해결하기 위해 한 번 이동할 때 [두 단위]를 이동하는 것으로 간주한다.
		 */
		public List<Point> moveTo(Direction direction) {
			List<Point> result = new ArrayList<>();
			result.add(new Point(this.x+direction.dX, this.y+direction.dY));
			result.add(new Point(this.x+2*direction.dX, this.y+2*direction.dY));
			return result;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}
		
		@Override
		public int compareTo(Point that) {
			if (this.x==that.x)
				return this.y-that.y;
			else
				return this.x-that.x;
		}
	}
	
	/**
	 * 선을 나타내는 객체<br/>
	 * 한 번 객체를 생성하면 내부의 값을 변경할 수 없다.<br/>
	 * 출발점과 종료점이 서로 반대인 두 선은 서로 동일한 것으로 간주한다.
	 * @author sianke1991
	 *
	 */
	static class Line {
		public final Point v0;
		public final Point v1;
		
		public Line(Point v0, Point v1) {
			this.v0 = v0;
			this.v1 = v1;
		}
		
		public Point minPoint() {
			if (this.v0.compareTo(this.v1)<0)
				return this.v0;
			else
				return this.v1;
		}
		
		public Point maxPoint() {
			if (this.v0.compareTo(this.v1)<0)
				return this.v1;
			else
				return this.v0;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o==null || !(o instanceof Line))
				return false;
			Line that = (Line) o;
			return (this.maxPoint().equals(that.maxPoint())) 
				&& (this.minPoint().equals(that.minPoint()));
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.minPoint(), this.maxPoint());
		}
	}
	
	public int solution(int[] arrows) {
        Set<Point> points = new HashSet<>();
        Set<Line> lines = new HashSet<>();
        Point currentPoint = new Point(0, 0);
        Point passingPoint = null; //한 번 움직였을 때 지나가는 점
        Point nextPoint = null; //한 번 움직였을 때 도착하는 점
        List<Point> tPoints = null;
        points.add(currentPoint);
        //currentPoint를 지시한 방향에 따라 움직이면서 점의 개수 및 선의 개수를 헤아린다.
        for (int arrow:arrows) {
        	tPoints = currentPoint.moveTo(Direction.values()[arrow]);
        	passingPoint = tPoints.get(0);
        	nextPoint = tPoints.get(1);
        	points.add(passingPoint);
        	points.add(nextPoint);
        	lines.add(new Line(currentPoint, passingPoint));
        	lines.add(new Line(passingPoint, nextPoint));
        	currentPoint = nextPoint;
        }
        //오일러 정리: F = 1 - V + E
        return 1-points.size()+lines.size();
    }
	
	public static void main(String[] args) {
		int[] arrows = {0, 6, 4, 2, 7, 2, 5};
		Q3_방의_개수 solution = new Q3_방의_개수();
		System.out.println(solution.solution(arrows));
	}
}
