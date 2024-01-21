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
		합이 n+1이 되는 두 카드를 페어라고 하자. 이 문제에서 페어는 3 종류가 있다.
		a. 동전을 소모하지 않고 만들 수 있는 페어 (처음에 받는 n/3개의 카드 중 2개를 사용하여 만드는 페어)
		b. 동전을 1개 소모하여 만들 수 있는 페어 (처음에 받는 카드와 이후에 드로하는 카드 1개씩을 사용하여 만드는 페어)
		c. 동전을 2개 소모하여 만들 수 있는 페어 (처음에 받는 카드를 사용하지 않고 이후에 드로하는 카드 2개를 사용하여 만드는 페어)
		이 문제에서 동전은 "연명"하는 수단이므로 동전을 아낄 수록 더 질기게 살아남을 수 있다. 따라서 a, b, c 세 종류의 페어 중 가급적 동전을 소모하지 않는
		페어를 제출해야 전략적인 움직임이 된다. [이 문제에 적용된 알고리즘은 그리디로 볼 수 있다.]
		본 문제에서는 카드 더미에서 두 카드를 드로할 때 해당 카드들을 구매할 지, 하지 않을 지 정해야 한다. 하지만 이 문제 풀이에서는 그렇게 접근하지 않는다.
		카드 더미에서 두 카드를 드로할 때 이 카드들로 생성할 수 있는 페어의 정보를 담는다. (온라인 마켓의 "찜"과 같다. 당장에 카드를 구매하지 않고,
		나중에 필요하면 구매한다.) 매 라운드마다 페어를 하나 씩 제출해야 하는데, 동전을 1개 혹은 2개 필요한 페어를 제출해야 하는 경우에 "찜" 해 놓은 카드를 구매한다.
		동전을 1개 혹은 2개 필요한 페어를 제출해야 하는데 동전이 부족한 경우 연명이 불가능하고 라운드 진행이 중단된다.

		아래의 풀이에서 카드 정보와 페어 정보를 Set에 넣어서 보관하였다. 카드 정보는 카드의 인덱스가 필요한 것이 아니라
			[해당 컬렉션이 특정 카드를 가지고 있는가 여부]
		가 필요하므로 이런 조회에 유리한 Set을 사용한다. 페어 정보는 컬렉션에서 제거가 용이하고 중복을 제거해야 할 필요가 있다. (i 번째 카드와 j 번째 카드로 생성가능한 페어는,
		j 번째 카드와 i 번째 카드로도 생성 가능하다. 하지만 이 두 페어는 사실상 같은 것이므로 컬렉션에서 제거가 되어야 한다.) 이 두 조건을 만족하는 Set에 
		페어 정보를 넣는다.
	*/
	
	public int solution(int coin, int[] cards) {
		/**구매하지 않고 활용할 수 있는 카드 집합*/
		Set<Integer> handCards = new HashSet<>();
		/**구매해야만 활용할 수 있는 카드 집합*/
		Set<Integer> passingCards = new HashSet<>();
		/**카드를 구매하지 않고 만들 수 있는 페어*/
		Set<Pair> pairsInHand = new HashSet<>();
		/**카드를 한 개 구매하여 만들 수 있는 페어*/
		Set<Pair> pairsPassing1 = new HashSet<>();
		/**카드를 두 개 구매하여 만들 수 있는 페어*/
		Set<Pair> pairsPassing2 = new HashSet<>();
		int numRemainingCoins = coin;
		
		for (int i=0; i<cards.length/3; i++) {
			handCards.add(cards[i]);
		}
		
		for (int card:handCards) {
			//동전을 사용하지 않고 만들 수 있는 페어 집합을 구성한다.
			int counterPart = cards.length+1-card;
			if (handCards.contains(counterPart)) {
				pairsInHand.add(new Pair(card, counterPart));
			}
		}
		
		for (int round=0; round<cards.length/3; round++) {	
			
			//step a. 커서를 오른쪽으로 두 칸 옮기면서 만나는 카드를 passingCards에 넣는다.
			int newCard0 = cards[cards.length/3+2*round];
			int newCard1 = cards[cards.length/3+2*round+1];
			passingCards.add(newCard0);
			passingCards.add(newCard1);
			
			//step b. 이번에 새로 추가된 카드를 사용하여 생성 가능한 페어를 조사하여 pairsPassing1 및 pairsPassing2에 넣는다.
			int counterPart0 = cards.length+1-newCard0;
			if (handCards.contains(counterPart0)) { //counterPart0가 handCards에 있다면 해당 페어는 [동전 1개를 사용하여 만들 수 있는 페어] 이다.
				pairsPassing1.add(new Pair(newCard0, counterPart0)); 
			} else if (passingCards.contains(counterPart0)) { //counterPart0가 passingCards에 있다면 해당 페어는 [동전 2개를 사용하여 만들 수 있는 페어] 이다.
				pairsPassing2.add(new Pair(newCard0, counterPart0));
			}
			int counterPart1 = cards.length+1-newCard1;
			if (handCards.contains(counterPart1)) {
				pairsPassing1.add(new Pair(newCard1, counterPart1));
			} else if (passingCards.contains(counterPart1)) {
				pairsPassing2.add(new Pair(newCard1, counterPart1));
			}
			
			//step c. 구매를 하지 않고 만들 수 있는 페어가 있으면 그 중 하나를 제거하고 다음 반복으로 진행한다. (동전 0개를 사용하여 연명이 가능한 경우)
			if (!pairsInHand.isEmpty()) {
				pairsInHand.remove(pairsInHand.stream().findAny().get());
				continue;
			}
			
			//step d. 동전이 1개 이상 있고, 동전을 1개 사용하여 만들 수 있는 페어가 있으면 그 중 하나를 제거하고 다음 반복으로 진행한다. (동전 1개를 사용하여 연명이 가능한 경우)
			if (numRemainingCoins>=1 && !pairsPassing1.isEmpty()) {
				numRemainingCoins -= 1;
				pairsPassing1.remove(pairsPassing1.stream().findAny().get());
				continue;
			}
			
			//step e. 동전이 2개 이상 있고, 동전을 2개 사용하여 만들 수 있는 페어가 있으면 그 중 하나를 제거하고 다음 반복으로 진행한다. (동전 2개를 사용하여 연명이 가능한 경우)
			if (numRemainingCoins>=2 && !pairsPassing2.isEmpty()) {
				numRemainingCoins -= 2;
				pairsPassing2.remove(pairsPassing2.stream().findAny().get());
				continue;
			}
			
			//step f. 위 세 단계 중 하나를 만족하지 못하는 경우 더 이상 연명하지 못하고 round loop를 빠져나온다.
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
