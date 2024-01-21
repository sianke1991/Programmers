import java.util.*;

public class Solution {
	
	static class Pair {
		public final int first;
		public final int second;
		
		public Pair(int first, int second) {
			this.first = Math.min(first, second);
			this.second = Math.max(first, second);
		}
		
		@Override
		public int hashCode() {
			return this.first*31+this.second;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Pair))
				return false;
			Pair that = (Pair) o;
			return this.first == that.first && this.second == that.second;
		}
	}

	/*
		���� n+1�� �Ǵ� �� ī�带 ����� ����. �� �������� ���� 3 ������ �ִ�.
		a. ������ �Ҹ����� �ʰ� ���� �� �ִ� ��� (ó���� �޴� n/3���� ī�� �� 2���� ����Ͽ� ����� ���)
		b. ������ 1�� �Ҹ��Ͽ� ���� �� �ִ� ��� (ó���� �޴� ī��� ���Ŀ� ����ϴ� ī�� 1������ ����Ͽ� ����� ���)
		c. ������ 2�� �Ҹ��Ͽ� ���� �� �ִ� ��� (ó���� �޴� ī�带 ������� �ʰ� ���Ŀ� ����ϴ� ī�� 2���� ����Ͽ� ����� ���)
		�� �������� ������ "����"�ϴ� �����̹Ƿ� ������ �Ƴ� ���� �� ����� ��Ƴ��� �� �ִ�. ���� a, b, c �� ������ ��� �� ������ ������ �Ҹ����� �ʴ�
		�� �����ؾ� �������� �������� �ȴ�. [�� ������ ����� �˰����� �׸���� �� �� �ִ�.]
		�� ���������� ī�� ���̿��� �� ī�带 ����� �� �ش� ī����� ������ ��, ���� ���� �� ���ؾ� �Ѵ�. ������ �� ���� Ǯ�̿����� �׷��� �������� �ʴ´�.
		ī�� ���̿��� �� ī�带 ����� �� �� ī���� ������ �� �ִ� ����� ������ ��´�. (�¶��� ������ "��"�� ����. ���忡 ī�带 �������� �ʰ�,
		���߿� �ʿ��ϸ� �����Ѵ�.) �� ���帶�� �� �ϳ� �� �����ؾ� �ϴµ�, ������ 1�� Ȥ�� 2�� �ʿ��� �� �����ؾ� �ϴ� ��쿡 "��" �� ���� ī�带 �����Ѵ�.
		������ 1�� Ȥ�� 2�� �ʿ��� �� �����ؾ� �ϴµ� ������ ������ ��� ������ �Ұ����ϰ� ���� ������ �ߴܵȴ�.

		�Ʒ��� Ǯ�̿��� ī�� ������ ��� ������ Set�� �־ �����Ͽ���. ī�� ������ ī���� �ε����� �ʿ��� ���� �ƴ϶�
			[�ش� �÷����� Ư�� ī�带 ������ �ִ°� ����]
		�� �ʿ��ϹǷ� �̷� ��ȸ�� ������ Set�� ����Ѵ�. ��� ������ �÷��ǿ��� ���Ű� �����ϰ� �ߺ��� �����ؾ� �� �ʿ䰡 �ִ�. (i ��° ī��� j ��° ī��� ���������� ����,
		j ��° ī��� i ��° ī��ε� ���� �����ϴ�. ������ �� �� ���� ��ǻ� ���� ���̹Ƿ� �÷��ǿ��� ���Ű� �Ǿ�� �Ѵ�.) �� �� ������ �����ϴ� Set�� 
		��� ������ �ִ´�.
	*/
	
	public int solution(int coin, int[] cards) {
		/**�������� �ʰ� Ȱ���� �� �ִ� ī�� ����*/
		Set<Integer> handCards = new HashSet<>();
		/**�����ؾ߸� Ȱ���� �� �ִ� ī�� ����*/
		Set<Integer> passingCards = new HashSet<>();
		/**ī�带 �������� �ʰ� ���� �� �ִ� ���*/
		Set<Pair> pairsInHand = new HashSet<>();
		/**ī�带 �� �� �����Ͽ� ���� �� �ִ� ���*/
		Set<Pair> pairsPassing1 = new HashSet<>();
		/**ī�带 �� �� �����Ͽ� ���� �� �ִ� ���*/
		Set<Pair> pairsPassing2 = new HashSet<>();
		int numRemainingCoins = coin;
		
		for (int i=0; i<cards.length/3; i++) {
			handCards.add(cards[i]);
		}
		
		for (int card:handCards) {
			//������ ������� �ʰ� ���� �� �ִ� ��� ������ �����Ѵ�.
			int counterPart = cards.length+1-card;
			if (handCards.contains(counterPart)) {
				pairsInHand.add(new Pair(card, counterPart));
			}
		}
		
		for (int round=0; round<cards.length/3; round++) {	
			
			//step a. Ŀ���� ���������� �� ĭ �ű�鼭 ������ ī�带 passingCards�� �ִ´�.
			int newCard0 = cards[cards.length/3+2*round];
			int newCard1 = cards[cards.length/3+2*round+1];
			passingCards.add(newCard0);
			passingCards.add(newCard1);
			
			//step b. �̹��� ���� �߰��� ī�带 ����Ͽ� ���� ������ �� �����Ͽ� pairsPassing1 �� pairsPassing2�� �ִ´�.
			int counterPart0 = cards.length+1-newCard0;
			if (handCards.contains(counterPart0)) { //counterPart0�� handCards�� �ִٸ� �ش� ���� [���� 1���� ����Ͽ� ���� �� �ִ� ���] �̴�.
				pairsPassing1.add(new Pair(newCard0, counterPart0)); 
			} else if (passingCards.contains(counterPart0)) { //counterPart0�� passingCards�� �ִٸ� �ش� ���� [���� 2���� ����Ͽ� ���� �� �ִ� ���] �̴�.
				pairsPassing2.add(new Pair(newCard0, counterPart0));
			}
			int counterPart1 = cards.length+1-newCard1;
			if (handCards.contains(counterPart1)) {
				pairsPassing1.add(new Pair(newCard1, counterPart1));
			} else if (passingCards.contains(counterPart1)) {
				pairsPassing2.add(new Pair(newCard1, counterPart1));
			}
			
			//step c. ���Ÿ� ���� �ʰ� ���� �� �ִ� �� ������ �� �� �ϳ��� �����ϰ� ���� �ݺ����� �����Ѵ�. (���� 0���� ����Ͽ� ������ ������ ���)
			if (!pairsInHand.isEmpty()) {
				pairsInHand.remove(pairsInHand.stream().findAny().get());
				continue;
			}
			
			//step d. ������ 1�� �̻� �ְ�, ������ 1�� ����Ͽ� ���� �� �ִ� �� ������ �� �� �ϳ��� �����ϰ� ���� �ݺ����� �����Ѵ�. (���� 1���� ����Ͽ� ������ ������ ���)
			if (numRemainingCoins>=1 && !pairsPassing1.isEmpty()) {
				numRemainingCoins -= 1;
				pairsPassing1.remove(pairsPassing1.stream().findAny().get());
				continue;
			}
			
			//step e. ������ 2�� �̻� �ְ�, ������ 2�� ����Ͽ� ���� �� �ִ� �� ������ �� �� �ϳ��� �����ϰ� ���� �ݺ����� �����Ѵ�. (���� 2���� ����Ͽ� ������ ������ ���)
			if (numRemainingCoins>=2 && !pairsPassing2.isEmpty()) {
				numRemainingCoins -= 2;
				pairsPassing2.remove(pairsPassing2.stream().findAny().get());
				continue;
			}
			
			//step f. �� �� �ܰ� �� �ϳ��� �������� ���ϴ� ��� �� �̻� �������� ���ϰ� round loop�� �������´�.
			return round+1;
		} //round loop
		
		return cards.length/3+1;
    }
	
	public static void main(String[] args) {
		try {
		int coin = 4;
		int[] cards = {3, 6, 7, 2, 1, 10, 5, 9, 8, 12, 11, 4};
		Solution solution = new Solution();
		System.out.println(solution.solution(coin, cards));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
