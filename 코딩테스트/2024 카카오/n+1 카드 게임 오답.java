import java.util.*;

public class WrongSolution {
	
	/**���� �޴� ī���� ������ �Ҵ��ϰ� �� ���Ŀ��� ���� �������� �ʴ´�.*/
	static int numInitialCards;
	/**����ϴ� ī�� �迭*/
	static int[] cardArray;
	/**���� �޴� ������ ������ �Ҵ��ϰ� �� ���Ŀ��� ���� �������� �ʴ´�.*/
	static int numInitialCoins;
	
	/**
	 * bfs�� ������ �� ����� ���
	 * @author sianke1991
	 */
	static class Node {
		/**�����ִ� ���� ����*/
		public final int numCoins;
		/**������ �ִ� ī�� ����*/
		public final Set<Integer> cards;
		/**�ش� ��忡 �����ϱ� ���� ������ �ϴ� ���� ����*/
		public final int numSteps;
		
		public Node(int numCoins, int numSteps) {
			this.numCoins = numCoins;
			this.cards = new HashSet<>();
			this.numSteps = numSteps;
		}
		
		/**
		 * ������ �ִ� ī�忡�� numCards+1 �� �����Ѵ�.<br/>
		 * numCards+1�� ������ �� ���� ��� <code>false</code>�� ��ȯ�Ѵ�.
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
		 * bfs�� ������ ��带 �����Ѵ�.<br/>
		 * �޼��� ȣ�� ���� ���� ������ ���� �����Ͽ��� �Ѵ�.
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
			if (this.numCoins>=1) { //���� ������ 1�� ������ ī�� ť���� ���� �� ���� ī�� �� �� ���� ������ �� �ִ�.
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
			if (this.numCoins>=2) { //���� ������ 2�� ������ ī�� ť���� ���� �� ���� ī�� ���θ� ������ �� �ִ�.
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
