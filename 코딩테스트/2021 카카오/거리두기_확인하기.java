import java.util.List;
import java.util.ArrayList;

class Solution {
    static class Point {
        public final int row;
        public final int col;
        
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public int distanceTo(Point that) {
            return Math.abs(this.row-that.row) + Math.abs(this.col-that.col);
        }
        
        /**
         * 메서드의 입력 객체가 해당 객체보다 나중에 위치하고,<br/>
         * 메서드의 입력 객체와의 거리가 정확하게 2일 경우,<br/>
         * 메서드의 입력 객체와 해당 객체 사이에 위치하는 객체 목록을 반환한다.
         */
        public List<Point> passingPointsTo(Point that) {
            if (this.row==that.row) //입력 객체가 해당 객체의 오른쪽에 위치한 경우
                return List.of(new Point(this.row, this.col+1));
            else if (this.col==that.col) //입력 객체가 해당 객체의 아래쪽에 위치한 경우
                return List.of(new Point(this.row+1, this.col));
            else if (this.col<that.col) //입력 객체가 해당 객체 오른쪽 아래에 위치한 경우
            	return List.of(
            		new Point(this.row, this.col+1),
            		new Point(this.row+1, this.col)
            	);
            else //입력 객체가 해당 객체 왼쪽 아래에 위치한 경우
            	return List.of(
            		new Point(this.row, this.col-1),
            		new Point(this.row+1, this.col)
            	);
            			
                
        }
        
        public char placeFrom(String[] places) {
            return places[this.row].charAt(this.col);
        }
        
        @Override
        public String toString() {
        	return new StringBuffer().append('(').append(this.row).append(", ").append(this.col).append(')').toString();
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
    
    
    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        for (int testCase=0; testCase<places.length; testCase++) {
            boolean tmp = true; //testCase가 거리 두기를 잘 지키고 있는지 여부
            List<Point> participantPositions = participantPositions(places[testCase]); //참가자의 위치 목록
            for (int beforeIndex=0; beforeIndex<participantPositions.size(); beforeIndex++) {
                Point beforePoint = participantPositions.get(beforeIndex);
                for (int afterIndex=beforeIndex+1; afterIndex<participantPositions.size(); afterIndex++) {
                    Point afterPoint = participantPositions.get(afterIndex);
                    int distance = beforePoint.distanceTo(afterPoint);
                    if (distance<2) {
                        tmp = false;
                        break;
                    } else if (distance>2) {
                        continue;
                    }
                    //두 참가자의 거리가 2인 경우에는 두 참가자 사이의 칸이 전부 X인지 여부를 반환한다.
                    List<Point> passingPoints = beforePoint.passingPointsTo(afterPoint);
                    for (Point passingPoint:passingPoints) {
                        if (passingPoint.placeFrom(places[testCase]) != 'X') { //거리가 2인 두 참가자 사이에 파티션이 없는 경우-
                            tmp = false;
                            break;
                        }
                    } //passingPoint loop
                    
                } //afterIndex loop
                if (!tmp)
                    break;
            } //beforeIndex loop
            answer[testCase] = tmp ? 1 : 0;
        } //testCase loop
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
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
}