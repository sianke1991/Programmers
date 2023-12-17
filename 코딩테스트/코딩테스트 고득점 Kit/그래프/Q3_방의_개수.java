package problem����_����;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Q3_����_���� {
	
	/**
	 * ���� ����ü<br/>
	 * ������ �������� ��ȯ�Ϸ��� Direction.values()[index]�� ����Ѵ�.
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
	 * ���� ��Ÿ���� ��ü<br/>
	 * �� �� ��ü�� �����ϸ� ������ ���� ������ �� ����.
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
		 * �ش� ������ �Է¹��� �������� �� �� �̵����� �� �������� �� �� �����ϴ� �� ����� ��ȯ�Ѵ�.
		 * @param direction
		 * @return
		 * @implNote 0642725�� ���� �밢���� ���� �����ϴ� ������ �ذ��ϱ� ���� �� �� �̵��� �� [�� ����]�� �̵��ϴ� ������ �����Ѵ�.
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
	 * ���� ��Ÿ���� ��ü<br/>
	 * �� �� ��ü�� �����ϸ� ������ ���� ������ �� ����.<br/>
	 * ������� �������� ���� �ݴ��� �� ���� ���� ������ ������ �����Ѵ�.
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
        Point passingPoint = null; //�� �� �������� �� �������� ��
        Point nextPoint = null; //�� �� �������� �� �����ϴ� ��
        List<Point> tPoints = null;
        points.add(currentPoint);
        //currentPoint�� ������ ���⿡ ���� �����̸鼭 ���� ���� �� ���� ������ ��Ƹ���.
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
        //���Ϸ� ����: F = 1 - V + E
        return 1-points.size()+lines.size();
    }
	
	public static void main(String[] args) {
		int[] arrows = {0, 6, 4, 2, 7, 2, 5};
		Q3_����_���� solution = new Q3_����_����();
		System.out.println(solution.solution(arrows));
	}
}
