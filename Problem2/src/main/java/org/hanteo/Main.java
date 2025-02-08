package org.hanteo;


public class Main {
    public static void main(String[] args) {
        int sum = 4;
        int[] coins = {1, 2, 3};
        int result = calculateCombinationCounts(coins, sum);
        System.out.println("result = " + result);

        int sum2 = 10;
        int[] coins2 = {2, 5, 3, 6};
        int result2 = calculateCombinationCounts(coins2, sum2);
        System.out.println("result2 = " + result2);
    }

    public static int calculateCombinationCounts(int[] coins, int sum) {
        // dp[n] => n을 만들기 위한 조합 경우의 수;
        int[] dp = new int[sum + 1];

        // 0을 만들기 위한 방법 1가지 -> 아무 coin도 선택하지 않음
        dp[0] = 1;

        for (int coin : coins) {
            for (int i = coin; i < sum+1; i++) {
                // dp[i-coin]: (i-coin)원을 만들 수 있는 모든 조합의 수
                // dp[i]: i원을 조합을 만드는 경우의 수
                // dp[i] += dp[i-coin]: (i-coin)원의 모든 조합에 현재 동전을 추가하여 i원을 만드는 경우를 더함
                dp[i] += dp[i-coin];
            }
        }

        return dp[sum];
    }
}