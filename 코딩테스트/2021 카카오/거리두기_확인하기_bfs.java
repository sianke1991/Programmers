import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashSet;

class Solution {
	
	static class Point {
		public final int row;
        public final int col;
        
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public char placeFrom(String[] place) {
            return place[this.row].charAt(this.col);
        }
        
        public int distanceTo(Point that) {
            return Math.abs(this.row-that.row) + Math.abs(this.col-that.col);
        }
        
        @Override
        public String toString() {
        	return new StringBuffer().append('(').append(this.row).append(", ").append(this.col).append(')').toString();
        }
        
        @Override
        public int hashCode() {
        	return this.row*100+this.col;
        }
        
        @Override
        public boolean equals(Object o) {
        	if (!(o instanceof Point))
        		return false;
        	Point that = (Point) o;
        	return this.row == that.row &&
        			this.col == that.col;
        }
        
        public List<Point> neighboringPoints() {
        	List<Point> result = new ArrayList<>();
        	if (this.row>0)
        		result.add(new Point(this.row-1, this.col));
        	if (this.col>0)
        		result.add(new Point(this.row, this.col-1));
        	if (this.row<4) //전체 행의 개수 5에 대해 다음 행이 존재하는 경우
        		result.add(new Point(this.row+1, this.col));
        	if (this.col<4) //전체 열의 개수 5에 대해 다음 열이 존재하는 경우
        		result.add(new Point(this.row, this.col+1));
        	return result;
        }
	}
	
	/**
     * 하나의 테스트 케이스에 대해 참가자의 위치 목록을 반환한다.
     */
    List<Point> participantPositions(String[] place) {
        List<Point> result = new ArrayList<>();
        for (int row=0; row<place.length; row++) {
            for (int col=0; col<place[row].length(); col++) {
                if (place[row].charAt(col)=='P')
                    result.add(new Point(row, col));
            } //col loop
        } //row loop
        return result;
    }
    
    /**
     * 너비우선탐색을 적용하여 해당 테스트 케이스가 거리두기를 잘 지키고 있는지 여부를 반환한다.
     * @param places
     * @return
     */
    boolean bfs(String[] place) {
    	List<Point> participantPositions = participantPositions(place);
    	for (Point startingPoint:participantPositions) {
    		Queue<Point> q = new ArrayDeque<>();
    		Set<Point> visitedPoints = new HashSet<>();
    		q.add(startingPoint);
    		visitedPoints.add(startingPoint);
    		while (!q.isEmpty()) {
    			Point currentPoint = q.poll();
    			if (currentPoint.distanceTo(startingPoint)>=2) //거리가 2를 초과한 경우 bfs를 중단한다.
    				break;
    			List<Point> neighboringPoints = currentPoint.neighboringPoints();
    			for (Point neighboringPoint:neighboringPoints) {
    				if (!visitedPoints.add(neighboringPoint)) //이웃한 칸을 체크할 때 이미 방문한 칸은 다시 체크할 필요가 없다.
    					continue;
    				switch (neighboringPoint.placeFrom(place)) {
	    				case 'P': //거리 2 이내에 사람이 있는 경우 거리두기를 지키지 않은 것이다.
	    					return false;
	    				case 'X': //파티션이 있는 경우 그 방향으로 탐색을 하지 않는다.
	    					break;
	    				default:
	    					q.add(neighboringPoint);
    				} //switch
    			} //neighboringPoint loop
    		} //until q becomes empty
    	} //startingPoint loop
    	return true;
    }

	public int[] solution(String[][] places) {
		int[] answer = new int[places.length];
		for (int testCase=0; testCase<places.length; testCase++) {
			answer[testCase] = bfs(places[testCase]) ? 1 : 0;
		}	
		return answer;
	}
	
	public static void main(String[] args) {
    	try {
	    	String[][] places = 
	    		{
	    			{"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"}, 
	    			{"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"}, 
	    			{"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"}, 
	    			{"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"}, 
	    			{"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}
	    		};
	    	
	    	int[] solution = new Solution().solution(places);
	    	System.out.println(java.util.Arrays.toString(solution));
	    	
	    	
	    	for (int i=0; i<places.length; i++) {
	    		System.out.println("testCase: " + i);
	    		System.out.println(new Solution().bfs(places[i]));
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
