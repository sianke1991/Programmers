import java.util.*;

public class WrongSolution {
	
	/**최초 받는 카드의 개수를 할당하고 그 이후에는 값을 변경하지 않는다.*/
	static int numInitialCards;
	/**사용하는 카드 배열*/
	static int[] cardArray;
	/**최초 받는 동전의 개수를 할당하고 그 이후에는 값을 변경하지 않는다.*/
	static int numInitialCoins;
	
	/**
	 * bfs를 적용할 때 사용할 노드
	 * @author sianke1991
	 */
	static class Node {
		/**남아있는 동전 개수*/
		public final int numCoins;
		/**가지고 있는 카드 종류*/
		public final Set<Integer> cards;
		/**해당 노드에 도달하기 위해 거쳐하 하는 라운드 개수*/
		public final int numSteps;
		
		public Node(int numCoins, int numSteps) {
			this.numCoins = numCoins;
			this.cards = new HashSet<>();
			this.numSteps = numSteps;
		}
		
		/**
		 * 가지고 있는 카드에서 numCards+1 을 제거한다.<br/>
		 * numCards+1을 제거할 수 없는 경우 <code>false</code>을 반환한다.
		 * @return
		 */
		private boolean removeN1() {
			for (int card:this.cards) {
				int counterPart = numInitialCards+1-card;
				if (this.cards.contains(counterPart)) {
					this.cards.remove(card);
					this.cards.remove(counterPart);
					return true;
				}
			} //card loop
			return false;
		}
		
		/**
		 * bfs를 시작할 노드를 생성한다.<br/>
		 * 메서드 호출 전에 정적 변수의 값을 설정하여야 한다.
		 * @return
		 */
		public static Node initialNode() {
			Node result = new Node(numInitialCoins, 0);
			for (int i=0; i<numInitialCards/3; i++) {
				result.cards.add(cardArray[i]);
			}
			return result;
		}
		
		public List<Node> childNodes() {
			if (this.numSteps>=numInitialCards/3)
				return List.of();
			int[] drawableCards = {
				cardArray[numInitialCards/3+2*this.numSteps],
				cardArray[numInitialCards/3+2*this.numSteps+1]
			};
			List<Node> result = new ArrayList<>();
			Node node0 = new Node(this.numCoins, this.numSteps+1);
			node0.cards.addAll(this.cards);
			if (node0.removeN1()) {
				result.add(node0);
			}
			if (this.numCoins>=1) { //현재 동전이 1개 있으면 카드 큐에서 뽑은 두 장의 카드 중 한 장을 유지할 수 있다.
				Node node1 = new Node(this.numCoins-1, this.numSteps+1);
				node1.cards.addAll(this.cards);
				node1.cards.add(drawableCards[0]);
				if (node1.removeN1()) {
					result.add(node1);
				}
				Node node2 = new Node(this.numCoins-1, this.numSteps+1);
				node2.cards.addAll(this.cards);
				node2.cards.add(drawableCards[1]);
				if (node2.removeN1()) {
					result.add(node2);
				}
			}
			if (this.numCoins>=2) { //현재 동전이 2개 있으면 카드 큐에서 뽑은 두 장의 카드 전부를 유지할 수 있다.
				Node node3 = new Node(this.numCoins-2, this.numSteps+1);
				node3.cards.addAll(this.cards);
				node3.cards.add(drawableCards[0]);
				node3.cards.add(drawableCards[1]);
				if (node3.removeN1()) {
					result.add(node3);
				}
			}
			return result;
		}
		
		
	}
	
	
	public int solution(int coin, int[] cards) {
        int answer = 0;
        numInitialCoins = coin;
        cardArray = cards;
        numInitialCards = cards.length;

        Queue<Node> q = new ArrayDeque<>();
        q.add(Node.initialNode());
        while (!q.isEmpty()) {
        	Node currentNode = q.poll();
        	answer = currentNode.numSteps;
        	q.addAll(currentNode.childNodes());
        } //while loop
        return answer+1;
    }
	
	public static void main(String[] args) {
		int coin = 2;
		int[] cards = {5, 8, 1, 2, 9, 4, 12, 11, 3, 10, 6, 7};
		WrongSolution solution = new WrongSolution();
		System.out.println(solution.solution(coin, cards));
	}
}
