import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
	
	static enum Direction {
		UP(-1, 0),
		DOWN(1, 0),
		LEFT(0, -1),
		RIGHT(0, 1);
		
		public final int dRow;
		public final int dCol;
		
		private Direction(int dRow, int dCol) {
			this.dRow = dRow;
			this.dCol = dCol;
		}
	}
	
	static class Tuple {
		public final int row;
		public final int col;
		public final Direction dir;
		
		private Tuple(int row, int col, Direction dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		private Tuple(int row, int col) {
			this.row = row;
			this.col = col;
			this.dir = null;
		}
		
		private static int hash(int row, int col) {
			return row*961 + col*31 + 30;
		}
		
		private static int hash(int row, int col, Direction dir) {
			return row*961 + col*31 + dir.ordinal();
		}
		
		private static final Map<Integer, Tuple> INSTANCE_MAP = new HashMap<>();
		
		public static Tuple of(int row, int col) {
			if (row<0 || col<0)
				throw new IllegalArgumentException("row or col cannot be negative.");
			int hashCode = Tuple.hash(row, col);
			Tuple result = Tuple.INSTANCE_MAP.get(hashCode);
			if (result!=null)
				return result;
			result = new Tuple(row, col);
			Tuple.INSTANCE_MAP.put(hashCode, result);
			return result;
		}
		
		public static Tuple of(int row, int col, Direction dir) {
			if (row<0 || col<0)
				throw new IllegalArgumentException("row or col cannot be negative.");
			int hashCode = Tuple.hash(row, col, dir);
			Tuple result = Tuple.INSTANCE_MAP.get(hashCode);
			if (result!=null)
				return result;
			result = new Tuple(row, col, dir);
			Tuple.INSTANCE_MAP.put(hashCode, result);
			return result;
		}
		
		@Override
		public int hashCode() {
			if (this.dir!=null)
				return Tuple.hash(this.row, this.col, this.dir);
			else
				return Tuple.hash(this.row, this.col);
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Tuple))
				return false;
			Tuple that = (Tuple) o;
			return this.row == that.row
				&& this.col == that.col
				&& this.dir == that.dir;
		}

		@Override
		public String toString() {
			return "Tuple [row=" + row + ", col=" + col + ", dir=" + dir + "]";
		}
	}
	
	static class Node implements Comparable<Node> {
		public final Tuple tuple;
		public final int cost;
		
		public Node(int row, int col, int cost) {
			this.tuple = Tuple.of(row, col);
			this.cost = cost;
		}
		
		public Node(Direction dir, int row, int col, int cost) {
			this.tuple = Tuple.of(row, col, dir);
			this.cost = cost;
		}
		
		public Node moveTo(Direction dir) {
			try {
				if (this.tuple.dir==null || this.tuple.dir==dir) {
					return new Node(dir, this.tuple.row+dir.dRow, this.tuple.col+dir.dCol, this.cost+100);
				} else {
					return new Node(dir, this.tuple.row+dir.dRow, this.tuple.col+dir.dCol, this.cost+600);
				}
			} catch (IllegalArgumentException iae) {
				return null;
			}
		}
		
		@Override
		public int compareTo(Node that) {
			return Integer.compare(this.cost, that.cost);
		}
	}
	
	static int distToCost(Integer dist) {
		if (dist==null)
			return Integer.MAX_VALUE;
		else
			return dist.intValue();
	}
	
	/**
	 * 다익스트라 최단경로 알고리즘
	 * 한 노드에서 다른 노드로 가는데 상황에 따라 비용이 다르므로 이 문제를 "가중치가 있는 그래프"로 볼 수 있다.
	 * 가중치가 있는 그래프에서 최단 경로는 너비 우선 탐색을 사용할 수 없고 다른 알고리즘을 사용해야 한다.
	 */
	public int solution(int[][] board) {
		/**
		 * 비용 테이블: 특정 위치에 도달하기 위해 필요한 최소 비용을 저장하되, 특정 위치에 도달할 시점의 [방향] 역시 저장해둔다.
		 * 특정 위치에 윗 방향으로 도착했을 때 비용과 특정 위치에 아랫 방향으로 도착했을 때 비용을 전부 기록해 두어야 문제의 정답을 구할 수 있다.
		 * 예를 들어 특정 위치에 오른쪽 방향으로 도착했을 때 비용이 윗쪽 방향으로 도착했을 때 보다 저렴하게 도착하더라도 목표 방향이 바로 아래 칸일 경우
		 * 특정 위치에 윗쪽 방향으로 도착했을 때 비용이 글로벌 옵티멈이 될 수 있다.
		 */
		Map<Tuple, Integer> distTable = new HashMap<>();
		distTable.put(Tuple.of(0, 0), 0);
		Queue<Node> pq = new PriorityQueue<>();
		Node initialNode = new Node(0, 0, 0);
		for (Direction dir:Direction.values()) {
			Node nextNode = initialNode.moveTo(dir);
			if (nextNode==null) continue;
			if (board[nextNode.tuple.row][nextNode.tuple.col]!=0) continue;
			distTable.put(nextNode.tuple, nextNode.cost);
			pq.add(nextNode);
		} //direction loop
		
		while (!pq.isEmpty()) {
			Node currentNode = pq.poll();
			if (currentNode.cost!=distTable.get(currentNode.tuple))
				continue;
			for (Direction dir:Direction.values()) {
				Node nextNode = currentNode.moveTo(dir);
//				System.out.println("nextNode: " + nextNode);
				if (nextNode==null) continue;
				try {
					if (board[nextNode.tuple.row][nextNode.tuple.col]!=0) continue;
				} catch (ArrayIndexOutOfBoundsException aioobe) { //옆으로 이동할 때 board의 오른쪽 혹은 아래 경계를 넘는 경우 ArrayIndexOutOfBoundsException이 던져진다.
					continue;
				}
				if (nextNode.cost>=distToCost(distTable.get(nextNode.tuple))) continue;
				distTable.put(nextNode.tuple, nextNode.cost);
				pq.add(nextNode);
			} //direction loop
		} //while loop
//		System.out.println(distTable);
		List<Integer> possibleAnswers = new ArrayList<>();
		for (Direction dir:Direction.values()) {
			possibleAnswers.add(distTable.get(Tuple.of(board.length-1, board.length-1, dir)));
		}
//		System.out.println(distTable);
		return possibleAnswers.stream().mapToInt((Int) -> distToCost(Int)).min().orElse(-1);
	}
	
	public static void main(String[] args) {
		try {
		int[][] board = {{0,0,0,0,0,0},{0,1,1,1,1,0},{0,0,1,0,0,0},{1,0,0,1,0,1},{0,1,0,0,0,1},{0,0,0,0,0,0}};
		Solution solution = new Solution();
		System.out.println(solution.solution(board));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
