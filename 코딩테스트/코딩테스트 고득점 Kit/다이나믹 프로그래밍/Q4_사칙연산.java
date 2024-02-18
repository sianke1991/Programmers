import java.util.*;

public class Solution {
	List<Integer> absValues = new ArrayList<>();
	List<Integer> signs = new ArrayList<>();
	//maxArray[i][j] := 인덱스 i 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최대치
	long[][] maxArray = new long[102][102];
	//minArray[i][j] := 인덱스 i 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최소치
	long[][] minArray = new long[102][102];
	
	public int solution(String[] arr) {
		signs.add(+1);
		for (int i=0; i<arr.length; i+=2) {
			absValues.add(Integer.parseInt(arr[i]));
		}
		for (int i=1; i<arr.length; i+=2) {
			switch (arr[i]) {
				case "+": signs.add(+1); break;
				case "-": signs.add(-1); break;
			}
		}
		
		// 점화식
		for (int i=absValues.size()-1; i>=0; i--) {
			maxArray[i][i] = absValues.get(i);
			minArray[i][i] = absValues.get(i);
			for (int j=i+1; j<absValues.size(); j++) {
				long maxValue = Long.MIN_VALUE;
				long minValue = Long.MAX_VALUE;
				for (int k=i+1; k<=j; k++) {
					/*
					 * 인덱스 i 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최대치를 구하려면
					 * [인덱스 i 이상 k 미만의 수를 연산하여 얻을 수 있는 값 중 최대치] + [인덱스 k 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최대치]
					 * 혹은
					 * [인덱스 i 이상 k 미만의 수를 연산하여 얻을 수 있는 값 중 최대치] - [인덱스 k 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최소치]
					 * 로 구한다.
					 * 비슷하게,
					 * 인덱스 i 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최소치를 구하려면
					 * [인덱스 i 이상 k 미만의 수를 연산하여 얻을 수 있는 값 중 최소치] + [인덱스 k 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최소치]
					 * 혹은
					 * [인덱스 i 이상 k 미만의 수를 연산하여 얻을 수 있는 값 중 최소치] - [인덱스 k 이상 j 이하의 수를 연산하여 얻을 수 있는 값 중 최대치]
					 * 로 구한다.
					 */
					switch (signs.get(k)) {
						case +1: {
							maxValue = Math.max(maxValue, maxArray[i][k-1] + maxArray[k][j]);
							minValue = Math.min(minValue, minArray[i][k-1] + minArray[k][j]);
							break;
						}
						case -1: {
							maxValue = Math.max(maxValue, maxArray[i][k-1] - minArray[k][j]);
							minValue = Math.min(minValue, minArray[i][k-1] - maxArray[k][j]);
							break;
						}
					} //부호에 따라 분기
				} //k loop
				// k loop의 결과를 array에 담는다.
				maxArray[i][j] = maxValue;
				minArray[i][j] = minValue;
			} //j loop
		} //i loop
		return (int)maxArray[0][absValues.size()-1];
	}
	
	public static void main(String[] args) {
		String[] arr = {"5", "-", "3", "+", "1", "+", "2", "-", "4"};
		Solution sol = new Solution();
		
		System.out.println(sol.solution(arr));
		
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				System.out.print(sol.maxArray[i][j] + " ");
			}
			System.out.println();
		}
	}

}
